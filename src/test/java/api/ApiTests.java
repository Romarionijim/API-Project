package api;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import basetest.BaseTest;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;


public class ApiTests extends BaseTest {
    WebDriver driver;

    @Test(description = "expect status 200 after get command", enabled = false)
    public void statusCode200AfterGetRequest() throws InterruptedException {
        given()
                .log()
                .all()
                .when()
                .get("https://www.ynet.co.il/")
                .then()
                .contentType(ContentType.HTML)
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK");
    }

    @Test(description = "log only if the request-response validation fails", enabled = false)
    public void logIfValidationFailed() throws InterruptedException {
        given()
                .log()
                .ifValidationFails()
                .when()
                .get("https://www.ynet.co.il/")
                .then()
                .contentType(ContentType.HTML)
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK");
    }

    @Test(description = "assert that response status is not 403", enabled = false)
    public void assertStatusIsNot403() throws InterruptedException {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("https://www.ynet.co.il/home/0,7340,L-8,00.html");
        if (response.getStatusCode() == 400) {
            driver = WebDriverManager.edgedriver().create();
            driver.navigate().to("https://www.nba.com");
        } else {
            driver = WebDriverManager.chromiumdriver().create();
            driver.navigate().to("https://playwright.dev/");
        }
        assertNotEquals(response.getStatusCode(), 403, "test failed, response status is: " + response.getStatusCode());
    }

    @Test(description = "same test written in a different way", enabled = false)
    public void getStatusCode() throws InterruptedException {
        int statusCode = given()
                .get("https://www.ynet.co.il/home/0,7340,L-8,00.html")
                .getStatusCode();
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            driver = WebDriverManager.chromedriver().create();
            driver.navigate().to("https://www.walla.co.il");
        } else {
            driver = WebDriverManager.edgedriver().create();
            driver.navigate().to("https://www.google.com");
        }
        assertEquals(statusCode, 200, "test failed, status code is: " + statusCode);
    }

    @Test(description = "validate body equals to yes", enabled = false)
    public void yesNoApi() throws InterruptedException {
        when()
                .get("https://yesno.wtf/api")
                .then()
                .assertThat()
                .body("answer", equalTo("yes"));
    }

    @Test(description = "get body as string", enabled = true)
    public void getBody() throws InterruptedException {
        String body = when().get("https://www.ynet.co.il/home/0,7340,L-8,00.html").asString();
        System.out.println(body);
    }

    @Test(description = "get the response content type", enabled = true)
    public void assertContentType() throws InterruptedException {
        Response response = when()
                .get("https://yesno.wtf/api")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        System.out.println(response.asString());
    }

    @Test(description = "extract body specific answer", enabled = false)
    public void getValueFromBody() throws InterruptedException {
        Response response = when()
                .get("https://yesno.wtf/api")
                .then()
                .extract()
                .response();
        String answer = response.path("answer");
        System.out.println(answer);
    }

    @Test(description = "performance testing by validating response time", enabled = false)
    public void validateResponseTime() throws InterruptedException {
        when()
                .get("https://yesno.wtf/api")
                .then()
                .time(lessThan(2L), TimeUnit.SECONDS);
    }
}
