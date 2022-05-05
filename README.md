# Springboot Application

Springboot Application was built and created for a university assignment, practising the concepts of Domain Driven Design. The application itself is an Online Ordering Application which contains four individual microservices.

## Business

The **business** microservice contains the business logic microservice which handles order requests made by the **order** microserivce.

## Customer

The **customer** microservice purpose is for creating and managing customers.

## Product

The **product** microservice purpose is for creating and managing products.

## Order

The **order** microservice creates orders as well has handles domain logic. Domain logic differs from business logic.

# Getting Started

### Requirements

Spring Boot 2.5.4 requires [Java 8](https://www.java.com/en/) and is compatiable up to and including Java 16.

Explicit build support is provided for the following build tools:

| Build Tool | Version               |
| ---------- | --------------------- |
| Maven      | 3.5+                  |
| Gardle     | 6.8.x, 6.9.x, and 7.x |

#### Kafka

Kafka is required as host as a local server. This can be found here [Apache Downloads](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.0/kafka_2.12-3.1.0.tgz)

#### IDE with Maven Support

Recommended: IntelliJ Community Edition

## Installation

This installation presumes you know how to setup Kafka.

1. `git clone https://github.com/peteqian/Springboot-Application.git`

2. Import maven projects from your IDE

3. Download maven sources

## Usage

1. Run kafka server

2. Build the main file located in the `src\main\java` for each microservice

3. Run each main file

## Appendix

The **Description.pdf** contains the report made as part of the assignment.
