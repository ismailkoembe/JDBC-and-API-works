package apitests;

import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithPath {
    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("spartanapi.uri");
    }

    /*
    Given accept type is json
    And path param id is 10
    When user sends a get request to "/spartans/{id}"
    Then status code is 200
    And content-type is "application/json;char"
    And response payload values match the following:
        id is 10,
        name is "Lorenza",
        gender is "Female",
        phone is 3312820936
 */

    @Test
    public void getSpartanWithPath(){
        Response response = given().accept(ContentType.JSON).and().
                pathParam("id", 10).
                when().get("/spartans/{id}");

        //verify content type and status code
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //print response json body
        System.out.println("ID :"+response.body().path("id").toString());
        System.out.println("NAME :"+response.body().path("name").toString());
        System.out.println("GENDER :"+response.body().path("gender").toString());
        System.out.println("PHONE :"+response.body().path("phone").toString());

        //save them as a variable
        int id = response.body().path("id");
        String name =response.body().path("name");
        String gender = response.body().path("gender");
        long phone = response.body().path("phone");

        //do assertion
        assertEquals(id,10);
        assertEquals(name, "Lorenza");
        assertEquals(gender, "Female");
        assertEquals(phone,3312820936L);

    }

        @Test
        public void getAllSpartanWithPath(){
        Response response= get("/spartans/");

        assertEquals(response.statusCode(), 200);

        int firstId = response.path("id[0]");
            System.out.println("firstId = " + firstId);

         String firstName = response.path("name[0]");
            System.out.println("firstName = " + firstName);

         String lastName = response.path("name[-1]");
            System.out.println("lastName = " + lastName);

         List<String> names = response.path("name");
            System.out.println("names.size() = " + names.size());
            System.out.println("names = " + names);

          Long phone = response.path("phone[0]");
            System.out.println("phone = " + phone);
          List<Object>phones = response.path("phone");
            System.out.println("phones = " + phones);

            phones.forEach(p-> System.out.println(p));

        }

}
