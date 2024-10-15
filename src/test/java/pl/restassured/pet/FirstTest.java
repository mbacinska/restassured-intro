package pl.restassured.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import properties.EnvironmentConfig;

import static io.restassured.RestAssured.given;

public class FirstTest {

  @BeforeClass
  public void setupConfiguration(){
//    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
//    RestAssured.basePath = "v2";
    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class); //owner
    RestAssured.baseURI = environmentConfig.baseUri();
    RestAssured.basePath = environmentConfig.basePath();
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); //filters
  }

  @Test
  @Description("Check response for non-existing pet id")
  @Severity(SeverityLevel.CRITICAL)
  public void getNotExistingPetId(){

    given()
      .when()
      .get("pet/0")
      .then()
      .statusCode(404);
  }

  @Test
  @Description("Check response for existing pet id")
  @Severity(SeverityLevel.CRITICAL)
  public void getExistingPetId(){

    given()
      .when()
      .get("pet/1")
      .then()
      .statusCode(200);
  }
}
