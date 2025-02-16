package testCases;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

public class UpdateOneAccount{
	
	String baseURI = "https://abcd.com";
	String requestCreateContentTypeHeader = "application/json, charset=utf-8";
	String responseUpdateContentTypeHeader = "application/json, charset=utf-8";
	
	String bearerToken = "sdkewoodoeeeows";
	String endpoint_create = "/create.php";
	String endpoint_update = "/update.php";
	int actualResponseCreateStatusCode;
	int actualResponseUpdateStatusCode;
	
	String actualResponseUpdateContentTypeHeader;
	String actualResponseUpdateStatusMessage;
	
	String actualBody;
	JsonPath jsonPath;
	String actualExtractedResponseBodyMessage;
	
	String expectedCreateAccountResponseMessage = "Account was created";
	String expectedUpdateAccountResponseMessage = "Account was updated";
	
	String extractedFirstAccountId;
	
	SoftAssert softAssert = new SoftAssert();

//1. Let's initialize our FIRST Hashmap that will store our keys and values, we will use the keys and values when we start CREATING A New Account
	
	HashMap<String,String> createPayloadBodyMap;

//2. Let's now create its Hashmap method; we will call this method so that it returns the Hashmap keys and values
	
	public Map<String,String> getCreatePayloadBodyMap(){
		
		createPayloadBodyMap = new HashMap<String,String>();
		
		createPayloadBodyMap.put("id", "2");
		createPayloadBodyMap.put("owner", "gaesigua tin");
		createPayloadBodyMap.put("currency", "COMIC_COINS");
		createPayloadBodyMap.put("creation_date", "2020-01-01");
	
		
		return createPayloadBodyMap;
		
	}
	
//3. Let's initialize our SECOND Hashmap that will store our keys and values, we will use the keys and values when we start UPDATING the Account created earlier
	
	HashMap<String,String> updatePayloadBodyMap;
	
//4. Let's now create its Hashmap method; we will call this method so that it returns the Hashmap keys and values when we are UPDATING the Account
	
	public Map<String,String> getUpdatePayloadBodyMap(){
		
		updatePayloadBodyMap = new HashMap<String,String>();
		
		updatePayloadBodyMap.put("id", extractedFirstAccountId);
		updatePayloadBodyMap.put("owner", "kanam nar");
		updatePayloadBodyMap.put("currency", "SOLANA_COINS");
		updatePayloadBodyMap.put("creation_date", "2021-01-01");
		
		
		return updatePayloadBodyMap;
		
	}

	
//5. TO UPDATE ONE ACCOUNT, WE FIRST MAKE SURE WE HAVE A NEW ACCOUNT CREATED, SINCE SOMEONE MAY HAVE DELETED THE ACCOUNT WE WANT TO UPDATE; THIS IS CALLED A END TO END TESTING
	 // AND THEN AFTER CREATING THE ACCOUNT, WE WILL CHECK THE STATUS CODE: 201
	
	@Test (priority=1)
	public void createOneAccount() {
	
	Response response =
		given()
		.baseUri(baseURI)
		.header("Content-Type",requestCreateContentTypeHeader)
		.header("Authorization", bearerToken)
		.body(getCreatePayloadBodyMap()).
		when()
		.post(endpoint_create).
		then()
		.extract().response();
	
	//a. Let's validate the Status Code: 201
	
	actualResponseCreateStatusCode = response.getStatusCode();
	System.out.println("The Account Creation Response Status Code: " + actualResponseCreateStatusCode);
	
	softAssert.assertEquals(actualResponseCreateStatusCode, 200, "The Status Code Does Not Match!");
	
	//b. Let's the body, and the account creation by reading the first account id
	
    actualBody = response.getBody().asPrettyString();
	
    jsonPath = new JsonPath(actualBody);
    actualExtractedResponseBodyMessage = jsonPath.getString("message");
    System.out.println("The Response Body Message: " + actualExtractedResponseBodyMessage);

    softAssert.assertEquals(actualExtractedResponseBodyMessage, expectedCreateAccountResponseMessage, "The Account Creation Message Does Not Match!!");

    
    extractedFirstAccountId = jsonPath.getString("accounts[0].id");
    System.out.println("The First Account ID: " + extractedFirstAccountId);
    
    softAssert.assertAll();

		
	}
	
//4. TO CHECK IF THE ACCOUNT WAS PROPERLY CREATED, WE NEED TO GO BACK AND READ ALL THE ACCOUNTS AVAILABLE, AND EXTRACT THE LATEST ACCOUNT ID (BASICALLY THE FIRST ACCOUNT IN LINE)
	
	@Test (priority=2)
	public void readAllAccounts() {
		
		Response response =

		given()
		.baseUri(baseURI)
		.header("Content-Type","application/json; charset=utf-8")
		.header("Authorization",bearerToken)
		.queryParam("id",extractedFirstAccountId).
		when()
		.get("/info").
		then()
		.extract().response();

		
	//a. Now Let's check if all the accounts are there (including the account we created)
	         //We will do that by just extracting the First Account ID

	  String actualBody = response.getBody().asPrettyString();
	  
	  jsonPath = new JsonPath(actualBody);
	  extractedFirstAccountId = jsonPath.getString("accounts[0].id");
	  System.out.println("The First Account ID: " + extractedFirstAccountId);
		
	}
		
	//5. FINALLY, WE CAN NOW UPDATE THE NEWLY CREATED ACCOUNT, 
	     //AND THEN CHECK IF THE CURRENT VALUES WE HAVE ENTERED MATCH WITH WHAT WE HAD IN THE ReadOneAccount() 
	
	@Test (priority=3)
	public void updateOneProduct() {
		
		Response response =
				
				given()
				.baseUri(baseURI)
				.header("Content-Type", requestCreateContentTypeHeader)
				.header("Authorization", bearerToken)
				.body(getUpdatePayloadBodyMap()).
				when()
				.put(endpoint_update).
				then()
				.extract().response();
		
           //a. Let's validate the Status Code: 200

		actualResponseUpdateStatusCode = response.getStatusCode();
		System.out.println("The Update Status Code: " + actualResponseUpdateStatusCode);
		
		softAssert.assertEquals(actualResponseUpdateStatusCode, 200, "The Update Status Code Does Not Match!");
		
		   //b. Let's validate the Content Type Header
		
		actualResponseUpdateContentTypeHeader = response.getHeader("Content-Type");
		System.out.println("The Update Content Type Header: " + actualResponseUpdateContentTypeHeader);
		
		softAssert.assertEquals(actualResponseUpdateContentTypeHeader, responseUpdateContentTypeHeader);
		
		  //c. Let's validate the Message: Account was updated
		
		actualBody = response.getBody().asPrettyString();
		jsonPath = new JsonPath(actualBody);
		
		actualResponseUpdateStatusMessage = jsonPath.getString("accounts[0].message");
		System.out.print("The Update Message: "+ actualResponseUpdateStatusMessage);
		
		softAssert.assertEquals(actualResponseUpdateStatusMessage, expectedUpdateAccountResponseMessage, "The Update Message Does Not Match!");

		softAssert.assertAll();
	}
	
	//6. NOW THAT WE KNOW ALL THE ACCOUNTS ARE THERE, AND THE FIRST ACCOUNT WAS CREATED BY CHECKING ITS ID, LET'S CHECK ALL OF THE DETAILS OF THE LATEST ACCOUNT(ID, OWNER, CURRENCY, ETC)
	
	@Test (priority=4)
	public void readOneProduct() {
					
		}
	
	
	
	
}
