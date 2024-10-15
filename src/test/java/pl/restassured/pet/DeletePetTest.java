package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeletePetTest {

  @BeforeClass
  public void setupConfiguration(){
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
  }

  @Test
  @Description("Check creation and removing pet")
  @Severity(SeverityLevel.CRITICAL)
  public void shouldCreateAndDeletePet(){

    String bodyCreate = "{\n" +
      "  \"name\": \"Kiciuś\",\n" +
      "  \"photoUrls\": [\n" +
      "    \"https://render.fineartamerica.com/images/rendered/default/canvas-print/8/8/mirror/break/images/artworkimages/medium/2/portrait-gray-tabby-cat-maika-777-canvas-print.jpg\"\n" +
      "   \n" +
      "  ],\n" +
      "  \"id\": 445,\n" +
      "  \"category\": {\n" +
      "    \"id\": 445,\n" +
      "    \"name\": \"cats\"\n" +
      "  },\n" +
      "  \"tags\": [\n" +
      "    {\n" +
      "      \"id\": 1,\n" +
      "      \"name\": \"cats-category\"\n" +
      "    }\n" +
      "  ],\n" +
      "  \"status\": \"available\"\n" +
      "}";

    given()
      .header("Content-Type", "application/json")
      .body(bodyCreate)
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
