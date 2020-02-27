package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithJsonPath {
    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("spartanapi.uri");
    }
  /*
    Given accept type is json
    And path param spartan id is 11
    When user sends a get request to /spartans/{id}
    Then status code is 200
    And content type is Json
    And  "id": 11,
     "name": "Nona",
     "gender": "Female",
     "phone": 7959094216
 */
    @Test
    public void verifyoneSpartanWithJsonPath(){
        Response response = given().accept(ContentType.JSON).and()
                .pathParam("id", 11)
                .when().get("/spartans/{id}");

        assertEquals(response.statusCode(), 200);
        //verify content type
        //assertEquals(response.getHeader("Content-Type"), "application/json;charset=UTF-8");
        assertEquals(response.contentType(), "application/json;charset=UTF-8");
        // verify transfer- encoding is exist in response headers
        assertTrue(response.headers().hasHeaderWithName("Transfer-Encoding"));
        //body
        int id = response.path("id");
        assertEquals(id, 11);

        JsonPath json = response.jsonPath();

        int idJson = json.getInt("id");
        String nameJson = json.getString("name");
        String genderJson = json.get("gender");
        long phoneJson = json.getLong("phone");

        assertEquals(idJson, 11);
        assertEquals(nameJson, "Nona");
        assertEquals(genderJson, "Female");
        assertEquals(phoneJson, 7959094216L);





    }

}
