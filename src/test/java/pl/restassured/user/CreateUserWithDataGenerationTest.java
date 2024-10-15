package pl.restassured.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;
import pl.javastart.main.pojo.UserResponse;
import pl.restassured.TestBase;

import static io.restassured.RestAssured.given;

import org.testng.Assert;

public class CreateUserWithDataGenerationTest extends TestBase {

  Faker faker = new Faker();
  private User user;

  @Test
  @Description("Check creation and update of user with data generator")
  @Severity(SeverityLevel.CRITICAL)
  public void shouldCreateUserWithDataGenerator() {

    user = User.builder()
      .id(faker.number().numberBetween(1, 9999))
      .username(faker.name().username())
      .firstName(faker.name().firstName())
      .lastName(faker.name().lastName())
      .email(faker.internet().emailAddress())
      .password("P@ssw0rd")
      .phone(faker.phoneNumber().phoneNumber())
      .userStatus(1)
      .build();

    UserResponse userResponse = given()
      .header("Content-Type", "application/json")
      .body(user)
      .log()
      .all()
      .when()
      .post("user")
      .then()
      .log()
      .all()
      .assertThat().statusCode(200)
      .extract().as(UserResponse.class);

    Assert.assertEquals(userResponse.getCode(), Integer.valueOf(200), "Code doesn't match");
    Assert.assertEquals(userResponse.getType(), "unknown", "Type doesn't match");
    Assert.assertEquals(userResponse.getMessage(), user.getId().toString(), "Message doesn't match");
  }

  @AfterMethod(alwaysRun = true)
  public void cleanUpAfterTest() {
    if (user != null) {

      given()
        .contentType("application/json")
        .pathParam("username", user.getUsername())
        .when()
        .delete("user/{username}")
        .then()
        .log()
        .all()
        .assertThat()
        .statusCode(200);
    }
  }
}
