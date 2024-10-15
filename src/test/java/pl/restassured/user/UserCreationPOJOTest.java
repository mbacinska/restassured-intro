package pl.restassured.user;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;
import pl.javastart.main.pojo.UserResponse;
import pl.restassured.TestBase;
import static io.restassured.RestAssured.given;
import org.testng.Assert;

public class UserCreationPOJOTest extends TestBase {

  private User user;

  @Test
  @Description("Builder: Check creation of user")
  @Severity(SeverityLevel.CRITICAL)
  public void shouldCreateUserAndCheckIfCreated() {

    user = User.builder()
      .id(445)
      .username("firstuser_2")
      .firstName("Krzysztof")
      .lastName("Kowalski")
      .email("krzysztof@test.com")
      .password("password")
      .phone("+123456789")
      .userStatus(1)
      .build();

    UserResponse expectedUserResponse = UserResponse.builder()
      .code(200)
      .type("unknown")
      .message(user.getId().toString())
      .build();

    UserResponse response = given()
      //.header("Content-Type", "application/json")
      .contentType(ContentType.JSON)
      .body(user)
      .when()
      .post("user")
      .then()
      .log()
      .all()
      .assertThat().statusCode(HttpStatus.SC_OK)
      .extract().as(UserResponse.class);

    Assert.assertEquals(response.getCode(), expectedUserResponse.getCode(), "Code doesn't match");
    Assert.assertEquals(response.getType(), expectedUserResponse.getType(), "Type doesn't match");
    Assert.assertEquals(response.getMessage(), expectedUserResponse.getMessage(), "Message doesn't match");

    given()
      .pathParam("username", "firstuser_2")
      .when()
      .get("user/{username}")
      .then()
      .log()
      .ifValidationFails()
      .statusCode(200);
  }

  @AfterMethod
  public void cleanUpAfterTest(){

    UserResponse response = given()
      .contentType("application/json")
      .pathParam("username", "firstuser_2")
      .when()
      .delete("user/{username}")
      .then()
      .assertThat()
      .statusCode(200)
      .extract().as(UserResponse.class);

    Assert.assertEquals(response.getCode(), Integer.valueOf(200), "Code doesn't match");
    Assert.assertEquals(response.getType(), "unknown", "Type doesn't match");
    Assert.assertEquals(response.getMessage(), user.getUsername(), "Message doesn't match");
  }
}
