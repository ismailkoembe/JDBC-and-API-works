package day7_Post_Delete_Patch;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;



import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;


public class SpartanPutDelete {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI= ConfigurationReader.get("spartanapi.uri");
    }
    @Test
    public void updatingSpartan(){

        Map<String, Object> putMap = new HashMap<>();
        putMap.put("gender", "Female");
        putMap.put("name", "Jessica");
        putMap.put("phone", 1594876231L);
        given().pathParam("id", 50)
                .and().contentType(ContentType.JSON)
                .and().body(putMap)
                .when().put("/spartans/{id}").then()
                .assertThat().statusCode(204);
    }
        @Test
        public void DeleteASpartan(){
        Random rd = new Random();
        int idToDelete = rd.nextInt(100)+2;
            System.out.println("idToDelete = " + idToDelete);
            given().pathParam("id", idToDelete)
                .when().delete("/spartans/{id}").then()
                .assertThat().statusCode(204);
        }
}
