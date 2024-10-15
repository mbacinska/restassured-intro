package pl.restassured.user;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserCreationTests {

  @BeforeClass
  public void setupConfiguration() {
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
  }

  @Test
  @Description("Body String: Check creation of user")
  @Severity(SeverityLevel.MINOR)
  public void shouldCreateUserAndCheckIfCreated() {

    String body = "{\n" +
      "  \"id\": 444,\n" +
      "  \"username\": \"firstuser\",\n" +
      "  \"firstName\": \"Krzysztof\",\n" +
      "  \"lastName\": \"Kowalski\",\n" +
      "  \"email\": \"krzysztof@test.com\",\n" +
      "  \"password\": \"password\",\n" +
      "  \"phone\": \"+123456789\",\n" +
      "  \"userStatus\": 1\n" +
      "}";

    given()
      //.header("Content-Type", "application/json")
      .contentType(ContentType.JSON)
      .body(body)
      .when()
      .post("user")
      .then()
      .log()
      .ifValidationFails()
      .assertThat().statusCode(HttpStatus.SC_OK)
      .assertThat().body("code", equalTo(200))
      .assertThat().body("type", equalTo("unknown"))
      .assertThat().body("message", equalTo("444"));


    given()
      .pathParam("username", "firstuser")
      .when()
      .get("user/{username}")
      .then()
      .log()
      .ifValidationFails()
      .assertThat().statusCode(200)
      .assertThat().body("id", equalTo(444))
      .assertThat().body("username", equalTo("firstuser"))
      .assertThat().body("firstName", equalTo("Krzysztof"))
      .assertThat().body("lastName", equalTo("Kowalski"))
      .assertThat().body("email", equalTo("krzysztof@test.com"))
      .assertThat().body("password", equalTo("password"))
      .assertThat().body("phone", equalTo("+123456789"))
      .assertThat().body("userStatus", equalTo(1));
  }
}
