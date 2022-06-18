package api;

import api.Hotel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.testng.Assert.*;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import basetest.BaseTest;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class HotelAppAPITest extends BaseTest {


    @Test(description = "create new hotel by creating a post request", enabled = false)
    public void createNewHotel() throws InterruptedException {
        int statusCode = given()
                .body("{\r\n\"city\": \"LA\",\r\n\"description\": \"Automation API\",\r\n\"name\":\" Hotel API Test\",\r\n\"rating\":11\r\n}")
                .when()
                .post("http://localhost:8090/example/v1/hotels/")
                .getStatusCode();
        assertEquals(statusCode, 201);
    }

    @Test(description = "get hotels info in JSON as string by creating a get request", enabled = false)
    public void getHotelInfo() throws InterruptedException {
        Response response = given()
                .when()
                .get("http://localhost:8090/example/v1/hotels/")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        System.out.println(response.asString());
    }

    @Test(description = "delete hotel with id 2", enabled = false)
    public void deleteHotel() throws InterruptedException {
        given()
                .pathParam("hotelId", 2)
                .when()
                .delete("http://localhost:8090/example/v1/hotels/{hotelId}")
                .then()
                .statusCode(204);
    }

    @Test(description = "create put request and modify a hotel", enabled = false)
    public void editHotel() throws InterruptedException {
        given()
                .body("id\": 3,\n\"name\": \" RRR\",\n\"description\": \"Automation API\",\n\"city\": \"LA\",\n\"rating\": 11")
                .when()
                .put("http://localhost:8090/example/v1/hotels/3")
                .then()
                .statusCode(202);
    }

    @Test(description = "find all ID'S with rating of 5 and delete them all", enabled = false)
    public void deleteHotelWithSpecificRating() throws InterruptedException {
        Response response = given()
                .when()
                .get("http://localhost:8090/example/v1/hotels/")
                .then()
                .extract()
                .response();
        ArrayList<Integer> hotelID = response.path("content.findAll {it.rating == 5}.id");
        hotelID.forEach(id -> given()
                .pathParam("hotelId", id)
                .when()
                .delete("http://localhost:8090/example/v1/hotels/{hotelId}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT));
    }

    @Test(description = "create new hotel by setting dynamic parameters in a method", enabled = false)
    public void createHotel() {
        createNewHotel("Golden", "Romario API Testing", "Romario 5 start Hotel", 5);
    }

    public void createNewHotel(String city, String description, String name, int rating) {
        given()
                .log()
                .all()
                .body(String.format("{\r\n\"city\": \"%s\",\r\n\"description\": \"%s\",\r\n\"name\":\"%s\",\r\n\"rating\":%d\r\n}", city, description, name, rating))
                .when()
                .post("http://localhost:8090/example/v1/hotels/")
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test(description = "create new hotel by creating an instance of a class object and creating a post request", enabled = false)
    public void createHotelFromClass() {
        Hotel hotel = new Hotel("ATL", "ATL new Hotel", "lux Hotel", 11);
        createHotelFromClassObject(hotel);
    }

    public void createHotelFromClassObject(Hotel hotel) {
        given()
                .log()
                .all()
                .body(hotel)
                .when()
                .post("http://localhost:8090/example/v1/hotels/")
                .then()
                .assertThat()
                .statusCode(201);
    }

    public void createNewHotelWithFaker() {
        given()
                .log()
                .all()
                .body(String.format("{\n" +
                                "\"city\": \"%s\",\n" +
                                "\"description\": \"%s\",\n" +
                                "\"name\":\"%s\",\n" +
                                "\"rating\":%d\n" +
                                "}", faker.address().city(),
                        faker.lorem().paragraph(),
                        faker.name().name(),
                        faker.number().randomDigit()))
                .when()
                .post("http://localhost:8090/example/v1/hotels/")
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test(description = "create number of random hotels by the help of faker and invocation count", enabled = false, invocationCount = 5)
    public void createDynamicRandomHotels() {
        createNewHotelWithFaker();
    }

    @Test(description = "delete all hotels that were created with rating under and equals to 7", enabled = false)
    public void deleteAllHotels() {
        Response response = when()
                .get("http://localhost:8090/example/v1/hotels/")
                .then()
                .extract()
                .response();
        ArrayList<Integer> AllHotels = response.path("content.findAll {it.rating <=7}.id");
        AllHotels.forEach(id -> {
            given()
                    .pathParam("hotelId", id)
                    .when()
                    .delete("http://localhost:8090/example/v1/hotels/{hotelId}")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        });
    }

    @Test(description = "find the city name of a specific id using find in GPath", enabled = false)
    public void extractNameOfSpecificCity() {
        Response response = when()
                .get("http://localhost:8090/example/v1/hotels/")
                .then()
                .extract()
                .response();
        String hotelName = response.path("content.find {it.id == 16}.city");
        System.out.println(hotelName);
    }
}
