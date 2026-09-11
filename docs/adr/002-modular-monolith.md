# Scope & Périmètre du MVP — CantineConnect

| Métadonnée | Valeur                                          |
| :--- |:------------------------------------------------|
| **Référence** | `docs/adr/002-modular-monolith.md`                       |
| **Statut** | Validé                                          |
| **Date** | 10 septembre 2026                               |
| **Auteur** | Killian GODET                                   |
| **Architecture** | Monolithe Modulaire (Spring Boot 3.x / Java 21) |

---

# Adoption d'un Monolithe Modulaire

## Contexte
Le système CantineConnect gère plusieurs domaines fonctionnels distincts (`booking`, `inventory`, `billing`).
Nous devons garantir une séparation claire des responsabilités métier tout en évitant la complexité opérationnelle globale (déploiement multi-services, latence réseau, orchestration, observabilité) liée à une architecture en microservices.

## Décision
Nous choisissons une architecture en **Monolithe Modulaire** au sein d'une unique application Spring Boot 3.x (Java 21).

Chaque domaine (Bounded Context) est totalement isolé dans son propre package de haut niveau (`fr.killiangodet.cantineconnect.<module>`). Les communications entre modules s'effectuent exclusivement via des événements de domaine internes ou des contrats d'interface stricts, sans aucun accès direct aux composants internes ou aux bases de données des autres modules.

## Conséquences
* **Positives :**
    * Déploiement et processus de build simples via un artéfact binaire unique (`.jar`).
    * Empreinte mémoire maîtrisée et exécution très rapide des tests d'intégration.
    * Frontières métier nettes, facilitant une éventuelle extraction future d'un module en microservice si une forte contrainte d'échelle l'imposait.
* **Négatives / Compromis :**
    * Nécessite une discipline rigoureuse de découpage, contrôlée automatiquement à la compilation par des tests d'architecture (ArchUnit).
    * Passage à l'échelle global (impossibilité d'allouer des ressources d'infrastructure indépendamment à un seul module).