package Day6_gson;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class JsonToJavaCollections {

    @BeforeClass
    public void setup(){
        RestAssured.baseURI= ConfigurationReader.get("spartanapi.uri");
    }
    @Test
    public void SpartanJsonToMap(){
        Response response = given().accept(ContentType.JSON).pathParam("id", 15)
                .get("/spartans/{id}");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");


        //convert json result to Java Map
        Map<String, Object> spartanMap= response.body().as(Map.class);
        System.out.println("spartanMap.toString() = " + spartanMap.toString());

        String name = (String) spartanMap.get("name");
        System.out.println("name = " + name);
        assertEquals(name, "Meta");
    }
    
    @Test
    public void allSpartansToList(){
        Response response = given().accept(ContentType.JSON)
                                    .get("/spartans/");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        List<Map<String, Object>> spartanListOfMap = response.body().as(List.class);
        System.out.println("spartanListOfMap.size() = " + spartanListOfMap.size());
        System.out.println("spartanListOfMap.get(0) = " + spartanListOfMap.get(0));
        System.out.println("spartanListOfMap.get(0).get(\"name\") = " + spartanListOfMap.get(0).get("name"));

        String name = (String) spartanListOfMap.get(0).get("name");
        assertEquals(name, "Meade");

        Map<String, Object> meadeMap = spartanListOfMap.get(1);
        System.out.println (meadeMap.get("name")+" "+meadeMap.get("gender"));
    }

    @Test
    public void regionJsonMap(){

        Response response = get(ConfigurationReader.get("hrapi.uri") + "/regions");
        assertEquals(response.statusCode(), 200);

        Map<String, Object> regionMap = response.body().as(Map.class);
        System.out.println(regionMap.get("limit"));
        System.out.println("regionMap.get(\"count\") = " + regionMap.get("count"));

        System.out.println("regionMap.get(\"items\") = " + regionMap.get("items"));


        //It is very complicated and it is not recommended way
        List<Map<String, Object>>itemsList = (List<Map<String, Object>>) regionMap.get("items");
        String regionName= (String) itemsList.get(0).get("region_name");
        System.out.println("regionName = " + regionName);


    }
}
