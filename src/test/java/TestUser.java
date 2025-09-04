import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class TestUser {
    
    static String ct = "application/json"; //content-type
    static String uriUser = "https://petstore.swagger.io/v2/user";

    @Test
    public static String testLogin(){
        String userName = "charlie";
        String password = "abcdef";
        String resultadoEspereado = "logged in user session:";

        Response resposta = (Response) given()
            .contentType(ct)
            .log().all()
        .when()
            .get(uriUser + "/login?username=" + userName + "&password=" + password)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", containsString(resultadoEspereado))
            .body("message", hasLength(36))
        .extract(); 
        //extracao
        String token = resposta.jsonPath().getString("message").substring(23);
        System.out.println("Conteudo do Token:" + token);
        return token;

    }
}
