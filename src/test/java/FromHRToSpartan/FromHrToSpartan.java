package FromHRToSpartan;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class FromHrToSpartan {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = ConfigurationReader.get("hrapi.uri");
    }
    @Test
    public void fromHRtoSpartan(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("employee_id", 100)
                .when().get( "/employees/{employee_id}");
        assertEquals(response.statusCode(), 200);
       //response.prettyPrint();

       Employees employees1 =response.body().as(Employees.class);
        System.out.println(employees1.getFirst_name());
        System.out.println(employees1.toString());

    }

    @Test
    public void test() {
        Response responseFrom = given()
                .queryParam("region", "United States")
                .queryParam("gender", "female")
                .queryParam("amount", 10)
                .get("http://uinames.com/api/?ext");
        JsonPath jsonData = responseFrom.jsonPath();
        List<String> names = jsonData.getList("name");
        System.out.println("names = " + names);
        List<String> phones = jsonData.getList("phone");
        System.out.println("phones = " + phones);
        List<Map<String, Object>> putList= new ArrayList<>();
        int k=0;
        Long l;
        String phone;
        for(String s:names) {
            Map<String, Object> putMap= new HashMap<>();
            phone= phones.get(k);
            phone= phone.substring(1,4).trim()+phone.substring(5,9).trim()+phone.substring(10).trim();
            l=Long.parseLong(phone);
            putMap.put("name", s);
            putMap.put("gender", "Female");
            putMap.put("phone",l);
            putList.add(k, putMap);
            k++;
        }
        Response responseTo;
        for (int i=0; i<names.size();i++){
            responseTo= given().accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(putList.get(i))
                    .post("http://3.90.148.246:8000/api/spartans");
            String successMessage= responseTo.path("success");
            assertEquals(successMessage,"A Spartan is Born!");
        }
    }










}


class Employees{
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}