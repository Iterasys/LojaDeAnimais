package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Store {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void venderPet() throws IOException {
        String jsonBody = lerJson("data/order.json");

        given()
                .log().all()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .post("https://petstore.swagger.io/v2/store/order")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(6401))
                .body("status", is("placed"))
                .body("complete", is(true))
        ;
    }

}
