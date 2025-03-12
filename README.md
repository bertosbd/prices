# Prices API

## Description

The Prices API is a Spring Boot microservice that provides RESTful endpoints to query product prices based on brand and
date. It follows the hexagonal architecture pattern to ensure a clean separation of concerns and maintainability.

## Project Structure

The project is organized into the following packages:

- `com.company.prices.domain.entity`: Contains the core business entities.
- `com.company.prices.domain.usecase`: Contains the use case interfaces (e.g., `FindPricesUseCase`).
- `com.company.prices.domain.port`: Defines the interfaces (ports) for external systems (e.g., `PricePersistencePort`).
- `com.company.prices.application.services`: Contains the use case implementations (e.g., `FindPricesService`).
- `com.company.prices.infrastructure.api.controller`: Contains the REST controllers (e.g., `PricesController`).
- `com.company.prices.infrastructure.api.exception`: Contains custom exceptions (e.g., `PriceNotFoundException`).
- `com.company.prices.infrastructure.api.mappers`: Contains the mappers for converting domain entities to DTOs (e.g.,
  `PricesMapper`).
- `com.company.prices.infrastructure.api.advice`: Contains global exception handlers (e.g., `GlobalExceptionHandler`).
- `com.company.prices.infrastructure.persistence.adapter`: Contains the adapters for persistence (e.g.,
  `PricesPersistenceAdapter`).
- `com.company.prices.infrastructure.persistence.jpa`: Contains the JPA repositories (e.g., `PricesRepository`).
- `com.company.prices.infrastructure.persistence.mappers`: Contains the mappers for converting JPA to domain entities (
  e.g., `PriceEntityMapper`).

## Prerequisites

- Java 17
- Maven 3.6+
- IntelliJ IDEA or any other preferred IDE

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/bertosbd/prices.git
cd prices
```

### Build the Project

```sh
mvn clean install
```

## Running the Application

### Using IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Click on `File > Open` and select the `prices-api` project directory.
3. Wait for IntelliJ to import the project and download the dependencies.
4. Navigate to `src/main/java/com/company/prices/PricesApiApplication.java`.
5. Right-click on `PricesApiApplication` and select `Run 'PricesApiApplication'`.

### Using Command Line

1. Open a terminal and navigate to the project directory.
2. Run the following command to start the application:

```sh
mvn spring-boot:run
```

## Docker

### Building the Docker Image

To build the Docker image for the Prices API, run the following command in the root directory of the project (where the
`Dockerfile` is located):

```sh
docker build -t prices-api .
```

### Running the Docker Container

To run the Docker container, use the following command:

```sh
docker run -d -p 8080:8080 --name prices-api-container prices-api
```

This command will start the container in detached mode and map port 8080 of the container to port 8080 on your host
machine.

### Stopping the Docker Container

To stop the running Docker container, use the following command:

```sh
docker stop prices-api-container
```

### Removing the Docker Container

To remove the Docker container, use the following command:

```sh
docker rm prices-api-container
```

### Accessing the Application

Once the container is running, you can access the Prices API at:

```
http://localhost:8080
```

## API Endpoints

The following endpoints are available:

- `GET /prices/product/{productId}`: Get product price by brand and date.
    - Parameters:
        - `brandId` (required): The brand identifier.
        - `priceDate` (required): The date for which the price is queried.

## Example Request

```sh
curl -X GET "http://localhost:8080/prices/product/1?brandId=1&priceDate=2020-06-14T10:00:00"
```

## Error Handling

The API uses custom exception handling to return meaningful error messages. The following error responses are possible:

- `404 Not Found`: Price not found for the given product, brand, and date.
- `400 Bad Request`: Invalid parameter type or missing parameters.
- `500 Internal Server Error`: Unexpected server error.

## Accessing Swagger UI

The Prices API includes Swagger UI for easy API documentation and testing. Swagger UI provides a web-based interface to
interact with the API endpoints.

### Accessing Swagger UI

1. Ensure the application is running. You can start the application using Docker, IntelliJ IDEA and the command line as
   described in the "Running the Application" section.
2. Open a web browser and navigate to the following URL:

```
http://localhost:8080/swagger-ui.html
```

### Using Swagger UI

1. Once you access the Swagger UI, you will see a list of available API endpoints.
2. Click on an endpoint to expand its details.
3. You can see the parameters required for the endpoint, and you can fill in the values directly in the UI.
4. Click the "Try it out" button to execute the request and see the response.

Swagger UI makes it easy to explore and test the API without the need for external tools.

## Integration Tests

Integration tests are located in the `src/test/java/com/company/prices/infraestructure/api/controller` directory. These
tests ensure that the API endpoints work correctly with the full application context.

## Postman Collection

A Postman collection is included in the project to facilitate testing the API endpoints.

### Importing the Postman Collection

1. Open Postman.
2. Click on `File > Import`.
3. Select the `Prices API.postman_collection.json` file located in the project directory.
4. Click `Import`.

### Using the Postman Collection

1. After importing, you will see the `Prices API` collection in Postman.
2. The collection includes predefined requests to test required scenarios.
3. Select a request and click the `Send` button to execute it.
4. You can modify the request parameters and headers as needed.

## OpenAPI Specification

The OpenAPI specification for the Prices API is located in the `src/main/resources/spec` directory. This file provides a
detailed description of the API endpoints, request/response formats, and other relevant information.

### Accessing OpenAPI Specification

You can access the OpenAPI specification via the following URL:

```
http://localhost:8080/v3/api-docs
```
