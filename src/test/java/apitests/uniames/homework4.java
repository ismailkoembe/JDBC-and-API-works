package apitests.uniames;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.*;
public class homework4 {
    @BeforeClass
    public void setup(){
        baseURI = ConfigurationReader.get("uinames.uri");
    }

    /*1. Send a get request without providing any parameters
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that name, surname, gender, region fields have value*/

    @Test
    public void noParameter(){
        Response response = given().accept(ContentType.JSON).get();
        JsonPath json =response.jsonPath();
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        String name = json.getString("name");
        String surname = json.getString("surname");
        String gender = json.getString("gender");
        String region = json.getString("region");

        System.out.println("(name+surname+gender+region) = " + (name + surname + gender + region));

        assertNotEquals(name,null);
        assertNotEquals(surname,null);
        assertNotEquals(gender,null);
        assertNotEquals(region,null);
    }
    /*Gender test
1. Create a request by providing query parameter: gender, male or female
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1*/
    @Test
    public void genderTest(){
        given().pathParam("gender", "male")
                .when().get("?gender={gender}").then().statusCode(200)
                .and().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("gender", equalTo("male"));

    }

    /*2 params test
1. Create a request by providing query parameters: a valid region and gender
NOTE: Available region values are given in the documentation
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1
4. Verify that value of region field is same from step 1
    * */
    @Test
    public void twoParamTest(){
        given().pathParam("region", "Germany")
                .and().pathParam("gender", "female")
                .get("?region={region}&gender={gender}")
                .then().statusCode(200).and().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("region", equalTo("Germany"))
                .and().assertThat().body("gender", equalTo("female"));
    }
    /*Invalid gender test
1. Create a request by providing query parameter: invalid gender
2. Verify status code 400 and status line contains Bad Request
3. Verify that value of error field is Invalid gender*/
    @Test
    public void invalidGenderTest(){
        given().pathParam("gender", "woman")
                .when().get("?gender={gender}")
                .then().statusCode(400)
                .and().statusLine("HTTP/1.1 400 Bad Request")
                .assertThat().body("error", equalTo("Invalid gender"));
    }

    /*Invalid region test
1. Create a request by providing query parameter: invalid region
2. Verify status code 400 and status line contains Bad Request
3. Verify that value of error field is Region or language not found*/
    @Test
    public void invalidRegionTest(){
        given().queryParam("region", "Antartica")
                .get("/")
                .then().statusCode(400).statusLine("HTTP/1.1 400 Bad Request")
                .assertThat().body("error", equalTo("Region or language not found"));
    }
/*Amount and regions test
1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that all objects have different name+surname combination
*/
    @Test
    public void amountAndRegionTest(){
        Response response = given().queryParam("amount", 5)
                .queryParam("region", "Germany")
                .when().get("/");


        JsonPath json = response.jsonPath();
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        Map<String, Object> name = json.getMap("/?amount=5&region=Germany");
        List<String> surname = json.getList("surname");




        int size= name.size();
        String fullname="";
        String otherFullname="";
        for (int i=0;i<size;i++){
            otherFullname=name.get(i) + " " + surname.get(i);
            for (int j=i+1;j<size;j++) {
                fullname = name.get(j) + " " + surname.get(j);
                System.out.println("fullname = " + fullname);
                assertNotEquals(otherFullname, fullname);
            }
        }


        System.out.println("Name = " + name.get(0));
        List<Object> jsonList = json.getList("items.findAll{it.name==\"Laura\"}.surname");
        System.out.println("jsonList = " + jsonList);
        System.out.println(json.getString("items.findAll.surname"));

        String s = response.path("items.name[0]");
        System.out.println("s = " + s);

    }
    /*3 params test
1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger
than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that all objects the response have the same region and gender passed in step 1
*/
    @Test
    public void threeParamsTest(){
        Response response = given().pathParam("amount", 10)
                .pathParam("region", "Germany")
                .pathParam("gender", "male")
                .get("/?amount={amount}&region={region}&gender={gender}");
        JsonPath json= response.jsonPath();
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
    }
    /*Amount count test
1. Create a request by providing query parameter: amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that number of objects returned in the response is same as the amount passed in step 1*/
    @Test
    public void amountCountTest(){
        given().accept(ContentType.JSON)
                .queryParam("amount",5)
                .when().get()
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json; charset=utf-8");
        Response response= given().accept(ContentType.JSON)
                .queryParam("amount",5).and().when().get();
        JsonPath jsonpath= response.jsonPath();
        List<Object> objects= jsonpath.getList("name");
        assertEquals(objects.size(), 5);
    }

}
