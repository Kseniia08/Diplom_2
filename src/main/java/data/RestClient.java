package data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static constants.Constants.BASE_URL;
import static io.restassured.RestAssured.given;

public class RestClient {
    protected String URL = "https://stellarburgers.nomoreparties.site/api/";
    public final RequestSpecification reqSpec = given()
            .header("Content-type", "application/json")
            .baseUri(URL);
}
