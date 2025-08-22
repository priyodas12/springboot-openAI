# Getting Started

Spring Boot + OpenAI Embeddings + PostgreSQL (pgvector)

This project demonstrates how to create semantic embeddings using OpenAI (or local models) and store them in PostgreSQL
with pgvector.
It allows you to perform semantic search based on cosine similarity.

## Tech Stack

#### Java 21, Spring Boot 3.x

#### Maven

#### PostgreSQL + pgvector

#### OpenAI Embeddings API (or local embedding models with GPU)

```aiignore
curl --location 'http://localhost:8080/open-api/v1/embeddings' \
--header 'Content-Type: application/json' \
--data '{
    "texts": [
    "k8s","kubernetes","pods","service","deployment","helm","log4j"
    ]
}'

curl --location 'http://localhost:8080/open-api/v1/search?query=functional%20interface&limit=5'


curl --location 'http://localhost:8080/open-ai/eligible/models'



```

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.4/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.4/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.4/reference/using/devtools.html)
* [OpenAI](https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html) - get apiKey,projectKey,organizationKey
  from here
* [Spring Web](https://docs.spring.io/spring-boot/3.5.4/reference/web/servlet.html)

## Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

## Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

