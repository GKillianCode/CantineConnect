


| Métadonnée | Valeur                                          |
| :--- |:------------------------------------------------|
| **Référence** | `docs/adr/004.md`                               |
| **Statut** | Validé                                          |
| **Date** | 14 septembre 2026                               |
| **Auteur** | Killian GODET                                   |
| **Architecture** | Monolithe Modulaire (Spring Boot 3.x / Java 21) |

---

# Stratégie de Test et Adoption du TDD pour le Domaine

## Contexte
Le cœur métier de CantineConnect (couche `domain`) héberge des règles critiques (invariants $J-2$, blocages PAI, algorithme FEPS). Pour garantir la qualité logicielle et éviter les régressions, nous devons définir une stratégie de test claire, adaptée à l'Architecture Hexagonale.

## Décision
1. **TDD obligatoire pour le Domaine :** Développement guidé par les tests (cycle *Red-Green-Refactor*) pour l'intégralité du code du package `domain`.
2. **Outillage léger :** Utilisation exclusive de **JUnit 5** et **AssertJ** pour le domaine.
3. **Interdiction de Mockito dans le Domaine :** Les tests de la couche `domain` instancient de vrais objets Java. L'usage de stubs ou de mocks via Mockito est **strictement réservé** aux couches `application` et `infrastructure` (pour doubler les ports de sortie ou les composants techniques).
4. **Couverture :** Mesure automatique de la couverture de code via le plugin Maven JaCoCo.

## Conséquences
* **Positives :**
  * Exécution ultra-rapide des tests du domaine (quelques millisecondes, sans contexte Spring).
  * Objets du domaine conçus autour des comportements métier plutôt que des données.
  * Absence de faux positifs liés à des configurations de mocks complexes dans le domaine.
* **Négatives / Compromis :**
  * Nécessite de créer manuellement des données de test (Fixtures / Builders) en pure Java.
