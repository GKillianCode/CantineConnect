


| Métadonnée | Valeur                                          |
| :--- |:------------------------------------------------|
| **Référence** | `docs/adr/006-ddd-tactical-design-patterns.md`                               |
| **Statut** | Validé                                          |
| **Date** | 14 septembre 2026                               |
| **Auteur** | Killian GODET                                   |
| **Architecture** | Monolithe Modulaire (Spring Boot 3.x / Java 21) |

---

# Gouvernance d'Architecture avec ArchUnit

## Contexte
Dans un Monolithe Modulaire combiné à une Architecture Hexagonale, la porosité entre packages est un risque majeur (ex : importer du code `billing` dans `booking` ou ajouter des annotations Spring/JPA dans la couche `domain`). La discipline d'équipe ne suffit pas sur le long terme sans contrôles automatisés.

## Décision
Nous intégrons la bibliothèque **ArchUnit** dans la suite de tests automatisés (`mvn test`). Les règles suivantes sont vérifiées à chaque build et bloquent la compilation en cas de violation :

1. **Étanchéité des Bounded Contexts :** Le package `com.cantineconnect.booking` ne doit jamais importer `com.cantineconnect.inventory` ou `com.cantineconnect.billing` (et vice versa).
2. **Pureté de la couche Domaine :** Le package `domain` ne doit dépendre d'aucun framework externe (`org.springframework`, `jakarta.persistence`, etc.).
3. **Sens des dépendances hexagonales :** `infrastructure` dépend de `application` et `domain`. `application` dépend de `domain`. `domain` ne dépend de personne.

## Conséquences
* **Positives :**
  * Invalidation immédiate des dérives d'architecture sur le poste du développeur et dans la CI.
  * Refactoring sécurisé sans risque d'introduire un couplage sauvage.
* **Négatives / Compromis :**
  * Temps d'exécution minime ajouté à la phase de tests unitaires.
