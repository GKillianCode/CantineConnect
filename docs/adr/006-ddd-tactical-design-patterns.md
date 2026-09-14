| Métadonnée | Valeur                                          |
| :--- |:------------------------------------------------|
| **Référence** | `docs/adr/004-testing-strategy-tdd.md`                               |
| **Statut** | Validé                                          |
| **Date** | 14 septembre 2026                               |
| **Auteur** | Killian GODET                                   |
| **Architecture** | Monolithe Modulaire (Spring Boot 3.x / Java 21) |

---

# Motifs Tactiques du Domain-Driven Design (DDD)

## Contexte
Chaque module de CantineConnect doit manipuler des identifiants et des données partagées (notamment l'élève) sans violer l'autonomie des Bounded Contexts ni créer de couplage fort en base de données.

## Décision
Nous adoptons trois motifs tactiques DDD au sein du projet :

1. **Shared Kernel (Noyau Partagé) minimal :** Création du package `com.cantineconnect.shared.domain` contenant uniquement des types universels immutables, en particulier le Value Object `StudentId`.
2. **Immutabilité via les `record` Java 21 :** Tous les Value Objects et Événements de Domaine sont implémentés sous forme de `record` natifs.
3. **Autonomie des bases de données :** Aucune clé étrangère SQL (`FOREIGN KEY`) n'existe entre les tables de modules différents. L'association s'effectue uniquement par identifiant logique (`student_id`).
4. **Data Mapping systématique :** Séparation stricte entre les Agrégats du domaine et les entités de persistance (`@Entity` JPA). La conversion est assurée par des mappers dédiés dans la couche `infrastructure`.

## Conséquences
* **Positives :**
  * Alignement fort sur le modèle métier et garanties d'immutabilité natives en Java 21.
  * Indépendance totale du stockage SQL par module.
* **Négatives / Compromis :**
  * Nécessite d'écrire des mappers pour traduire les objets entre les couches.
