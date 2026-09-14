| Métadonnée | Valeur                                          |
| :--- |:------------------------------------------------|
| **Référence** | `docs/adr/007-standard-exception-handling-rfc7807.md`                               |
| **Statut** | Validé                                          |
| **Date** | 14 septembre 2026                               |
| **Auteur** | Killian GODET                                   |
| **Architecture** | Monolithe Modulaire (Spring Boot 3.x / Java 21) |

---

# Gestion standardisée des exceptions et format RFC 7807 (Problem Details)

## 1. Contexte et Problématique

Dans le cadre d'un Monolithe Modulaire sous Architecture Hexagonale, plusieurs Bounded Contexts coexistent et exposent des API REST (`/api/v1/...`).

Sans convention globale :
1. Les erreurs HTTP risquent d'être renvoyées sous des formats hétérogènes (stacktraces brutes, objets JSON ad hoc), dégradant l'expérience d'intégration côté client/front-end.
2. La couche **Domaine** risquerait d'être couplée aux concepts HTTP (codes `400`, `404`, annotations Spring) pour piloter le statut de réponse, ce qui violerait l'étanchéité hexagonale.
3. Les erreurs techniques d'infrastructure risqueraient de fuiter vers l'extérieur sans masquage préalable.

---

## 2. Décisions

### 2.1. Format de réponse standardisé : RFC 7807
Toutes les réponses d'erreur HTTP de l'application respectent la spécification **RFC 7807 Problem Details**, nativement supportée par Spring Boot via la classe `ProblemDetail`.

Chaque réponse d'erreur contient au minimum :
* `title` : Libellé synthétique de la catégorie d'erreur.
* `status` : Code de statut HTTP (`400`, `404`, `422`, `500`).
* `detail` : Message explicatif destiné au consommateur.
* `timestamp` : Horodatage ISO-8601 de l'événement.
* `module` : Identifiant du Bounded Context ayant levé l'erreur (ex : `booking`, `inventory`).

---

### 2.2. Hiérarchie d'exceptions isolée par couche

L'application s'appuie sur le polymorphisme avec deux classes mères abstraites dans le socle partagé (`shared`) :

1. **Couche Domaine (`DomainException`) :**
  * Classe abstraite pure Java (zéro dépendance Spring/Web).
  * Représente une violation de règle métier (ex : délai d'annulation dépassé).
  * Interceptée et traduite automatiquement en **HTTP `422 Unprocessable Entity`**.

2. **Couche Application (`ApplicationException`) :**
  * Classe abstraite d'orchestration.
  * Représente un échec de cas d'usage ou une entité introuvable via un port de sortie (ex : `ReservationNotFoundException`).
  * Interceptée et traduite automatiquement en **HTTP `404 Not Found`**.

3. **Couche Infrastructure & Web :**
  * Les erreurs de validation DTO (`MethodArgumentNotValidException`, `@Valid`) et de désérialisation JSON (`HttpMessageNotReadableException`) sont traduites en **HTTP `400 Bad Request`**.
  * Toutes les autres exceptions non capturées (`Exception.class`) sont loguées en `ERROR` et masquées derrière un **HTTP `500 Internal Server Error`**.

---

### 2.3. Traitement centralisé et traçabilité modulaire
Un composant unique `@RestControllerAdvice` (`GlobalExceptionHandler`), situé dans `shared.infrastructure.primary.exception`, intercepte l'ensemble des exceptions du système.

Par réflexion Java (`HandlerMethod`), le handler extrait dynamiquement le nom du package du contrôleur intercepté afin d'injecter automatiquement la propriété `"module"` dans le payload RFC 7807.

---

## 3. Conséquences

### Positives
* **Pureté du Domaine :** Le domaine exprime ses erreurs sous forme de classes d'exceptions pures sans aucune connaissance du protocole HTTP.
* **Extensibilité :** Tout nouveau cas d'usage ou Bounded Context crée ses exceptions filles sans jamais modifier la classe `GlobalExceptionHandler`.
* **Régularité des contrats API :** Les clients consommant l'API disposent d'un contrat d'erreur prédictible et documenté sous Swagger UI.

### Risques et Atténuations
* **Inattention sur le choix de la classe mère :** Hériter directement de `RuntimeException` au lieu de `DomainException` ou `ApplicationException` ferait basculer l'erreur en HTTP 500.
  * *Atténuation :* Règle de revue de code et vérification possible via ArchUnit.
