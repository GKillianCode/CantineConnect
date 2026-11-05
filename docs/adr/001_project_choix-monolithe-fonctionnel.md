# ADR 001 : Choix d'une architecture monolithe modulaire fonctionnelle

- **Date :** 2026-11-05
- **Statut :** Accepté
- **Domaine :** Projet / Architecture globale

## Contexte
Cantine Connect est un projet personnel (side-project) développé dans le but principal de consolider les fondamentaux de Java, de maîtriser l'écosystème Spring Boot et d'explorer des briques spécifiques (Thymeleaf, Spring Batch). 
Une approche initiale basée sur un Domain-Driven Design (DDD) strict et une architecture hexagonale s'est avérée trop complexe et prématurée par rapport au niveau d'apprentissage visé.

## Décision
Il a été décidé d'opter pour une architecture en **Monolithe Modulaire fonctionnel**.
Le code source ne sera pas découpé par type d'acteur, ni par une architecture hexagonale complexe, mais structuré par **grands domaines métiers fonctionnels** au sein d'un packaging clair :
- `booking` (réservations)
- `inventory` (stocks, recettes, DLC)
- `billing` (facturation, tarifs)
- `schoollife` (écoles, élèves, passages)
- `common` (sécurité, configurations transverses)

## Conséquences

### Positives
- **Réduction de la charge cognitive :** Permet de se concentrer pleinement sur les mécanismes de Java 21 et Spring Boot 3 sans la lourdeur des patterns d'architecture avancés.
- **Cohérence métier :** Les briques fonctionnelles regroupent logiquement les données et les traitements qui vont ensemble, indépendamment de qui les utilise (qu'il s'agisse d'un humain ou d'un automate).
- **Évolutivité :** Conserve une structure propre qui permettra, dans un futur lointain et hypothétique, d'isoler plus facilement certains modules si le besoin s'en fait sentir.

### Négatives / Compromis
- **Couplage fort :** L'ensemble des modules partage la même base de données et le même cycle de vie de déploiement (application unique). Ce compromis est pleinement assumé s'agissant d'un projet d'apprentissage individuel.