package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertTrue;

public class FilterPetsWithGivenStatusTest {

  @BeforeClass
  public void setupConfiguration(){
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
  }

  @Test
  @Description("Check filtering by status: sold")
  @Severity(SeverityLevel.NORMAL)
  public void shouldFilterPetsWithSoldStatus() {

    Pet soldFish = Pet.builder()
      .id(7347)
      .name("Nemo")
      .photoUrls(List.of("https://render.fineartamerica.com/images/rendered/default/canvas-print/10/7/mirror/break/images/artworkimages/medium/2/clownfish-in-white-anemone-alastair-pollock-photography-canvas-print.jpg"))
      .category(Category.builder()
        .id(4)
        .name("fish")
        .build())
      .tags(List.of(Tag.builder()
        .id(1)
        .name("fish-category")
        .build()))
      .status("sold")
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(soldFish)
      .when()
      .post("pet")
      .then()
      .statusCode(200);

    Pet[] pets = given()
      .queryParam("status", "sold")
      .when()
      .get("pet/findByStatus")
      .then()
      .statusCode(200)
      .extract().as(Pet[].class);

    List<Pet> petsList = Arrays.asList(pets);

    boolean petWIthExpectedIdExists = petsList.stream()
      .anyMatch(pet -> pet.getId() == 7347);

    assertTrue("There is no pet with given id", petWIthExpectedIdExists);
  }

  @Test
  @Description("Check filtering by status: pending")
  @Severity(SeverityLevel.NORMAL)
  public void shouldFilterPetsWithPendingStatus(){

    Pet pendingOctopus = Pet.builder()
      .id(1111)
      .name("Octopek")
      .photoUrls(List.of("https://render.fineartamerica.com/images/rendered/default/acrylic-print/10/6.5/hangingwire/break/images/artworkimages/medium/2/1-octopus-reynold-mainse--design-pics.jpg"))
      .category(Category.builder()
        .id(6)
        .name("octopus")
        .build())
      .tags(List.of(Tag.builder()
        .id(6)
        .name("octopus-category")
        .build()))
      .status("pending")
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(pendingOctopus)
      .when()
      .post("pet")
      .then()
      .statusCode(200);

    List<Pet> pets = given()
      .queryParam("status", "pending")
      .when()
      .get("pet/findByStatus")
      .then()
      .statusCode(200)
      .extract().jsonPath().getList("", Pet.class);
  }
}
