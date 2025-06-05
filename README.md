# SafetyNet Alerts API

[![forthebadge](http://forthebadge.com/images/badges/built-with-love.svg)](http://forthebadge.com)  
[![forthebadge](http://forthebadge.com/images/badges/powered-by-electricity.svg)](http://forthebadge.com)

Une API REST permettant de centraliser et exploiter les informations d’un service d’alerte en cas d’urgence, développée avec Spring Boot 3 et Java 21. Elle gère les résidents, les casernes de pompiers et les dossiers médicaux afin de répondre rapidement à des situations critiques.

---

## Pour commencer

Ces instructions vous permettront d'obtenir une copie du projet opérationnelle sur votre machine locale à des fins de développement et de test.

### Pré-requis

- Java 21
- Maven 3.x
- Git

### Installation

Clonez le dépôt :

```bash
git clone https://github.com/braseb/brault-sebastien-SafetyNetAlerts.git
cd brault-sebastien-SafetyNetAlerts
```

Lancez l’application :

```bash
mvn spring-boot:run
```

Accédez ensuite à :

- Swagger UI : [http://localhost:9000/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Démarrage

Pour lancer les tests :

```bash
mvn test
```

Chemin rapports test :

- **Couverture (Jacoco)** : `target/site/jacoco/index.html`

```bash
mvn surefire-report:report
```

- **Tests (Surefire)** : `target/site/surefire-report.html`  

---

## Fabriqué avec

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Log4j2](https://logging.apache.org/log4j/2.x/)
* [Springdoc OpenAPI](https://springdoc.org/)
* [Gson](https://github.com/google/gson)
* [Jacoco](https://www.eclemma.org/jacoco/)
* [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/)

---


## Auteurs

* **Brault Sébastien**  
GitHub : [@braseb](https://github.com/braseb)

Lisez la liste des [contributeurs](https://github.com/braseb/brault-sebastien-SafetyNetAlerts/contributors) pour voir qui a participé au projet.

---

## License

Ce projet est sous licence MIT - voir le fichier [LICENSE.md](LICENSE.md) pour plus d’informations.

---

## Endpoints principaux

### 🔍 Endpoints d'information

| Méthode | URL | Description |
|--------|-----|-------------|
| `GET` | `/firestation?stationNumber=<num>` | Personnes desservies par une caserne |
| `GET` | `/childAlert?address=<adresse>` | Enfants à une adresse + adultes |
| `GET` | `/phoneAlert?firestation=<num>` | Numéros des résidents liés à une caserne |
| `GET` | `/fire?address=<adresse>` | Infos santé + caserne d’un foyer |
| `GET` | `/flood/stations?stations=<nums>` | Infos par casernes sur plusieurs foyers |
| `GET` | `/personInfo?lastName=<nom>` | Infos détaillées d’une personne |
| `GET` | `/communityEmail?city=<ville>` | Emails de tous les résidents d’une ville |

Endpoints principaux
🔍 Endpoints d'information

    GET /firestation?stationNumber=<num>
    Retourne la liste des personnes couvertes par la caserne numéro <num>
    Inclut prénom, nom, adresse, téléphone, et décompte adultes/enfants.

    GET /childAlert?address=<adresse>
    Liste des enfants (≤ 18 ans) à cette adresse avec âge et autres membres du foyer.

    GET /phoneAlert?firestation=<num>
    Numéros de téléphone des résidents desservis par la caserne <num>.

    GET /fire?address=<adresse>
    Liste des habitants à cette adresse, leur numéro de caserne, téléphone, âge, antécédents médicaux.

    GET /flood/stations?stations=<liste_num>
    Liste des foyers desservis par plusieurs casernes, groupés par adresse avec infos détaillées.

    GET /personInfo?lastName=<nom>
    Informations complètes des personnes portant ce nom.

    GET /communityEmail?city=<ville>
    Adresses mail de tous les habitants de la ville.

### ✏️ Endpoints CRUD

#### `/person`
- `POST` : Créer une personne
- `PUT` : Mettre à jour une personne
- `DELETE` : Supprimer une personne

#### `/firestation`
- `POST` : Associer une adresse à une caserne
- `PUT` : Mettre à jour le numéro d’une caserne
- `DELETE` : Supprimer une association adresse ↔ caserne

#### `/medicalRecord`
- `POST` : Ajouter un dossier médical
- `PUT` : Modifier un dossier médical
- `DELETE` : Supprimer un dossier médical

---

## Structure du projet

```
SafetyNetAlerts/
├── src/
│   ├── main/
│   │   ├── java/                  # Code source
│   │   └── resources/             # Configurations
│   └── test/
│       └── java/                  # Tests
├── target/                        # Build output
├── pom.xml                        # Dépendances Maven
└── README.md                      # Fichier de documentation
```

---

## Documentation Swagger

Disponible à l’adresse :  
📚 [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html)

Permet de tester tous les endpoints directement via une interface web.

---

