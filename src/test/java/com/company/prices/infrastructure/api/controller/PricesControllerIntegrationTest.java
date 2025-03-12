package com.company.prices.infrastructure.api.controller;

import com.company.prices.infrastructure.api.dto.PriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PricesControllerIntegrationTest {

    private static final String HOST = "http://localhost:";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = HOST + port + "/prices/product/1?brandId=1&priceDate=";
    }

    @Test
    void givenGetPrices14thJune10AM_ShouldReturn3550price() {
        ResponseEntity<PriceResponse> response = getPriceByRequest("2020-06-14T10:00:00");
        PriceResponse result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertAll("Expected price returned",
                () -> assertEquals("1", result.getIdProducto()),
                () -> assertEquals("1", result.getIdBrand()),
                () -> assertEquals("1", result.getPriceList()),
                () -> assertEquals("2020-06-14T00:00:00",
                        result.getPriceStartDate().format(formatter)),
                () -> assertEquals("2020-12-31T23:59:59",
                        result.getPriceEndDate().format(formatter)),
                () -> assertEquals(new BigDecimal("35.50"), result.getPrice())
        );
    }

    @Test
    void givenGetPrices14thJune16PM_ShouldReturn2545price() {
        ResponseEntity<PriceResponse> response = getPriceByRequest("2020-06-14T16:00:00");
        PriceResponse result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertAll("Expected price returned",
                () -> assertEquals("1", result.getIdProducto()),
                () -> assertEquals("1", result.getIdBrand()),
                () -> assertEquals("2", result.getPriceList()),
                () -> assertEquals("2020-06-14T15:00:00",
                        result.getPriceStartDate().format(formatter)),
                () -> assertEquals("2020-06-14T18:30:00",
                        result.getPriceEndDate().format(formatter)),
                () -> assertEquals(new BigDecimal("25.45"), result.getPrice())
        );

    }

    @Test
    void givenGetPrices14thJune21PM_ShouldReturn3550price() {
        ResponseEntity<PriceResponse> response = getPriceByRequest("2020-06-14T21:00:00");
        PriceResponse result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertAll("Expected price returned",
                () -> assertEquals("1", result.getIdProducto()),
                () -> assertEquals("1", result.getIdBrand()),
                () -> assertEquals("1", result.getPriceList()),
                () -> assertEquals("2020-06-14T00:00:00",
                        result.getPriceStartDate().format(formatter)),
                () -> assertEquals("2020-12-31T23:59:59",
                        result.getPriceEndDate().format(formatter)),
                () -> assertEquals(new BigDecimal("35.50"), result.getPrice())
        );

    }

    @Test
    void givenGetPrices15thJune10AM_ShouldReturn3050price() {
        ResponseEntity<PriceResponse> response = getPriceByRequest(
                "2020-06-15T10:00:00");
        PriceResponse result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertAll("Expected price returned",
                () -> assertEquals("1", result.getIdProducto()),
                () -> assertEquals("1", result.getIdBrand()),
                () -> assertEquals("3", result.getPriceList()),
                () -> assertEquals("2020-06-15T00:00:00",
                        result.getPriceStartDate().format(formatter)),
                () -> assertEquals("2020-06-15T11:00:00",
                        result.getPriceEndDate().format(formatter)),
                () -> assertEquals(new BigDecimal("30.50"), result.getPrice())
        );
    }

    @Test
    void givenGetPrices16thJune21PM_ShouldReturn3895price() {
        ResponseEntity<PriceResponse> response = getPriceByRequest(
                "2020-06-16T21:00:00");
        PriceResponse result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertAll("Expected price returned",
                () -> assertEquals("1", result.getIdProducto()),
                () -> assertEquals("1", result.getIdBrand()),
                () -> assertEquals("4", result.getPriceList()),
                () -> assertEquals("2020-06-15T16:00:00",
                        result.getPriceStartDate().format(formatter)),
                () -> assertEquals("2020-12-31T23:59:59",
                        result.getPriceEndDate().format(formatter)),
                () -> assertEquals(new BigDecimal("38.95"), result.getPrice())
        );
    }


    private ResponseEntity<PriceResponse> getPriceByRequest(String priceDate) {
        String request = baseUrl + priceDate;
        return restTemplate.getForEntity(request, PriceResponse.class);
    }

}
