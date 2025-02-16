# Prices API

## Description

The Prices API is a Spring Boot microservice that provides RESTful endpoints to query product prices based on brand and
date. It follows the hexagonal architecture pattern to ensure a clean separation of concerns and maintainability.

## Project Structure

The project is organized into the following packages:

- `com.company.prices.domain.entity`: Contains the core business entities (e.g., `Price`, `Brand`).
- `com.company.prices.application.port`: Defines the interfaces (ports) for external systems.
- `com.company.prices.application.services`: Contains the use case implementations (e.g., `FindPricesService`).
- `com.company.prices.infraestructure.api.controller`: Contains the REST controllers (e.g., `PricesController`).
- `com.company.prices.infraestructure.api.dto`: Contains the data transfer objects (DTOs) (e.g., `PriceResponse`).
- `com.company.prices.infraestructure.api.exception`: Contains custom exceptions (e.g., `PriceNotFoundException`).
- `com.company.prices.infraestructure.api.mappers`: Contains the mappers for converting entities to DTOs (e.g.,
  `PricesMapper`).
- `com.company.prices.infraestructure.api.advice`: Contains global exception handlers (e.g., `GlobalExceptionHandler`).
- `com.company.prices.infraestructure.persistence.adapter`: Contains the adapters for persistence (e.g.,
  `PricesPersistenceAdapter`).
- `com.company.prices.infraestructure.persistence.jpa`: Contains the JPA repositories (e.g., `PricesRepository`).

## Prerequisites

- Java 17
- Maven 3.6+
- IntelliJ IDEA or any other preferred IDE

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/your-username/prices-api.git
cd prices-api
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

1. Ensure the application is running. You can start the application using IntelliJ IDEA or the command line as described
   in the "Running the Application" section.
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

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.