package pl.restassured.pet;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;
import pl.restassured.TestBase;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class CreatePetWithDataGeneratorTest extends TestBase {

  Faker faker = new Faker();
  Random random = new Random();
  private Pet pet;

  @Test
  @Description("Check creation with data generator")
  @Severity(SeverityLevel.NORMAL)
  public void shouldCreatePetWithDataGenerator() {

    String[] statuses = {"available", "pending", "sold"};
    int randomIndex = random.nextInt(statuses.length);

    pet = Pet.builder()
      .id(faker.number().numberBetween(1, 9999))
      .name(faker.funnyName().toString())
      .photoUrls(List.of(faker.internet().url()))
      .category(Category.builder()
        .id(faker.number().numberBetween(1, 3))
        .name(faker.funnyName().toString())
        .build())
      .tags(List.of(Tag.builder()
        .id(faker.number().numberBetween(1, 2))
        .name(faker.funnyName().toString())
        .build()))
      .status(statuses[randomIndex])
      .build();

    given()
      .header("Content-Type", "application/json")
      .body(pet)
      .when()
      .post("pet")
      .then()
      .statusCode(200);
  }

  @AfterMethod(alwaysRun = true)
  public void cleanUpAfterTest() {
    if (pet != null) {
      given()
        .contentType("application/json")
        .pathParam("petId", pet.getId())
        .when()
        .delete("pet/{petId}")
        .then()
        .assertThat().statusCode(200);

    }
  }
}
