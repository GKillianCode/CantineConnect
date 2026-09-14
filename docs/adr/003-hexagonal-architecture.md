# Scope & Périmètre du MVP — CantineConnect

| Métadonnée | Valeur                                          |
| :--- |:------------------------------------------------|
| **Référence** | `docs/adr/003-hexagonal-architecture.md`              |
| **Statut** | Validé                                          |
| **Date** | 10 septembre 2026                               |
| **Auteur** | Killian GODET                                   |
| **Architecture** | Monolithe Modulaire (Spring Boot 3.x / Java 21) |

---

# Adoption de l'Architecture Hexagonale (Ports & Adaptateurs)

## Contexte
Le cœur métier de CantineConnect contient des règles critiques (délai $J-2$, blocages sanitaires PAI, algorithme FEPS).
Mélanger ce code métier avec le framework Spring Boot, des annotations de persistance JPA (`@Entity`, `@Table`) ou du code HTTP REST rendrait l'application difficile à tester, dépendante de l'infrastructure et sensible aux évolutions techniques.

## Décision
Nous adoptons l'**Architecture Hexagonale** (Ports et Adaptateurs) au sein de chaque Bounded Context du Monolithe Modulaire.

Chaque module (`booking`, `inventory`, `billing`) est structuré selon 3 couches d'isolation strictes :
1. **`domain` (Cœur métier) :** Pure Java 21, zéro dépendance externe (ni Spring, ni JPA, ni frameworks). Il héberge les Agrégats, Value Objects, Invariants et les interfaces de persistence (Ports de sortie).
2. **`application` (Cas d'usage) :** Orchestration des flux applicatifs (Commandes, Requêtes, Handlers) servant de Ports d'entrée.
3. **`infrastructure` (Monde extérieur) :** Implémentations techniques (Contrôleurs REST, Repositories Spring Data JPA, adaptateurs d'événements).

## Conséquences
* **Positives :**
    * Testabilité optimale : le domaine se teste via des tests unitaires JUnit 5 purs, exécutables en quelques millisecondes sans contexte Spring.
    * Isolation totale du métier vis-à-vis des choix techniques (changement de base de données ou de framework sans réécriture du domaine).
    * Alignement naturel avec les principes DDD.
* **Négatives / Compromis :**
    * Duplication de structures de données (nécessité de Mappers entre Entités JPA, Agrégats du Domaine et DTOs REST).
    * Légère surcouche de code (boilerplate) lors de la création d'un cas d'usage.
