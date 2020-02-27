package Day6_gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.nashorn.internal.scripts.JS;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class POJO_deserialize {

    @Test
    public void oneSpartanWithPOJO(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get(ConfigurationReader.get("spartanapi.uri") + "/spartans/{id}");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json;charset=UTF-8");


        Spartan spartan15 = response.body().as(Spartan.class);
        System.out.println("spartan15.toString() = " + spartan15.toString());
        System.out.println("spartan15.getId() = " + spartan15.getId());
        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getGender() = " + spartan15.getGender());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());

        assertEquals(spartan15.getId(),15);
        assertEquals(spartan15.getName(), "Meta");
        assertEquals(spartan15.getGender(),"Female");
        assertEquals(spartan15.getPhone(),1938695106L);
    }

    @Test
    public void regionWithPojo(){
        Response response = get(ConfigurationReader.get("hrapi.uri") + "/regions");
        assertEquals(response.statusCode(), 200);

        Regions regions = response.body().as(Regions.class);
        System.out.println("regions.getCount() = " + regions.getCount());


        List<Item> regionList = regions.getItems();
        System.out.println("regionList.get(0).getRegionName() = " + regionList.get(0).getRegionName());
        System.out.println(regions.getItems().get(1).getRegionId());


        for (Item item : regionList) {
            System.out.println(item.getRegionName());
        }

    }
    @Test
    public void GsonExample(){
        Gson gson = new Gson();

        String myjson = "{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";

        //converting json to pojo(Spartan Class)
        Spartan spartan15 = gson.fromJson(myjson, Spartan.class);
        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());

        //----------------Serialization------------
        Spartan spartanEU = new Spartan(111, "Jason", "Kidd", 12345678L);
        //it will take spartanEU information and convert ti json
        String jsonSpartanEU = gson.toJson(spartanEU);
        System.out.println("jsonSpartanEU = " + jsonSpartanEU);

    }
}


