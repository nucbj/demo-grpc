# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.3/gradle-plugin/reference/html/#build-image)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/6.1.4/spring-framework-reference/languages.html#coroutines)
* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#messaging.amqp)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#data.sql.r2dbc)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#using.devtools)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#features.docker-compose)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#web)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#web.reactive)

### Guides

The following guides illustrate how to use some features concretely:

* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [Accessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [R2DBC Homepage](https://r2dbc.io)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* mysql: [`mysql:latest`](https://hub.docker.com/_/mysql)
* rabbitmq: [`rabbitmq:latest`](https://hub.docker.com/_/rabbitmq)

Please review the tags of the used images and set them to the same as you're running in production.

