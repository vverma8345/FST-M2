package liveProjects;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class GitHub_RestAssured_Project {
    RequestSpecification req;
    int id=0;
    final String baseURI ="https://api.github.com";

    @BeforeClass
    public void setup(){
        req =  new RequestSpecBuilder().setContentType(ContentType.JSON).
                addHeader("Authorization","token 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").
                setBaseUri(baseURI).build();
    }
    @Test(priority=0)
    public void postTest(){

    String filePath = System.getProperty("user.dir") + "\\src\\TestFiles\\body.json";
    System.out.println(filePath);

    File reqFile = new File(filePath);

    Response response = given().
            spec(req).
            body(reqFile).
            post("/user/keys");
    System.out.println(response.asPrettyString());
    id = response.then().extract().path("id");
    response.then().statusCode(201);
    }
    @Test(priority=1)
    public void getTest(){
        Response response = given().
                spec(req).
                get(" /user/keys");
        response.then().statusCode(200);
        Reporter.log("Response for getTest() is : \n\n" + response.asPrettyString());
        System.out.println(response.asPrettyString());
    }
    @Test(priority = 2)
    public void deleteTest(){
        Response response = given().
                spec(req).
                pathParam("keyId",id).
                delete("/user/keys/{keyId}");
        response.then().statusCode(204);
        Reporter.log("Response for deleteTest() is : \n\n"+response.asPrettyString());

    }
}
