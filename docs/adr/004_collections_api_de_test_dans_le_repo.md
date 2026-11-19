# ADR 004 : Utilisation de Bruno et collections API versionnées dans le dépôt

- **Date :** 2026-11-19
- **Statut :** Accepté
- **Domaine :** Outillage / Workflow de test API

## Contexte
Dès l'issue en cours, plusieurs endpoints clés sont exposés (création de compte, authentification avec gestion du token/cookie JWT, et liaison d'un élève via son code unique et sa date de naissance).
S'appuyer sur un outil cloud externe ou conserver des requêtes isolées hors du dépôt rendrait les tests manuels moins reproductibles et moins alignés avec le code source.

## Décision
Il a été décidé d'utiliser **Bruno** en complément de Postman et de versionner les collections de requêtes API directement au sein du dépôt (dans un dossier dédié à la racine) :
- **Collections dans Git :** Les requêtes et scénarios d'appel API suivent l'évolution du code et des branches, sans dépendance à un espace de travail cloud externe.
- **Variables partagées :** Les paramètres communs (tels que l'URL de base ou le token/cookie JWT d'authentification) sont centralisés via des variables de collection/environnement afin d'être réutilisés automatiquement par les différents endpoints protégés.
- **Hygiène des secrets :** Les fichiers versionnés ne doivent contenir ni secrets de production, ni mots de passe sensibles, ni tokens réels figés.

## Conséquences

### Positives
- **Reproductibilité locale :** N'importe qui récupérant le projet dispose immédiatement des endpoints prêts à être testés localement, hors ligne et sans compte tiers.
- **Ergonomie sur les parcours authentifiés :** Mettre à jour une seule variable (comme le token/cookie JWT) propage automatiquement l'authentification aux requêtes suivantes.
- **Cohérence avec la branche/issue :** Les ajouts ou ajustements de requêtes peuvent évoluer en même temps que les endpoints correspondants.

### Négatives / Compromis
- **Maintenance de la collection :** Les fichiers de collection doivent être tenus à jour lorsque les contrats d'API (routes, DTOs, mécanismes d'auth) évoluent.
- **Risque d'oubli de variables sensibles :** Il faut rester vigilant à ne pas committer de valeurs confidentielles dans les fichiers d'environnement partagés.