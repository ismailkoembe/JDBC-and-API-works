package apitests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class HRApiTestWithJsonPath {
    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("hrapi.uri");
    }


    @Test
    public void countriesWithJsonPath(){
        Response response = get("/countries");
        //put data inside json path
        JsonPath jsonData = response.jsonPath();

        //read second country name
        String secondCountryName = jsonData.getString("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        //get all country ids
        List<String> jsonDataList = jsonData.getList("items.country_id");
        jsonDataList.iterator().forEachRemaining(j-> System.out.println(j));

        //get countryNames off all countries region_id 2
        List<String> countryNamesWithRegion2 = jsonData.getList("items.findAll{it.region_id==2}.country_name");
        System.out.println("countryNamesWithRegion2 = " + countryNamesWithRegion2);



    }
    @Test
    public void findAllEmpExample(){
        //request
        Response response = given().queryParam("limit",150).get("/employees");

        //put response body to JsonPath object
        JsonPath jsonData = response.jsonPath();

        //get me all first_name of employees who is working as IT_PROG
        List<String> firstnames = jsonData.getList("items.findAll {it.job_id ==\"IT_PROG\"}.first_name");
        System.out.println("firstnames = " + firstnames);

        //get me all first_name of employees who is making more than 5k
        List<String> firstnames2 = jsonData.getList("items.findAll {it.salary > 5000}.first_name");
        System.out.println("firstnames2 = " + firstnames2);

    }


}
