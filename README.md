# FileParser
A basic implementation of a file parser to convert the contents of the file and
store the records into a database

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Reference Documentation](#reference-documentation)
- [Guides](#guides)

## Features

- Retrieve an account profile by name and surname.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring MVC
- H2 Database (in-memory database for development)
- Maven (for build and dependency management)

## Getting Started

To get started with the Account Profile Management System, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/account-profile-management.git

2. Navigate to the project directory:

   ```bash
   cd file_parser

3. Build the project using Maven:

    ```bash
    mvn clean install

4. Run the application:

   ```bash
   mvn spring-boot:run

5. Access the application locally:

    > Open your web browser and visit http://localhost:8080 to access the application.

## Usage

- Retrieve `httpImageLink` that will give you access to the account profile's physical image:

    > Send a `GET` request to `/v1/api/image/{name}/{surname}/{\\w\.\\w}` with the name and surname parameters, and image format.


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

