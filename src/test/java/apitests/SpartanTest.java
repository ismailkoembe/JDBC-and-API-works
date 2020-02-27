package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import  static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTest {

    String spartanAllUrl="http://3.90.148.246:8000/api/spartans";

    @Test
    public void viewAllSpartans(){
        Response response = RestAssured.get(spartanAllUrl);

        System.out.println("response.statusCode() = " + response.statusCode());

       // System.out.println(response.body().asString());

        System.out.println(response.body().prettyPrint());

    }

       /* when user send GET request to /api/spartans end point
        Then status code must be 200
        And body should contains Orion
     */

    @Test
    public void viewSpartanTest1(){
        Response response = RestAssured.get(spartanAllUrl);

        //verify status code is 200
        Assert.assertEquals(response.statusCode(),200);
        //verify body includes Orion
        Assert.assertTrue(response.body().asString().contains("Orion"));

    }

     /* Given accept type applicaton/json
        when user send GET request to /api/spartans end point
        Then status code must be 200
        And Response Content type must be Json
        And body should contains Orion
     */

    @Test
    public void viewSpartanTest2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(spartanAllUrl);
        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify response content type is json
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");

        Assert.assertTrue(response.getBody().asString().contains("Orion"));


    }

    //  /* Given accept type applicaton/xml
    //        when user send GET request to /api/spartans end point
    //        Then status code must be 200
    //        And Response Content type must be xml
    //        And body should contains Orion
    //     */
    @Test
    public void viewSpartanTest3(){
        Response response =given().accept(ContentType.XML).
                when().get(spartanAllUrl);

        assertEquals(response.statusCode(), 200);

        assertEquals(response.contentType(), "application/xml;charset=UTF-8");

        assertTrue(response.getBody().asString().contains("Orion"));

    }

    //  /* Given accept type application/xml
    //        when user send GET request to /api/spartans end point
    //        Then status code must be 200
    //        And Response Content type must be xml
    //     */

    @Test
    public void viewSpartanTest4(){
        //request starts here
        given().accept(ContentType.XML)
                .when().get(spartanAllUrl).
                //response starts here
                        then().statusCode(200)
                .and().contentType("application/xml;charset=UTF-8");

    }
         /*
        Given the accept type Json
        When I send get request to /api/spartans/3
        Then status code must be 200
        And Content type should be json
        and body should contains Fidole
     */

    @Test
    public void getOneSpartan(){
        Response response = given().accept(ContentType.JSON).when().get(spartanAllUrl + "/3");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Fidole"));

    }





}
