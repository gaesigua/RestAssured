package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadEachDetailInEachAccount {
	@Test
	public void readEachDetailInEachAccount() {
		
		Response response =
				
				given()
				.baseUri("https://template.postman-echo.com")
				.header("Content-Type", "application/json; charset=utf-8")
				.header("api-key", "OMpqVWAH.UC80wyXTtPwhDgAUdCTx6")
				.queryParam("id", "159309").
				
				when()
				.get("/api/v1/accounts").
				
				then()
				.extract().response();
				
		
		//1. Let's validate the status code and perform the assertion
		
		int actualStatusCode = response.getStatusCode();
		
		System.out.println("The Status Code is: " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		
		//2. Let's validate the Response Time and perform the assertion
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		
		System.out.println("The Response Time is: " + actualResponseTime);
		if(actualResponseTime <= 2000) {
			System.out.println("The Response Time is within the range");
		}else {
			System.out.println("The Response Time is out of range!!");
		}
		
//		//3. Let's validate the Header and perform assertion

		String actualHeaderContentType = response.getHeader("Content-Type");
		
		System.out.println("The Header Content Type is: " + actualHeaderContentType);
		Assert.assertEquals(actualHeaderContentType, "application/json; charset=utf-8");

//		//4. Let's validate the Body
		
		String actualBody = response.getBody().asPrettyString();
		System.out.println("The body content is: " + actualBody);
		
//		//5. Let's validate if the first id's content is available, and if it is available we will print it out
		
		JsonPath jsonPath = new JsonPath(actualBody);
		
		String extractedBodyContent = jsonPath.getString("accounts[0].id");
		
		if(extractedBodyContent!= null) {
			System.out.println("The first id of the body is: " + extractedBodyContent);
		}else {
			System.out.println("The body is empty!!");
			}

				
		
	}

}
