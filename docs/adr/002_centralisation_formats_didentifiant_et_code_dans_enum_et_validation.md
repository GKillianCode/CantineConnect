# ADR 002 : Centralisation des formats d'identifiants et codes via enums et validation déléguée

- **Date :** 2026-11-19
- **Statut :** Accepté
- **Domaine :** Domaine métier / Validation & Génération (`common` / `schoollife`)

## Contexte
L'application manipule plusieurs catégories de formats structurés : des identifiants publics (`publicId`) variant selon le type d'entité cible (ex. étudiant, responsable légal) et un code de liaison sécurisé utilisé lors du rattachement d'un responsable à un élève.
Dupliquer les règles de format (préfixe, découpage en parties, longueurs) et les contrôles de validité dans les DTOs, services ou contrôleurs créerait un risque d'incohérence ou d'oubli lors d'une évolution de format.

## Décision
Il a été décidé de centraliser les définitions de formats et la logique de vérification syntaxique au sein d'enums dédiés, consommés par un générateur réutilisable et des annotations Bean Validation :
- **Enums comme source de vérité :** Chaque catégorie de format est portée par un enum dédié (ex. un enum de type de `publicId` et un enum pour le code de liaison) définissant les métadonnées structurelles (préfixe, parties, longueurs des parties) ainsi qu'une méthode de validation encapsulant le contrôle de format.
- **Génération paramétrée :** Un générateur commun réutilise l'algorithme de base en s'appuyant sur les paramètres fournis par l'enum concerné.
- **Annotations de validation (`FIELD` et `PARAMETER`) :** Des annotations personnalisées, applicables sur les champs (`ElementType.FIELD`) et les paramètres de méthode (`ElementType.PARAMETER`), délèguent le contrôle de format à la méthode de validation de l'enum (avec un attribut de type lorsque plusieurs types coexistent, et sans paramètre lorsqu'un type unique s'applique).

## Conséquences

### Positives
- **Source de vérité unique :** Les longueurs, préfixes et règles de format sont définis à un seul endroit, limitant les divergences entre génération, saisie et validation.
- **Réutilisabilité et clarté :** Le générateur reste générique et les annotations gardent les DTOs et signatures de méthodes lisibles.
- **Séparation des responsabilités :** Les annotations vérifient la conformité syntaxique/structurelle du format, tandis que les règles métier (existence en base, correspondance avec la date de naissance, limite de responsables, unicité) restent dans la couche Service.

### Négatives / Compromis
- **Légère prolifération d'annotations/validators :** L'ajout de nouvelles catégories de formats peut nécessiter des annotations ou validateurs dédiés selon la signature attendue.
- **Couplage aux enums :** Toute modification d'un préfixe ou d'une longueur dans un enum impacte directement les formats attendus et générés.