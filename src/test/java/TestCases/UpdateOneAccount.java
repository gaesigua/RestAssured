package TestCases;


import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class UpdateOneAccount {
		
		//In order to run the whole code, I will need to change the HTTPS Uri because it is not a valid POST baseURI
		
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
		
		//Since the .body() can accept a String, let's create 2 HashMaps.
		//One will be used to create the account, the other to Update the account; basically to pass the key and value; 
		
		HashMap<String,String> createBodyMap;
		HashMap<String,String> updateBodyMap;
		

		public Map<String,String> getCreateBodyMap(){
			
			createBodyMap = new HashMap<String,String>();
			
			createBodyMap.put("id", "2");
			createBodyMap.put("owner", "kana nar - ToAccount");
			createBodyMap.put("balance", "500");
			createBodyMap.put("currency", "COSMIC_COINS");
			createBodyMap.put("createdAt", "2024-04-04T19:25:38.000Z");
			
			return createBodyMap;
			
		}

		public Map<String,String> getUpdateBodyMap(){
			
			createBodyMap = new HashMap<String,String>();
			
			createBodyMap.put("id", "2");
			createBodyMap.put("owner", "kana nar - ToAccount");
			createBodyMap.put("balance", "500");
			createBodyMap.put("currency", "COSMIC_COINS");
			createBodyMap.put("createdAt", "2024-04-04T19:25:38.000Z");
			
			return createBodyMap;
			
		}
		
		
		@Test(priority=1)
		public void createOneAccount() {
			
		}
		
		@Test(priority=2)
		public void readAllAccounts() {
				
				
		//and grab the first account id				
			}
		
		public void 
			
		@Test (priority=4)
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
