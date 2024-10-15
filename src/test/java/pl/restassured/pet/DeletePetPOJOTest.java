package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;
import pl.restassured.TestBase;
import java.util.List;
import static io.restassured.RestAssured.given;

public class DeletePetPOJOTest extends TestBase {

  @Test
  @Description("POJO: Check creation and removing pet")
  @Severity(SeverityLevel.NORMAL)
  public void shouldCreateAndDeletePet(){

    Pet cat = Pet.builder()
      .id(445)
      .name("Kiciuś")
      .photoUrls(List.of("https://render.fineartamerica.com/images/rendered/default/canvas-print/8/8/mirror/break/images/artworkimages/medium/2/portrait-gray-tabby-cat-maika-777-canvas-print.jpg"))
      .category(Category.builder()
        .id(3)
        .name("cats")
        .build())
      .tags(List.of(Tag.builder()
        .id(1)
        .name("cats-category")
        .build()))
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(cat)
      .when()
      .post("pet")
      .then()
      .statusCode(200);
    given()
      .pathParam("petId", 445)
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(200);

    given()
      .pathParam("petId", 445)
      .when()
      .delete("pet/{petId}")
      .then()
      .statusCode(200);

    given()
      .pathParam("petId", 445)
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(404);
  }
}
