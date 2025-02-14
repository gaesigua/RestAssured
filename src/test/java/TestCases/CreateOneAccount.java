package TestCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateOneAccount {
	
	//In order to run the whole code, I need to change the HTTPS uri because it is not a valid POST baseURI
	
	String baseURI = "https://postman-rest-api-learner.glitch.me/";
	
	String createProductRequestHeader = "application/json, charset=utf-8";
	String actualContentTypeResponseHeader;
	String bearerToken = "Bearer dflkjslkjlkeoweroiuoslkdj";
	int actualStatusCode;
	String actualBody;
	JsonPath jsonPath;
	String actualExtractedResponseBodyMessage;
	String expectedCreateAccountResponseMessage = "Account was created!";
	String extractedFirstAccountId;
	String actualResponseBody;
	
	SoftAssert softAssert = new SoftAssert();

	
	@Test(priority=1)
	public void createOneAccount() {
		
		
		Response response = 
				
				given()
				.baseUri(baseURI)
				.header("Content-Type", createProductRequestHeader)
				.header("Authorization", bearerToken)
				.body(new File("\\test\\java\\myFiles\\CreateOneAccount.json")).		
				when()
				.post("/api/v1/accounts").
				
				then()
				.extract().response();
				;
				
				
				
		//1. Now Let's validate the 201 Status code: meaning that the Account has been created
				
			actualStatusCode = response.getStatusCode();
			
			System.out.println("The Status Code: " + actualStatusCode);
			
			softAssert.assertEquals(actualStatusCode, 201, "The Status Code Does Not Match!!");
		
		//2. Now Let's validate the Content Type Header
			
		actualContentTypeResponseHeader	= response.getHeader("Content-Type");
		
		System.out.println("The Content Type Header: " + actualContentTypeResponseHeader);
		
		softAssert.assertEquals(actualContentTypeResponseHeader, createProductRequestHeader, "The Content Type Header Does Not Match!!!");
		
		//3. Now Let's validate the Response Time
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		
		System.out.println("The Response Time: " + actualResponseTime);
		
		if(actualResponseTime<=2000) {
			
			System.out.println("The Response Time Is Within The Range!!");
			
		}else {
			
			System.out.println("The Response Time Is Out Of Range!!");
			
		}
		
		//4. When An Account gets created, there's a message that is printed out in the Response Body/Area of the Postman; something like "message": Account was created
		//so how do we validate that the account was created? We read what is in the Response Body
		//Remember that what is printed out in the body is a json file, so how do we extract something from a json file
		
		actualBody = response.getBody().asPrettyString();
		
		jsonPath = new JsonPath(actualBody);
	    actualExtractedResponseBodyMessage = jsonPath.getString("message");
		
		System.out.println("The Response Body Message: " + actualExtractedResponseBodyMessage);
		
		softAssert.assertEquals(actualExtractedResponseBodyMessage, expectedCreateAccountResponseMessage, "The Account Creation Message Does Not Match!!");
		
		softAssert.assertAll();
		
		
	}
		@Test(priority=1)
		public void readAllAccounts() {
			
			
			//Since we might want to validate many parameters, how can we make sure that when one validation fails, the following ones will not fail also;
			// We will achieve that by creating a variable (Interface) called Response and save the steps inside that variable; and we introduce the extract() method
			
		
			Response response =

			given()
			.baseUri(baseURI)
			.header("Content-Type","application/json; charset=utf-8")
			.auth().none().
			when()
			.get("/info").
			then()
			.extract().response();
			
			
			//1. Now Let's validate the body; 
			
			actualResponseBody = response.getBody().asPrettyString();
			
			System.out.println("The Response Body is: " + actualResponseBody);
			
			//Is there a way we can validate that the response body is not empty/null, and contains some values
			//Keep in mind that we have the response body as a String, so we need to use a different class called JsonPath, 
			//and then build its constructor by parameterizing our response body variable as a String, 
			//and then click on the class to instruct it to convert the parameterized variable to a Json file, 
			//and then save it as JsonPath variable; basically we are changing the response body back to a String 
			
			jsonPath = new JsonPath(actualResponseBody);
			
			//Now Let's extract the First Account ID
			
			extractedFirstAccountId = jsonPath.getString("accounts[0].id");
			
			System.out.println("The First Account ID: " + extractedFirstAccountId);
			
			
			
			
		}
	}
