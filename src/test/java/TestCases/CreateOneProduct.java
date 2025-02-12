package TestCases;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class CreateOneProduct {
	
	@Test
	public void createOneProduct() {
		
		String baseURI = "";
		
		Response response = 
				
				given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json, charset=utf-8")
				.header("api-key", "")
				.queryParam("id", "352").
				
				when()
				.get("").
				
				then()
				.extract().response();
				;
		
		
		
	}

}
