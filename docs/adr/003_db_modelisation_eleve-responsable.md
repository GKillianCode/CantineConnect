# ADR 003 : Modélisation de la liaison élève-responsable, suppression en cascade et audit JPA

- **Date :** 2026-11-19
- **Statut :** Accepté
- **Domaine :** Persistance / Base de données & Modèle (`schoollife` / `common`)

## Contexte
La relation entre un élève (`Student`) et un responsable légal (`LegalGuardian`) est de type plusieurs-à-plusieurs (un élève peut avoir jusqu'à deux responsables légaux par code de liaison unique, et un responsable légal peut être rattaché à plusieurs élèves). Par ailleurs, l'application a besoin de tracer automatiquement la création et la mise à jour des entités (notamment en vue de futures règles de cycle de vie des comptes).
Un simple `@ManyToMany` anonyme limiterait le contrôle explicite sur la table de jointure et ses règles métier, et la gestion manuelle des dates d'audit ou du nettoyage des liens orphelins serait source d'erreurs.

## Décision
Il a été décidé d'adopter les conventions suivantes pour la modélisation relationnelle et l'audit :
- **Entité de jointure explicite à clé composite :** La table `student_legal_guardian` est modélisée via une entité dédiée (`StudentLegalGuardian`) dotée d'une clé primaire composite (`@EmbeddedId` avec `StudentLegalGuardianId`) et des associations `@ManyToOne(fetch = FetchType.LAZY)` reliées via `@MapsId`.
- **`ON DELETE CASCADE` sur les clés étrangères :** Les contraintes de clé étrangère de `student_legal_guardian` vers `student` et `legal_guardian` utilisent `ON DELETE CASCADE` afin de supprimer automatiquement les lignes de jointure orphelines lors de la suppression d'un parent ou d'un élève, sans supprimer l'autre entité référencée.
- **Audit automatique via `AuditableEntity` et JPA Auditing :** Une classe abstraite `@MappedSuperclass` nommée `AuditableEntity` (avec `AuditingEntityListener` et `@EnableJpaAuditing`) porte `createdAt` (`@CreatedDate`) et `updatedAt` (`@LastModifiedDate`) pour les entités concernées.

## Conséquences

### Positives
- **Intégrité référentielle et nettoyage automatique :** La base de données purge immédiatement les liens obsolètes lors de la suppression d'un étudiant ou d'un responsable, tout en préservant les comptes des autres entités (par exemple un parent ayant plusieurs enfants).
- **Contrôle des requêtes et performances :** Le `FetchType.LAZY` évite les chargements implicites non désirés, et l'entité de jointure facilite les requêtes de comptage ou d'existence (`countBy...`, `existsBy...`) nécessaires aux règles métier.
- **Audit uniforme :** Les dates de création et de modification sont maintenues automatiquement sans duplication de code dans les services.

### Négatives / Compromis
- **Verbosité du modèle JPA :** Une classe de clé composite (`equals`/`hashCode`), une entité de jointure et un repository dédié ajoutent davantage de classes qu'un `@ManyToMany` basique.
- **Suppression SQL immédiate :** `ON DELETE CASCADE` agit de manière synchrone au niveau de la base ; les règles métier temporelles (comme la purge des comptes inactifs après 30 jours ou la fin d'année scolaire) devront être portées par des traitements planifiés ou batch dédiés.