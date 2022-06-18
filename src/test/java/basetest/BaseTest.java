package basetest;

import com.github.javafaker.Faker;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    public Faker faker;

    @BeforeClass
    protected void setup() {
//        RestAssured.baseURI = "https://www.ynet.co.il/";
        faker = new Faker();
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
        RestAssured.requestSpecification = requestSpecification;
    }

    RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost")
                .setBasePath("/example/v1/hotels/")
                .setPort(8090)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
