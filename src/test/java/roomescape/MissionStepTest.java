package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import javax.sound.sampled.Port;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 어드민_페이지를_요청하면_200_코드를_반환한다() {
        testPageStatus("/admin", 200);
    }

    @Test
    void 예약_페이지를_요청하면_200_코드를_반환한다() {
        testPageStatus("/admin/reservation",200);
    }

    @Test
    void 예약_목록을_요청하면_200_코드와_데이터를_반환한다() {
        testResponseBodyDataSize("/reservations", 200, 0);
    }

    @Test
    void 예약추가_및_삭제를_수행한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        testResponseBodyDataSize("/reservations", 200, 1);


        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        testResponseBodyDataSize("/reservations", 200, 0);
    }

    private void testResponseBodyDataSize(String path, int expectedStatusCode, int size) {
        testPageStatus(path, expectedStatusCode)
                .body("size()", is(size));
    }

    private ValidatableResponse testPageStatus(String path, int expectedStatusCode) {
        return RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(expectedStatusCode);
    }
}
