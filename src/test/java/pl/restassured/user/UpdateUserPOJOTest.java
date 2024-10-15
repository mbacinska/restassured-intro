package pl.restassured.user;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserPOJOTest {

  @BeforeClass
  public void setupConfiguration(){
    RestAssured.useRelaxedHTTPSValidation(); // do not check of SSL certificate
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
  }

  @Test
  @Description("Builder: Check creation and update of user")
  @Severity(SeverityLevel.CRITICAL)
  public void shouldCreateAndUpdateUser(){

    User user = User.builder()
      .id(445)
      .username("seconduser_v2")
      .firstName("Marian")
      .lastName("Ziębala")
      .email("marian@test.com")
      .password("password")
      .phone("+222222222")
      .userStatus(1)
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(user)
      .when()
      .post("user")
      .then()
      .log()
      .ifValidationFails()
      .assertThat().statusCode(200)
      .assertThat().body("code", equalTo(200))
      .assertThat().body("type", equalTo("unknown"))
      .assertThat().body("message", equalTo(user.getId().toString()));

    User userWithUpdatedName= User.builder()
      .id(445)
      .username("seconduser_v2")
      .firstName("Marianek")
      .lastName("Ziębala - Brzeski")
      .email("marian@test.com")
      .password("password")
      .phone("+222222222")
      .userStatus(1)
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(userWithUpdatedName)
      .pathParam("username", "seconduser_v2")
      .when()
      .put("user/{username}")
      .then()
      .log()
      .ifValidationFails()
      .assertThat().statusCode(200)
      .assertThat().body("code", equalTo(200))
      .assertThat().body("type", equalTo("unknown"))
      .assertThat().body("message", equalTo(user.getId().toString()));

    given()
      .pathParam("username", "seconduser_v2")
      .when()
      .get("user/{username}")
      .then()
      .log()
      .ifValidationFails()
      .assertThat().statusCode(200)
      .assertThat().body("id", equalTo(user.getId()))
      .assertThat().body("username", equalTo(user.getUsername()))
      .assertThat().body("firstName", equalTo(userWithUpdatedName.getFirstName()));
  }
}
