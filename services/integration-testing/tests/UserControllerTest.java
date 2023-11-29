import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserControllerTest {

    @Test
    public void testEmptyUserArray() {
        given()
            .baseUri("http://gateway:8080")
            .basePath("/api/users")
            .header("accept", "application/json")
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("$", empty());
    }

    @Test
    public void testUserNotFound() {
        given()
            .baseUri("http://gateway:8080")
            .basePath("/api/users")
            .header("accept", "application/json")
        .when()
            .get("/11f83625-06d7-47f7-887c-e5de5a428183") 
        .then()
            .statusCode(404);
    }
    
}
