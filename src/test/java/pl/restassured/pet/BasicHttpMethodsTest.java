package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BasicHttpMethodsTest {

  @BeforeClass
  public void setupConfiguration(){
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
  }

  @Test
  @Description("Body string: Create new pet")
  @Severity(SeverityLevel.MINOR)
  public void shouldCreateNewPet(){

    String body = "{\n" +
      "  \"name\": \"Burek\",\n" +
      "  \"photoUrls\": [\n" +
      "    \"https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/nerd-dog-thomas-hole.jpg\"\n" +
      "   \n" +
      "  ],\n" +
      "  \"id\": 789,\n" +
      "  \"category\": {\n" +
      "    \"id\": 1,\n" +
      "    \"name\": \"dogs\"\n" +
      "  },\n" +
      "  \"tags\": [\n" +
      "    {\n" +
      "      \"id\": 1,\n" +
      "      \"name\": \"dogs-category\"\n" +
      "    }\n" +
      "  ],\n" +
      "  \"status\": \"available\"\n" +
      "}";

    given()
      .log()
      .method()
      .log()
      .uri()
      .header("Content-Type", "application/json")
      .body(body)
      .when()
      .post("pet")
      .then()
      .log()
      .all()
      .statusCode(200);
  }
  @Test
  @Description("Check if pet exists")
  @Severity(SeverityLevel.MINOR)
  public void shouldCHeckIfPetWasCreated(){

    given()
      .pathParam("petId", 789)
      .when()
      .get("pet/{petId}")
      .then()
      .log()
      .ifValidationFails()
      .statusCode(200);
  }
}
