package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
public class HomeWork1 {
    /*Q1:
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is 2
    * */

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON).and()
                .pathParam("country_id", "US")
                .when().get("http://3.90.148.246:1000/ords/hr/countries/{country_id}");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json");

        JsonPath json =  response.jsonPath();
        assertEquals(json.getString("country_id"), "US");
        assertEquals(json.getString("country_name"),"United States of America");
        assertEquals(json.getString("region_id"),"2");

    }
        /*Q2:
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25*/

        @Test
        public void Test2(){
            Response response = given().accept(ContentType.JSON)
                    .param("q={\"department_id\":80}")
                    .get("http://3.90.148.246:1000/ords/hr/employees");
            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json");
            JsonPath json = response.jsonPath();

           // List<Object> job_ids = json.getList("items.findAll{it.job_id.startsWith(\"AD_V\")}job_id");
            List<Object> job_ids = json.getList("items.department_id");
           // System.out.println("job_ids = " + job_ids);
            job_ids.forEach(j-> System.out.println(j));
        }
}
