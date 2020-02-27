package Day6_gson;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class PostMethod {
    @BeforeClass
    public void Setup(){RestAssured.baseURI = ConfigurationReader.get("spartanapi.uri"); }

    /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"Morpheus",
      "phone":1234567890
   }
    When user sends POST request to '/spartans/'
    Then status code 201
    And content type should be application/json
    And json payload/response should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */
    @Test
    public void postNewSpartan(){
        Gson gson = new Gson();
        String spartan102=gson.toJson(new Spartan(102, "Morpheus", "Male", 1234567890L));
        Response post = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and().body(spartan102)
                .post("/spartans");
        assertEquals(post.statusCode(), 201);
        assertEquals(post.contentType(), "application/json");

        String successMessage= post.path("success");
        assertEquals(successMessage, "A Spartan is Born!");

        String name = post.path("data.name");
        System.out.println("name = " + name);
        String gender = post.path("data.gender");
        Integer phone = post.path("data.phone");

        assertEquals(name, "Morpheus");
        assertEquals(gender, "Male");
        assertEquals(phone, (Integer) 1234567890);
        System.out.println(post.path("data.id").toString());


    }

    @Test
    public void PostNewSpartan(){
        Response response = given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON)
                .and().body("{\n" +
                        "  \"gender\": \"Male\",\n" +
                        "  \"name\": \"MikeEU\",\n" +
                        "  \"phone\": 5478783575\n" +
                        "}").when().post("/spartans");

        //response validations
        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json");

        //verify response body
        String successMessage = response.path("success");
        System.out.println("successMessage = " + successMessage);
        assertEquals(successMessage,"A Spartan is Born!");

        String name = response.path("data.name");
        String gender = response.path("data.gender");
        long phone = response.path("data.phone");

        assertEquals(name,"MikeEU");
        assertEquals(gender,"Male");
        assertEquals(phone,5478783575l);
        System.out.println(response.path("data.id").toString());
    }
    @Test
    public void PostNewSpartanWithMap(){
            //we will only change the way of sending body part
            Map<String,Object> requestMap = new HashMap<>();
            //adding the values that we want to post
            requestMap.put("gender","Male");
            requestMap.put("name","MikeEUMAP");
            requestMap.put("phone",5478783575L);

            Response response = given().accept(ContentType.JSON).
                    and().contentType(ContentType.JSON)
                    .and().body(requestMap).when().post("/spartans");

            //response validations
            assertEquals(response.statusCode(),201);
            assertEquals(response.contentType(),"application/json");

            //verify response body
            String successMessage = response.path("success");
            System.out.println("successMessage = " + successMessage);
            assertEquals(successMessage,"A Spartan is Born!");

            String name = response.path("data.name");
            String gender = response.path("data.gender");
            long phone = response.path("data.phone");

            assertEquals(name,"MikeEUMAP");
            assertEquals(gender,"Male");
            assertEquals(phone,5478783575l);
            //printing the id
            System.out.println(response.path("data.id").toString());
            int idFromPost = response.path("data.id");

            System.out.println("-----------END OF POST REQUEST--------------");
            //after post I want to send get request to new spartan
            given().pathParam("id",idFromPost)
                    .when().get("/spartans/{id}")
                    .then().assertThat().statusCode(200).log().all();
        }

    @Test
    public void PostNewSpartanWithPOJO(){//Most recommended way
        //we will only change the way of sending body part
       Spartan spartanEU = new Spartan();
       spartanEU.setGender("Female");
       spartanEU.setName("Jasmine");
       spartanEU.setPhone(4564564564L);

       //send spartanEU object inside body
        Response response = given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON)
                .and().body(spartanEU).when().post("/spartans");

        //response validations
        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json");

        //verify response body
        String successMessage = response.path("success");
        System.out.println("successMessage = " + successMessage);
        assertEquals(successMessage,"A Spartan is Born!");

        String name = response.path("data.name");
        String gender = response.path("data.gender");
        long phone = response.path("data.phone");

        assertEquals(name,"Jasmine");
        assertEquals(gender,"Female");
        assertEquals(phone,4564564564L);
        //printing the id
        System.out.println(response.path("data.id").toString());
        int idFromPost = response.path("data.id");

        System.out.println("-----------END OF POST REQUEST--------------");
        //after post I want to send get request to new spartan
        given().pathParam("id",idFromPost)
                .when().get("/spartans/{id}")
                .then().assertThat().statusCode(200).log().all();
    }

}



