package apitests;



import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class HRApiTest {
  /*
        Create a new class HRApiTests
        createa a @Test getALLRegionsTest
        send a get request to AllRegions API endpoint
        -print status code
        -print content type
        -pretty print response JSON
        verify that status code is 200
        verify that content type is "application/json"
        verify that json response body contains "Americas"
        verify that json response body contains "Europe"
        *try to use static imports for both RestAssured and testng
        *store response inside the Response type variable
     */


    String url="http://3.90.148.246:1000/ords/hr/regions";
    @Test
    public void gettAllRegionTest(){
        Response response = RestAssured.get(url);
        //print status code
        System.out.println("response.statusCode() = " + response.statusCode());
        //-print content type
        System.out.println("response.contentType() = " + response.contentType());
        //-pretty print response JSON
        System.out.println(response.body().prettyPrint());
        //verify that status code is 200
        assertEquals(response.statusCode(),200);
        //verify that content type is "application/json"
        assertEquals(response.contentType(),"application/json");
        //verify that json response body contains "Americas"
        assertTrue(response.body().asString().contains("Americas"));
        //verify that json response body contains "Europe"
        assertTrue(response.body().asString().contains("Europe"));
    }

    @Test
    public void gettAllRegionTestStaticImport(){
        given().accept("ContentType.json").when().get(url).
                then().statusCode(200).
                and().contentType("application/json");
    }

     /*
        Given the accept type XML
        When I send get request to /api/spartans/3
        Then status code must be 406
     */
     String urlSpartan = "http://3.90.148.246:8000/api/spartans/3";
     @Test
    public void spartanTest(){
         Response response = given().accept(ContentType.XML).
                 when().get(urlSpartan);
         assertEquals(response.statusCode(), 406);

     }
}
