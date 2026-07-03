**Cantine Connect**
_Système d'information de Restauration Scolaire - Projet factice_

---

# Spécification Fonctionnelles Générales

Introduction Générale - Vue d'ensemble du projet

---

| Référence       | SFG-CC_introduction                                       |
| --------------- | --------------------------------------------------------- |
| Version         | v1.0                                                      |
| Status          | Brouillon                                                 |
| Date            | 01/07/2026                                                |
| Commanditaire   | Région Centre-Val de Loire                                |
| Direction       | Direction de l'Education et de la Restauration Scolaire   |
| Auteur(s)       | Killian GODET - Développeur Backend & Solutions Digitales |
| Validation      | Killian GODET - Développeur Backend & Solutions Digitales |
| Confidentialité | Interne                                                   |

---

**Historique des révision**

| Version | Date       | Auteur        | Objet de la modification |
| ------- | ---------- | ------------- | ------------------------ |
| 0.1     | 01/07/2026 | Killian Godet | Création initiale        |
|         |            |               |                          |

---

**Table des matière**

## Table des matières

- [Spécification Fonctionnelles Générales](#spécification-fonctionnelles-générales)
  - [Table des matières](#table-des-matières)
  - [1. Présentation du projet](#1-présentation-du-projet)
    - [1.1 Contexte et genèse du projet](#11-contexte-et-genèse-du-projet)
    - [1.2 Vision et ambition](#12-vision-et-ambition)
    - [1.3 Objectifs stratégiques](#13-objectifs-stratégiques)
  - [2. Périmètre global du système](#2-périmètre-global-du-système)
    - [2.1 Dans le périmètre (IN)](#21-dans-le-périmètre-in)
    - [2.2 Hors périmètre (OUT)](#22-hors-périmètre-out)
    - [2.3 Hypothèses et contraintes globales](#23-hypothèses-et-contraintes-globales)
  - [3. Architecture fonctionnelle - les 4 modules](#3-architecture-fonctionnelle---les-4-modules)
    - [3.1 Vue d'ensemble des mobules](#31-vue-densemble-des-mobules)
    - [3.2 Interactions entre modules](#32-interactions-entre-modules)
    - [3.3 Systèmes et acteurs externes](#33-systèmes-et-acteurs-externes)
  - [4. Parties prenantes](#4-parties-prenantes)
    - [4.1 Tableau des parties prenantes](#41-tableau-des-parties-prenantes)
    - [4.2 Matrice RACI Synthétique](#42-matrice-raci-synthétique)
  - [5. Exigences non fonctionnelles transverses](#5-exigences-non-fonctionnelles-transverses)
  - [6. Glossaire et références](#6-glossaire-et-références)
    - [6.1 Glossaire](#61-glossaire)
    - [6.2 Documents de référence](#62-documents-de-référence)

---

## 1. Présentation du projet

### 1.1 Contexte et genèse du projet

...

### 1.2 Vision et ambition

...

### 1.3 Objectifs stratégiques

| Objectif                            | Description | Indicateur de succès |
| ----------------------------------- | ----------- | -------------------- |
| Fluidifier le parcours famille      |             |                      |
| Réduire le gaspillage alimentaire   |             |                      |
| Automatiser la facturation          |             |                      |
| Valoriser le savoir-faire culinaire |             |                      |

---

## 2. Périmètre global du système

### 2.1 Dans le périmètre (IN)

...

### 2.2 Hors périmètre (OUT)

...

### 2.3 Hypothèses et contraintes globales

| Catégorie  | Description |
| ---------- | ----------- |
| Hypothèse  |             |
| Contrainte |             |

---

## 3. Architecture fonctionnelle - les 4 modules

### 3.1 Vue d'ensemble des mobules

| Module | Intitulé                      | Acteurs principaux                 | Réf. SFG   |
| ------ | ----------------------------- | ---------------------------------- | ---------- |
| M1     | Portail Famille               | Parents, enfants et administration | SFG-CC-XXX |
| M2     | Restauration & Recettes       | Employés de cuisine, gestionnaire  | SFG-CC-XXX |
| M3     | Logistique & Stocks           | Gestionnaires de stocks, chefs     | SFG-CC-XXX |
| M4     | Administration et Facturation | Mairie, comptable                  | SFG-CC-XXX |

### 3.2 Interactions entre modules

Description textuelle des flux transverses :

- ...

### 3.3 Systèmes et acteurs externes

| Système / Acteur externe      | Nature de l'interaction                 | Module(s) concerné (s) |
| ----------------------------- | --------------------------------------- | ---------------------- |
| Si établissement              | Lecture de la liste des élèves          | M4                     |
| Si maires (quotient familial) | Lecture des données de tarification     | M1, M4                 |
| Comptabilité                  | Export fichier de facturation mensuelle | M4                     |

---

## 4. Parties prenantes

### 4.1 Tableau des parties prenantes

| Partie prenante            | Rôle dans le projet                          | Modules concernés |
| -------------------------- | -------------------------------------------- | ----------------- |
| Région Centre-Val de Loire | Commanditaire, validation stratégique        | Tous              |
| Direction de l'Education   | Maîtrise d'ouvrage, validation fonctionnelle | Tous              |
| Mairies / Communes         | Paramétrage tarifaire, réception facturation | M1, M4            |
| Parents / Familles         | Utilisateurs du portail famille              | M1                |
| Chef de cuisine            | Gestion des recettes et menu                 | M2                |

### 4.2 Matrice RACI Synthétique

| Activité clé               | MOA (Région) | Mairies | Métier | MOE |
| -------------------------- | ------------ | ------- | ------ | --- |
| Validation des SFG         | A            | C       | C      | R   |
| Validation des SFD         | A            | C       | C/R    | R   |
| Développement              | I            | -       | C      | R   |
| Recette fonctionnelle      | I            | C       | A/R    | R   |
| Paramétrage tarifaire (M4) | I            | A/R     | -      | I   |
| Mise en production         | A            | I       | I      | R   |

## 5. Exigences non fonctionnelles transverses

| Catégorie              | Exigence globale |
| ---------------------- | ---------------- |
| Performance            |                  |
| Disponibilité          |                  |
| Sécurité               |                  |
| Protection des données |                  |
| Accessibilité          |                  |
| Compatibilité          |                  |
| Maintenabilité         |                  |
| Internationnalisation  |                  |

## 6. Glossaire et références

### 6.1 Glossaire

| Terme             | Définition                                                                                                                                                         |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| PAI               | Projet d'Accueil Individualisé - document médical officialisant le protocole d'accueil d'un enfant allergique ou porteur d'un trouble de santé en milieu scolaire. |
| Quotient familial | Indicateur calculé par les mairies permettant de déterminer le tarif du repas applicable à une famille en fonction de ses revenus.                                 |
| FEPS              | Premier Expiré, Premier Sorti - méthode de gestion des stocks consistant à utiliser en priorité les ingrédients dont la DLC est la plus proche.                    |
| DLC               | Date Limite de Consommation - date au-delà de laquelle un aliment<br>ne peut légalement plus être consommé.                                                        |
| Cuisine centrale  | Établissement de cuisine produisant les repas pour plusieurs établissements scolaires d'une même commune ou zone géographique.                                     |
| SFG               | Spécifications Fonctionnelles Générales - document de niveau macro décrivant les grandes fonctions et le périmètre d'un module.                                    |
| SFD               | Spécifications Fonctionnelles Détaillées - document de niveau fin décrivant les cas d'usage, règles métier et comportements précis du système.                     |
| MOA               | Maîtrise d'ouvrage                                                                                                                                                 |
| MOE               | Maître d'oeuvre                                                                                                                                                    |

### 6.2 Documents de référence

| Document                                    | Référence                          |
| ------------------------------------------- | ---------------------------------- |
| Cahier des charges initial                  | cahier-des-charges.pdf             |
| Cahier des charges annotés                  | cahier-des-charges-annote-v1.0.pdf |
|                                             |                                    |
| SFG Module 1 - Portail Famille              |                                    |
| SFG Module 2 - Restauration & Recettes      |                                    |
| SFG Module 3 - Logistique & Stocks          |                                    |
| SFG Module 4 - Administration & Facturation |                                    |
|                                             |                                    |
| SFD Module 1 - Portail Famille              |                                    |
| SFD Module 2 - Restauration & Recettes      |                                    |
| SFD Module 3 - Logistique & Stocks          |                                    |
| SFD Module 4 - Administration & Facturation |                                    |
|                                             |                                    |
