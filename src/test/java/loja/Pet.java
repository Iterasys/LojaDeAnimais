package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Pet {
    public String tokenGeral; // variavel para receber o token

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordemDaExecucao() throws IOException {
        incluirPet();
        consultarPet();
        alterarPet();
        excluirPet();
    }

    // Create / Incluir / POST
    @Test
    public void incluirPet() throws IOException {
        //String tokenLocal = loginUser2();
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

    // Reach or Research / Consultar / Get
    @Test
    public void consultarPet(){
        String petId = "4201";

        given()                                             // Dado que
            .contentType("application/json")                        // Tipo de conteúdo da requisição
            .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
            .get("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()                                             // Então
            .log().all()                                            // Mostrar tudo que foi recebido
            .statusCode(200)                                        // Validou que a operação foi realizada
            .body("name", is("Garfield"))                // Validou o nome do pet
            .body("category.name", is("cat"))            // Validou a espécie
        ;
    }

    // Update / Alterar / Put
    @Test
    public void alterarPet() throws IOException {
        // Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("data/petput.json");

        given()                                 // Dado que
            .contentType("application/json")    // Tipo de conteúdo da requisição
            // "text/xml" para web services comuns
            // "application/json" para APIs REST
                .log().all()                        // Gerar um log completo da requisição
                .body(jsonBody)                     // Conteúdo do corpo da requisição
        .when()                             // Quando
            .put("https://petstore.swagger.io/v2/pet")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Garfield"))
                .body("status", is("adopted"))
        ;

    }

    // Delete / Excluir / Delete
    @Test
    public void excluirPet(){
        String petId = "4201";

        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conteúdo da requisição
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
                .delete("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()
               .log().all()
               .statusCode(200)
                .body("code", is(200))
                .body("message",is(petId))
       ;

    }

    // Login
    @Test
    public void loginUser(){
        // public String loginUser(){

        String token =
        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conteúdo da requisição
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()
                .get("https://petstore.swagger.io/v2/user/login?username=charlie&password=brown")
        .then()
                .log().all()
                .statusCode(200)
                .body("message", containsString("logged in user session:"))
                .extract()
                .path("message")
        ;
        tokenGeral = token.substring(23); // separa o token da frase
        System.out.println("O token valido eh " + tokenGeral);
        //return tokenGeral;
    }

    @Test
    public void sempreMetodo(){
        loginUser2();
    }

    public String loginUser2(){

        String token =
                given()                                             // Dado que
                        .contentType("application/json")                        // Tipo de conteúdo da requisição
                        .log().all()                                            // Mostrar tudo que foi enviado
                        .when()
                        .get("https://petstore.swagger.io/v2/user/login?username=charlie&password=brown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("message", containsString("logged in user session:"))
                        .extract()
                        .path("message")
                ;
        String tokenGeral2 = token.substring(23); // separa o token da frase
        System.out.println("O token valido eh " + tokenGeral2);
        return tokenGeral2;
    }



}
