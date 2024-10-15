package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class BasicHttpMethodsJsonPathTest {

  @BeforeClass
  public void setupConfiguration(){
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
  }

  @Test
  @Description("JSON Path: create new pet")
  @Severity(SeverityLevel.MINOR)
  public void shouldCreatePetAndCheckWithJsonPath() {

    Pet ant = Pet.builder()
      .id(321)
      .name("Mrówka")
      .photoUrls(List.of("https://render.fineartamerica.com/images/rendered/default/metal-print/10/7.5/break/images/artworkimages/medium/2/ant-on-leaf-adegsm.jpg"))
      .category(Category.builder()
        .id(5)
        .name("ants")
        .build())
      .tags(List.of(Tag.builder()
        .id(1)
        .name("ants-category")
        .build()))
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(ant)
      .when()
      .post("pet")
      .then()
      .log()
      .all()
      .statusCode(200);

    JsonPath actualAntJsonPathResponse = given()
      .pathParam("petId", ant.getId())
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(200)
      .extract().jsonPath();

    assertEquals("Name doesn't match", ant.getName(), actualAntJsonPathResponse.getString("name"));
    assertEquals("Id doesn't match", ant.getId().intValue(), actualAntJsonPathResponse.getInt("id"));
    assertEquals("Category name doesn't match", ant.getCategory().getName(), actualAntJsonPathResponse.getString("category.name"));
    assertEquals("Photo url doesn't match", ant.getPhotoUrls().get(0), actualAntJsonPathResponse.getString("photoUrls[0]"));
    assertEquals("Tag name doesn't match", ant.getTags().get(0).getName(), actualAntJsonPathResponse.getString("tags[0].name"));
    assertEquals("Tag id doesn't match", ant.getTags().get(0).getId().intValue(), actualAntJsonPathResponse.getInt("tags[0].id"));
  }
}
