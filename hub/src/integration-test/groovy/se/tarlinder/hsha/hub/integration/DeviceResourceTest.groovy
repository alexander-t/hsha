package se.tarlinder.hsha.hub.integration

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import static io.restassured.RestAssured.given
import static io.restassured.RestAssured.when

// Since this is an end-to-end test, we can impose test ordering
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceResourceTest {

    @LocalServerPort
    private int port

    @Before
    void "setup port"() {
        RestAssured.port = port
    }

    @Test
    void "0.Smoke test"() {
        given().log().all().when().get("/device").then().statusCode(200)
    }

    @Test
    void "1.Querying status when no events are registered produces 404"() {
        given().log().all().when().get("/device/123").then().statusCode(404);
    }

    @Test
    void "2.Put and read back a device event"() {
        def eventJson = ["device_id" : 88,
                         "value" : "ON",
                         "device_name": "motion sensor"]
        given().log().all().contentType(ContentType.JSON).body(eventJson).when().put("/device/88").then().statusCode(200)
        when().get("/device/88").then().statusCode(200)
    }

}
