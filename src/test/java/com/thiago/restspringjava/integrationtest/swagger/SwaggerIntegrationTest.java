package com.thiago.restspringjava.integrationtest.swagger;

import com.thiago.restspringjava.config.TestConfig;
import com.thiago.restspringjava.integrationtest.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldDisplaySwaggerUiPage(){
        var content =
            given()
                    .basePath("/swagger-ui/index.html")
                    .port(TestConfig.SERVER_PORT)
                    .when().get()
                    .then().statusCode(200)
                    .extract().body().asString();

        Assertions.assertTrue(content.contains("Swagger UI"));
    }
}
