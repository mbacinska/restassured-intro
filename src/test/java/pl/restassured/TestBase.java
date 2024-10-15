package pl.restassured;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeSuite;
import properties.EnvironmentConfig;

public class TestBase {
  @BeforeSuite
  public void setupConfiguration() {
    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);

    RestAssured.baseURI = environmentConfig.baseUri();
    RestAssured.basePath = environmentConfig.basePath();
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
  }
}
