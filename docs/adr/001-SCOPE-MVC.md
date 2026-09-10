# Scope & Périmètre du MVP — CantineConnect

| Métadonnée | Valeur                                          |
| :--- |:------------------------------------------------|
| **Référence** | `docs/adr/001-SCOPE-MVP.md`                     |
| **Statut** | Validé                                          |
| **Date** | 10 septembre 2026                               |
| **Auteur** | Killian GODET                                   |
| **Architecture** | Monolithe Modulaire (Spring Boot 3.x / Java 21) |

---

## 1. Objectif du document

Ce document définit les frontières strictes du **Minimum Viable Product (MVP)** pour le projet **CantineConnect**.

Afin de maximiser la valeur technique du projet pour un profil Backend (Spring Boot, Domain-Driven Design, Spring Batch), le périmètre fonctionnel initial du cahier des charges est filtré. Les fonctionnalités à faible valeur ajoutée (IHM, CRUDs administratifs) sont exclues pour se concentrer sur la complexité métier et algorithmique.

---

## 2. Bounded Contexts inclus (Périmètre IN)

Le système est découpé en **3 modules / Bounded Contexts étanches** hébergés dans un Monolithe Modulaire :

![Modules - CantineConnect](/docs/assets/diagrams/export/cantine_connect_modules-v1.0.png)

### 2.1 Module `booking` (Réservations & PAI)
* **API REST :** Exposition des endpoints de réservation et d'annulation de repas.
* **Invariant J-2 (48h) :** Impossibilité d'ajouter ou d'annuler une réservation à moins de 48 heures du service.
* **Invariant PAI / Allergies :** Vérification systématique de l'incompatibilité entre les composants du menu et la fiche sanitaire (PAI) de l'enfant. Blocage de la réservation en cas de risque.
* **Publication d'événements :** Émission de l'événement métier `MealBookedEvent` lors d'une réservation validée.

### 2.2 Module `inventory` (Logistique & Stock)
* **Écoute événementielle :** Consommation asynchrone / interne de `MealBookedEvent`.
* **Algorithme FEPS (Premier Expiré, Premier Sorti) :** Calcul de la déduction optimale des ingrédients selon la DLC des lots enregistrés en base.
* **Gestion du Seuil de Sécurité :** Détection automatique du franchissement du seuil critique d'un ingrédient et émission d'une alerte.

### 2.3 Module `billing` (Facturation Mensuelle)
* **Batch de fin de mois (Spring Batch) :** Job automatisé d'agrégation des présences réelles et réservations sur le mois écoulé.
* **Calcul Tarifaire :** Application de la grille tarifaire communale indexée sur le Quotient Familial de la famille.
* **Export Comptable :** Génération d'un fichier plat (Flat File CSV/XML) à destination de la comptabilité publique / du Trésor Public.

---

## 3. Périmètre exclu (Périmètre OUT)

Les éléments suivants sont volontairement **exclus du développement** :

* **Front-End / UI :** 0 ligne de code HTML/CSS/JavaScript. L'interaction avec le système se fait exclusivement via Swagger UI / OpenAPI et Postman.
* **Gestion des comptes & CRUDs administratifs :** La création d'utilisateurs, la gestion des écoles et l'affectation des élèves sont initialisées au démarrage via des scripts SQL / Flyway (`data.sql`).
* **Module Recettes & Partage (M2 initial) :** Le réseau communautaire de partage de recettes entre chefs de cuisine n'est pas développé.
* **Modes de paiement en ligne :** Pas d'intégration d'API de paiement (Stripe, PayFit). La facturation s'arrête à la génération du fichier de recouvrement comptable.

---

## 4. Matrice des exigences non-fonctionnelles

| Catégorie | Exigence technique | Solution mise en œuvre |
| :--- | :--- | :--- |
| **Architecture** | Isolation stricte des domaines métier | Clean / Hexagonal Architecture + ArchUnit |
| **Communication** | Découplage inter-modules | Événements de domaine internes (Spring Application Events) |
| **Performance Batch** | Traitement volumétrique sans débordement mémoire | Spring Batch avec Chunk Processing (Reader / Processor / Writer) |
| **Persistence** | Immuabilité des schémas BDD | Migrations automatisées via Flyway |
| **CI / Quality Gate** | Validation automatique à chaque commit | GitHub Actions (Compilation, Tests unitaires, ArchUnit) |