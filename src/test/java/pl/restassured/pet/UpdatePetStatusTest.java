package pl.restassured.pet;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;
import pl.restassured.TestBase;
import java.util.List;
import static io.restassured.RestAssured.given;

public class UpdatePetStatusTest extends TestBase {

  Faker faker = new Faker();

  @Test
  public void shouldUpdatePetStatus() {

    // given pet with "available" status
    Pet activeAnt = Pet.builder()
      .id(555444)
      .category(Category.builder()
        .id(3)
        .name("Ant - category")
        .build())
      .name("Ant Z")
      .photoUrls(List.of(faker.internet().image()))
      .tags(List.of(Tag.builder()
        .id(8)
        .name("Ant - tag name")
        .build()))
      .status("available")
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(activeAnt)
      .when()
      .post("pet")
      .then()
      .statusCode(200);

    Pet actualActiveAnt = given()
      .pathParam("petId", 555444)
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(200)
      .extract().as(Pet.class);

    Assert.assertEquals(actualActiveAnt.getStatus(), activeAnt.getStatus(), "Actual status doesn't match");

    given()
      .header("Content-Type", "application/x-www-form-urlencoded")
      .pathParam("petId", 555444)
      .formParam("status", "sold")
      .when()
      .post("pet/{petId}")
      .then()
      .statusCode(200);

    Pet actualSoldAnt = given()
      .pathParam("petId", 555444)
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(200)
      .extract().as(Pet.class);

    Assert.assertEquals(actualSoldAnt.getStatus(), "sold", "Actual status doesn't match");
  }

}
