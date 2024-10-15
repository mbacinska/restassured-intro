package pl.restassured.cert;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class InvalidCertTest {

  @Test
  @Description("Check untrusted root")
  public void untrustedRootTest(){

    given()
      .relaxedHTTPSValidation() // do not check of SSL certificate
      .when()
      .get("https://untrusted-root.badssl.com/")
      .then()
      .assertThat()
      .statusCode(200);
  }
}
