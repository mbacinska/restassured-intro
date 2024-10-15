package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class CreateAndUpdatePetTest {

  @BeforeClass
  public void setupConfiguration(){
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
  }

  @Test
  @Description("Check creation and update of pet")
  @Severity(SeverityLevel.MINOR)
  public void shouldCreateAndUpdatePetName(){

    String bodyCreate = "{\n" +
      "  \"name\": \"Czarus\",\n" +
      "  \"photoUrls\": [\n" +
      "    \"https://render.fineartamerica.com/images/rendered/default/poster/6.5/8/break/images/artworkimages/medium/2/rabbit-ken-petch.jpg\"\n" +
      "   \n" +
      "  ],\n" +
      "  \"id\": 123,\n" +
      "  \"category\": {\n" +
      "    \"id\": 1,\n" +
      "    \"name\": \"rabbits\"\n" +
      "  },\n" +
      "  \"tags\": [\n" +
      "    {\n" +
      "      \"id\": 1,\n" +
      "      \"name\": \"rabbits-category\"\n" +
      "    }\n" +
      "  ],\n" +
      "  \"status\": \"available\"\n" +
      "}";

    given()
      .header("Content-Type", "application/json")
      //.contentType("application/json")
      .body(bodyCreate)
      .when()
      .post("pet")
      .then()
      .statusCode(200);

    given()
      .pathParam("petId", 123)
      .when()
      .get("pet/{petId}")
      .then()
      .statusCode(200);

    String bodyUpdate = "{\n" +
      "  \"name\": \"Czarus-Bannuś\",\n" +
      "  \"photoUrls\": [\n" +
      "    \"https://render.fineartamerica.com/images/rendered/default/poster/6.5/8/break/images/artworkimages/medium/2/rabbit-ken-petch.jpg\"\n" +
      "   \n" +
      "  ],\n" +
      "  \"id\": 123,\n" +
      "  \"category\": {\n" +
      "    \"id\": 1,\n" +
      "    \"name\": \"rabbits\"\n" +
      "  },\n" +
      "  \"tags\": [\n" +
      "    {\n" +
      "      \"id\": 1,\n" +
      "      \"name\": \"rabbits-category\"\n" +
      "    }\n" +
      "  ],\n" +
      "  \"status\": \"available\"\n" +
      "}";

    given()
      .header("Content-Type", "application/json")
      .body(bodyUpdate)
      .when()
      .put("pet")
      .then()
      .statusCode(200);
  }
}
