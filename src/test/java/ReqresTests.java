import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresTests extends TestBase {

    @Test
    public void SuccessfulRegTest() {
        String data = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .log().uri()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void SuccessfulRegWithKeyTest() {
        String data = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .log().uri()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("token"));
    }

    @Test
    public void unSuccessfulRegWithKeyTest() {
        String data = "{\"email\": \"sydney@fife\"}";
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .log().uri()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("$", hasKey("error"));

    }

    @Test
    public void unSuccessfulRegTest() {
        String data = "{\"email\": \"sydney@fife\"}";
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .log().uri()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void updateSuccessfulJobTest() {
        String data = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .log().uri()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void updateSuccessfulNameTest() {
        String userData = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .body(userData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"));
    }

    @Test
    void singleUserTest() {
        given()
                .contentType(JSON)
                .log().uri()
                .log().body()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"));
    }
}

