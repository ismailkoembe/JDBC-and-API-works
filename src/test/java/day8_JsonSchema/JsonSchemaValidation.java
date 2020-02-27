package day8_JsonSchema;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import  static io.restassured.module.jsv.JsonSchemaValidator.*;
public class JsonSchemaValidation {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI= ConfigurationReader.get("spartanapi.uri");
    }

    @Test
    public void jsonSchemaValidationForSpartan(){
        given().accept(ContentType.JSON)
                .pathParam("id", 5)
                .when().get("/spartans/{id}").then()
                .statusCode(200).body(matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
    }


}
