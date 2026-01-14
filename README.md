# Microservice de gestion des taches ======Todo Service=====

Microservice Spring Boot de gestion de tâches, développé selon une **architecture hexagonale (Clean Architecture)** avec règles métier centralisées, tests à plusieurs niveaux, documentation OpenAPI (Swagger) et dockerisation.

---

##  Architecture

Organisation en couches :

* **domain** : modèle métier + règles + exceptions
* **application** : cas d’usage + ports
* **exposition** : API REST (controllers + handlers)
* **infrastructure** : repository en mémoire
* **commons** : DTOs et objets partagés

Objectifs :

* séparation claire des responsabilités
* indépendance du domaine vis-à-vis des frameworks
* testabilité maximale

---

##  Lancement de l’application

### Lancement Avec Maven

```bash
mvn spring-boot:run
```

### ▶ Depuis IntelliJ

Lancer la classe :

```
TodoServiceApplication
```

###  Accès API

```
http://localhost:8082
```

---

##  Documentation API (Swagger)

Swagger UI :

```
http://localhost:8082/swagger-ui/index.html
```

OpenAPI JSON :

```
http://localhost:8082/v3/api-docs
```

---

##  Tests

###  Tous les tests

```bash
mvn test
```

### Types de tests

* Tests unitaires : Mockito (service applicatif)
* Tests d’intégration : SpringBootTest + TestRestTemplate
* Tests BDD : Cucumber

---

##  Tests manuels avec Postman

Base URL :

```
http://localhost:8082/api/tasks
```

---

###  POST — Créer une tâche

**POST** `/api/tasks`

Headers:

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

Body:

```json
{
  "label": "Faire les courses",
  "description": "Lait, pain, oeufs"
}
```

Réponse : **201 Created**

```json
{
  "id": "uuid",
  "label": "Faire les courses",
  "description": "Lait, pain, oeufs",
  "completed": false
}
```

---

###  GET — Toutes les tâches

**GET** `/api/tasks`

---

###  GET — Uniquement les tâches à faire

**GET** `/api/tasks?todoOnly=true`

---

###  GET — Par ID

**GET** `/api/tasks/{id}`

---

###  PATCH — Modifier le statut

**PATCH** `/api/tasks/{id}/status`

Headers:

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

Body:

```json
{ "completed": true }
```

---

##  Cas d’erreurs métier

###  Label trop court

```json
{ "label": "abc", "description": "x" }
```

→ 400 Bad Request

---

###  Doublon de label actif

Créer deux tâches avec le même label sans compléter la première.

→ 400 Bad Request

---

###  Plus de 10 tâches actives

Créer 11 tâches non complétées.

→ 400 Bad Request

---

##  Règles métier implémentées

* Label minimum : 5 caractères
* Maximum 10 tâches actives
* Pas de doublon de label actif
* Une tâche commence toujours à `completed = false`
* Une tâche complétée peut être réactivée

---

##  Docker

###  Build de l’image

```bash
docker build -t todo-service .
```

###  Lancer le container

```bash
docker run -p 8082:8082 --name todo-service -d todo-service
```

### ▶Lancement Avec Docker Compose

```bash
docker-compose up --build
```

Stop :

```bash
docker-compose down
```

---

###  Fichiers Docker

#### Dockerfile

```dockerfile
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### docker-compose.yml

```yaml
version: "3.8"
services:
  todo-service:
    build: .
    ports:
      - "8082:8082"
```

---

## La Stack Technique 

* Java 17
* Spring Boot 3
* Maven
* JUnit 5
* Mockito
* Cucumber
* Spring MVC REST
* Springdoc OpenAPI (Swagger)
* Docker

---

##  Choix d’architecture

* Domaine indépendant des frameworks
* Cas d’usage explicites
* API REST sans logique métier
* Exceptions métier centralisées
* DTOs isolés dans une couche commune

Objectif : code maintenable, testable et évolutif.

---

## Odrick NGOUAMA


