package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Servico {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Create / Incluir / POST
    @Test
    public void IncluirPet() throws IOException {
        // Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("data/pet.json");

        given()                                 // Dado que
            .contentType("application/json")    // Tipo de conteúdo da requisição
                                                // "text/xml" para web services comuns
                                                // "application/json" para APIs REST
            .log().all()                        // Gerar um log completo da requisição
            .body(jsonBody)                     // Conteúdo do corpo da requisição
        .when()                                 // Quando
            .post("https://petstore.swagger.io/v2/pet") // Operação e endpoint
        .then()                                 // Então
            .log().all()                        // Gerar um log completo da resposta
            .statusCode(200)                    // Validou o código de status da requisição como 200
            // .body("code", is(200))  // Valida o code como 200
            .body("id", is(4201))    // Validou a tag id com o conteúdo esperado
            .body("name", is("Garfield")) // Validou a tag nome como Garfield
            .body("tags.name", contains("adoption")) // Validou a tag Name filha da tag Tags
        ;
        System.out.print("Executou o serviço");


    }

}
