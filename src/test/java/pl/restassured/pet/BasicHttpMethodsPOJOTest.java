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

public class BasicHttpMethodsPOJOTest extends TestBase {

  @Test
  @Description("Builder: Create new pet")
  @Severity(SeverityLevel.MINOR)
  public void shouldCreateNewPet() {

    Pet dog = Pet.builder()
      .id(789)
      .category(Category.builder()
        .id(1)
        .name("dogs")
        .build())
      .name("Burek")
      .photoUrls(List.of("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/nerd-dog-thomas-hole.jpg"))
      .status("available")
      .tags(List.of(Tag.builder()
        .id(1)
        .name("dogs-category")
        .build()))
      .build();

    Pet actualDog = given()
      .log()
      .method()
      .log()
      .uri()
      .header("Content-Type", "application/json")
      .body(dog)
      .when()
      .post("pet")
      .then()
      .log()
      .all()
      .statusCode(200)
      .extract().as(Pet.class);

    Assert.assertEquals(actualDog.getId(), dog.getId(), "Actual animal id doesn't match");
    Assert.assertEquals(actualDog.getName(), dog.getName(), "Actual animal name doesn't match");
    Assert.assertEquals(actualDog.getCategory().getName(), dog.getCategory().getName(), "Actual category name doesn't match");
    Assert.assertEquals(actualDog.getCategory().getId(), dog.getCategory().getId(), "Actual category id doesn't match");
    Assert.assertEquals(actualDog.getTags().get(0).getName(), dog.getTags().get(0).getName(), "Actual tag name doesn't match");
  }
}
