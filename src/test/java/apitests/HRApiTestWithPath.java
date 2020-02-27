package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class HRApiTestWithPath {
    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("hrapi.uri");
    }
    @Test
    public void getCountriesWithPath() {
        Response response = given().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(), 200);

        System.out.println(response.path("count").toString());

        /*
        items.country_id[0] --> AR
        items.country_name[1]   -->Brazil
        items.links[2].href --> "http://54.161.128.36:1000/ords/hr/countries/CA"
        items.country_name --> list with all countries names
         */

        String firstCountryId = response.path("items.country_id[0]");
        System.out.println("firstCountryId = " + firstCountryId);

        String link2 = response.path("items.links[2].href[0]");
        System.out.println("link2 = " + link2);

        List<String> allCountries= response.path("items.country_name");

        allCountries.forEach(a-> System.out.println(a));

        List<Object> num = response.path("items.region_id");
        num.forEach(n->assertEquals(n,2));
    }
        @Test
        public void countriesWithQueryParam2(){
            Response response = given().accept(ContentType.JSON).and()
                    .queryParam("q","{\"job_id\":\"IT_PROG\"}")
                    .when().get("/employees");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json");
            assertTrue(response.body().asString().contains("IT_PROG"));

            List<String> jobIds=response.body().path("items.job_id");
            jobIds.forEach(j->assertEquals(j, "IT_PROG"));

      // Iterator forEachRemaining
             Iterator jobIT=jobIds.iterator();
             jobIT.forEachRemaining(job -> assertEquals(job,"IT_PROG"));
                System.out.println("jobID = " + jobIds);
      //Iterator
            System.out.println("------------------------------");
            Iterator<String> it=jobIds.iterator();
            while(it.hasNext()) {
            String jobITS=it.next();
            System.out.println("jobITS = " + jobITS);
            assertEquals(jobITS,"IT_PROG");
            }


    }

}



