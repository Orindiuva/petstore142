import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class TestPet {
    
    static String ct = "application/json"; //content-type
    static String uriUser = "https://petstore.swagger.io/v2/pet";


    public static String lerArquivoJson(String arquivoJson) throws IOException{                 
         return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test
    public void testPostPet() throws IOException{
        //carregar os dados do arquivo JSON do pet
        String jsonBody = lerArquivoJson("src/test/resources/json/pet1.json");
        boolean tst = jsonBody == null; 
        System.out.println("boolean: " + tst);
        int petId = 173998;
        given().
            contentType(ct).
            log().all().                                         //mostra tudo na ida
            body(jsonBody)                                       //envia o corpo da requisicao
        .when()
            .post(uriUser)                                      //Chamamos o endpoint com o corpo da requisicao
        .then()
            .log().all()                                        //mostra tudo na volta
            .statusCode(200)
            .body("name", is("snoopy"))
            .body("id", is(petId))
            .body("category.name", is("cachorro"))
            .body("tags[0].name", is("vacinado"));        
    }
}
