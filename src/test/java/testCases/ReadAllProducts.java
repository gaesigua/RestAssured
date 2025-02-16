package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadAllProducts {
	
	String baseUri = "https://postman-rest-api-learner.glitch.me/";
	
	@Test
	public void readAllProducts() {
		
		
		//Since we might want to validate many parameters, how can we make sure that when one validation fails, the following ones will not fail also;
		// We will achieve that by creating a variable (Interface) called Response and save the steps inside that variable; and we introduce the extract() method
		
	
		Response response =

		given()
		.baseUri(baseUri)
		.header("Content-Type","application/json; charset=utf-8")
		.auth().none().
		when()
		.get("/info").
		then()
		.extract().response();
		
		
		//1. Now Let's validate the status code, by first saving the result into a variable, and then do an assertion comparing the variable which is the actual status code to the expected status code
		
		int actualStatusCode = response.getStatusCode();
		
		System.out.println("The Status Code is: " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		
		//2. Now Let's validate the response time; the getTimein returns a long variable, so we will save the result accordingly; and since the run time changes constantly, we will have to use an if else statement in order to assert
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		
		System.out.println("The Response Time is: " +actualResponseTime);
		
		if(actualResponseTime <= 2000) {
			System.out.println("Responde time is within the range.");
		}else {
			System.out.println("Response time is out of range.");
		}
		
		//3. Let's validate one of the headers: Content-Type; the result is a String, so we will save it accordingly;
		
		String actualContentType = response.getHeader("Content-Type");
		
		System.out.println("The Content Type Header is: " + actualContentType);
		Assert.assertEquals(actualContentType,  "application/json; charset=utf-8");
		
		//4. Let's now validate the body; here we have to be careful since getBody() returns something that is not neither a String, nor an int, nor anything that Java recongnizes; 
		// so must add asString() or asPrettyString (Pretty will print the result in a pretty format) method to it, so that java recognizes it as a String, and then we will be able to save it as a string
		
		String responseBody = response.getBody().asPrettyString();
		
		System.out.println("The Response Body is: " +responseBody);
		
		//Is there a way we can validate that the response body is not empty/null, and contains some values
		//Keep in mind that we have the response body as a String, so we need to use a different class called JsonPath, 
		//and then build its constructor by parameterizing our response body variable as a String, 
		//and then click on the class to instruct it to convert the parameterized variable to a Json file, 
		//and then save it as JsonPath variable; basically we are changing the response body back to a String 
		
		JsonPath jsonPath = new JsonPath(responseBody);
		
		//Now Let's extract a content (in my case it's a message that is inside the json file); 
		//Just remember to convert the Json text to a Json path to work faster, and then save the result into a String variable
		
		String extractedBodyContent = jsonPath.getString("message");
		
		System.out.println("First message of the file is: " + extractedBodyContent);
		
		if(extractedBodyContent!=null) {
			System.out.println("The body is not null!!");
			
		}else {
			System.out.println("The body is null!!");
		}
		
		
	}
}