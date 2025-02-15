package TestCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import java.io.File;
import java.util.HashMap;
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
	String actualAccountIdFromResponse;
	String actualAccountOwnerFromResponse;
	String actualAccountBalanceFromResponse;
	String actualAccountCurrencyFromResponse;
	
	SoftAssert softAssert = new SoftAssert();
	
	//Since the .body() can accept a String, let's create a HashMap that will save a key and value both strings;
	// after creating the HashMap, we will create a method, to be able to pass in the values; and make sure we make the method has a return statement of a HashMap, that will be used later on
	
	
	HashMap<String,String> createBodyMap;

	public HashMap<String,String> getBodyMap(){
		
		createBodyMap = new HashMap<String,String>();
		
		createBodyMap.put("id", "1111");
		createBodyMap.put("owner", "gaesigua tin - ToAccount");
		createBodyMap.put("balance", "20");
		createBodyMap.put("currency", "COSMIC_COINS");
		createBodyMap.put("createdAt", "2024-04-04T19:25:38.000Z");
		
		return createBodyMap;
		
	}
	
	
	@Test(priority=1)
	public void createOneAccount() {
		
		
		Response response = 
				
				given()
				.baseUri(baseURI)
				.header("Content-Type", createProductRequestHeader)
				.header("Authorization", bearerToken)
				.body(getBodyMap()).				
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
		//so how we validate that the account was created by reading what is in the Response Body
		//Remember that what is printed out in the body is a json file, so how do we extract something from a json file
		
		actualBody = response.getBody().asPrettyString();
		
		jsonPath = new JsonPath(actualBody);
	    actualExtractedResponseBodyMessage = jsonPath.getString("message");
		
		System.out.println("The Response Body Message: " + actualExtractedResponseBodyMessage);
		
		softAssert.assertEquals(actualExtractedResponseBodyMessage, expectedCreateAccountResponseMessage, "The Account Creation Message Does Not Match!!");
		
		softAssert.assertAll();
		
		
	}
		@Test(priority=2)
		public void readAllAccounts() {
			
			Response response =

			given()
			.baseUri(baseURI)
			.header("Content-Type","application/json; charset=utf-8")
			.header("Authorization",bearerToken)
			.queryParam("id","999").
			when()
			.get("/info").
			then()
			.extract().response();
			
			
			//1. Now Let's validate the body and grab/read the First account Id that was created; 
			
			actualResponseBody = response.getBody().asPrettyString();
			System.out.println("The Response Body is: " + actualResponseBody);
			
		
			jsonPath = new JsonPath(actualResponseBody);
			
			//Now Let's extract the First Account ID
			
			extractedFirstAccountId = jsonPath.getString("accounts[0].id");
			
			System.out.println("The First Account ID: " + extractedFirstAccountId);
			
			softAssert.assertEquals(extractedFirstAccountId, 999, "The Account Id Does Not Match!");			
					
		}
		
		@Test (priority=3)
		public void readOneAccount() {
			
			Response response = 
					
					given()
					.baseUri(baseURI)
					.header("Content-Type", createProductRequestHeader)
					.header("Authorization", bearerToken)
					.queryParam("id", extractedFirstAccountId).
					when()
					.get("/info/").
					
					then()
					.extract().response();
					
			//1. Now Let's validate the body, and then check the First Account Id
			    
			actualBody = response.getBody().asPrettyString();
			
			jsonPath = new JsonPath(actualBody);
		    
			actualAccountIdFromResponse = jsonPath.getString("accounts[0].id");
			System.out.println("The First Account Id: " + actualAccountIdFromResponse);
			softAssert.assertEquals(actualAccountIdFromResponse, getBodyMap().get("id"), "The Account Id Does Not Match!!");
			
			
			
            actualAccountOwnerFromResponse = jsonPath.getString("accounts[0].owner");
			System.out.println("The First Account Owner: " + actualAccountOwnerFromResponse);
			softAssert.assertEquals(actualAccountOwnerFromResponse, getBodyMap().get("owner"), "The Account Owner Does Not Match!!");
			
			
			actualAccountBalanceFromResponse = jsonPath.getString("accounts[0].balance");
			System.out.println("The First Account Balance: " + actualAccountBalanceFromResponse);
			softAssert.assertEquals(actualAccountBalanceFromResponse, getBodyMap().get("balance"), "The Account Balance Does Not Match!!");
			
			
			actualAccountCurrencyFromResponse = jsonPath.getString("accounts[0].currency");
			System.out.println("The First Account Currency: " + actualAccountCurrencyFromResponse);
			softAssert.assertEquals(actualAccountCurrencyFromResponse, getBodyMap().get("currency"), "The Account Currency Does Not Match!!");
			
			
			
			softAssert.assertAll();
			

			
			//4. Now Let's validate the Response Time
			
			long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
			
			System.out.println("The Response Time: " + actualResponseTime);
			
			if(actualResponseTime<=2000) {
				
				System.out.println("The Response Time Is Within The Range!!");
				
			}else {
				
				System.out.println("The Response Time Is Out Of Range!!");
				
			}
			

			
			
			
			
			
		}
	}
