package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;
import pl.restassured.TestBase;
import java.util.List;
import static io.restassured.RestAssured.given;

public class CreateAndUpdatePetPOJOTest extends TestBase {

  @Test
  @Description("POJO: Check creation and update of pet")
  @Severity(SeverityLevel.CRITICAL)
  public void shouldCreateAndUpdatePetName() {

    Pet rabbit = Pet.builder()
      .id(123)
      .name("Czaruś")
      .photoUrls(List.of("https://render.fineartamerica.com/images/rendered/default/poster/6.5/8/break/images/artworkimages/medium/2/rabbit-ken-petch.jpg"))
      .category(Category.builder()
        .id(2)
        .name("rabbits")
        .build())
      .tags(List.of(Tag.builder()
        .id(1)
        .name("rabbits-category")
        .build()))
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(rabbit)
      .when()
      .post("pet")
      .then()
      .statusCode(200);

    Pet actualRabbit = given()
      .pathParam("petId", 123)
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(200)
      .extract().as(Pet.class);

    Assert.assertEquals(actualRabbit.getName(),rabbit.getName(), "Actual name doesn't match");


    Pet rabbitWithUpdatedName = Pet.builder()
      .id(123)
      .name("Czaruś - Bannuś")
      .photoUrls(List.of("https://render.fineartamerica.com/images/rendered/default/poster/6.5/8/break/images/artworkimages/medium/2/rabbit-ken-petch.jpg"))
      .category(Category.builder()
        .id(2)
        .name("rabbits")
        .build())
      .tags(List.of(Tag.builder()
        .id(1)
        .name("rabbits-category")
        .build()))
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(rabbitWithUpdatedName)
      .when()
      .put("pet")
      .then()
      .statusCode(200);

    Pet actualRabbitWithUpdatedName = given()
      .pathParam("petId", 123)
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(200)
      .extract().as(Pet.class);


    Assert.assertEquals(actualRabbitWithUpdatedName.getName(),rabbitWithUpdatedName.getName(), "Actual name doesn't match");
  }
}
