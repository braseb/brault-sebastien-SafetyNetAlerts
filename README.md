# SafetyNet Alerts API

[![forthebadge](http://forthebadge.com/images/badges/built-with-love.svg)](http://forthebadge.com)  
![Java Version](https://img.shields.io/badge/Java-21-blue)  
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.6-brightgreen)  


Une API REST permettant de centraliser et exploiter les informations d’un service d’alerte en cas d’urgence, développée avec Spring Boot 3 et Java 21. Elle gère les résidents, les casernes de pompiers et les dossiers médicaux afin de répondre rapidement à des situations critiques.

---

## Pour commencer

Ces instructions vous permettront d'obtenir une copie du projet opérationnelle sur votre machine locale à des fins de développement et de test.

### Pré-requis

- Java 21
- Maven 3.9.9
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

- Pour lancer les tests :

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

---

## License

Ce projet est sous licence MIT - voir le fichier [LICENSE.md](LICENSE.md) pour plus d’informations.

---

## Endpoints principaux

### 🔍 Endpoints d'information

| Méthode | URL | Description |
|--------|-----|-------------|
| `GET` | `/firestation?stationNumber=<station_number>` | Personnes desservies par une caserne |
| `GET` | `/childAlert?address=<adress>` | Enfants à une adresse + adultes |
| `GET` | `/phoneAlert?firestation=<station_number>` | Numéros des résidents liés à une caserne |
| `GET` | `/fire?address=<adresse>` | Infos santé + caserne d’un foyer |
| `GET` | `/flood/stations?stations=<list of station_numbers>` | Infos par casernes sur plusieurs foyers |
| `GET` | `/personInfo?lastName=<lastName>` | Infos détaillées d’une personne |
| `GET` | `/communityEmail?city=<city>` | Emails de tous les résidents d’une ville |


### ✏️ Endpoints CRUD

#### `/person`
- `POST` : `/person` : Créer une personne
- `PUT` : `/person/{lastName}/{firstName}` : Mettre à jour une personne
- `DELETE` : `/person/{lastName}/{firstName}` : Supprimer une personne

#### `/firestation`
- `POST` : `/firestation` : Associer une adresse à une caserne
- `PUT` : `/firestation/{address}` : Mettre à jour le numéro d’une caserne
- `DELETE` : `/firestation/station/{stationNumber}` : Supprimer une caserne par son numéro
- `DELETE` : `/firestation/address/{address}` : Supprimer une caserne par son adresse

#### `/medicalRecord`
- `POST` : `/medicalRecord` : Ajouter un dossier médical
- `PUT` : `/medicalRecord/{lastName}/{firstName}` : Modifier un dossier médical
- `DELETE` : `/medicalRecord/{lastName}/{firstName}` : Supprimer un dossier médical

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

