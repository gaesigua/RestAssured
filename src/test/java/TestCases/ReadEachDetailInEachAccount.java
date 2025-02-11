package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadEachDetailInEachAccount {
	@Test
	public void readEachDetailInEachAccount() {
		
		//0. Let's create a variable for the baseURI
		
		String baseURI = "https://template.postman-echo.com";
		
		//1. Let's create a variable for the Resource/Endpoint
		
		String endpoint = "/api/v1/accounts";
		
		
		//2. Let's create a reference variable for the Response interface 
		// the reference variable will hold the given (baseURI, Headers, Authorization, Payload/Body, QueryParameters), when (get), and then (extract, response)
		
		SoftAssert softAssert = new SoftAssert();

		Response response =
				
				given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json; charset=utf-8")
				.header("api-key","OMpqVWAH.UC80wyXTtPwhDgAUdCTx6")
				.queryParam("id", "248439")
				.queryParam("balance", "60").

				
				when()
				.get(endpoint).
				
				then()
				.extract().response();
				
		
		//3. Let's validate the status code and perform the assertion
		
		int actualStatusCode = response.getStatusCode();
		
		System.out.println("The Status Code is: " + actualStatusCode);
		
		// Now we could use the Assert.assertEquals to perform the assertion, but since we have many assertions to perform, when the first one fails, the following assertions cannot be performed
		// so instead of using Assert.assertEquals, we will use softAssert.assertEquals, so that it can continue to the next assertion in case one assertion, we make sure we create it's method before using it.
		// Let's fail this assertion on purpose by using 210 as the expected status code instead of 200, and we will add a comment that will be printed out
		softAssert.assertEquals(actualStatusCode, 210, "The Status codes are not matching!"); 
		
		//2. Let's validate the Response Time and perform the assertion
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		
		System.out.println("The Response Time is: " + actualResponseTime);
		if(actualResponseTime <= 2000) {
			System.out.println("The Response Time is within the range");
		}else {
			System.out.println("The Response Time is out of range!!");
		}
		
		//3. Let's validate the Header and perform assertion

		String actualHeaderContentType = response.getHeader("Content-Type");
		
		System.out.println("The Header Content Type is: " + actualHeaderContentType);		
		softAssert.assertEquals(actualHeaderContentType, "application/json; charset=utf-8", "The Response Headers do not match");

		//4. Let's validate the Body
		
		String actualBody = response.getBody().asPrettyString();
		System.out.println("The body content is: " + actualBody);
		
		//5. Let's create an Object of a JsonPath, and then validate if the ID of the first Account is available, and if it is, we will print out the id number 
		//   remember we used the queryParam to search for the ID)	
		
		JsonPath jsonPath = new JsonPath(actualBody);
		
		String extractedFirstAccountId = jsonPath.getString("accounts[0].id");
		System.out.println("The Account Id: " + extractedFirstAccountId);
		softAssert.assertEquals(extractedFirstAccountId, "248439", "The Account IDs are not matching!");
		
		//6. Using the Object of a JsonPath created above, and let's also validate if the balance of the first Account is available, and if it is, we print out its amount available 
				//   remember we used the queryParam to search for the balance)	
				
		String extractedFirstAccountBalance = jsonPath.getString("accounts[0].balance");
        System.out.println("The Account Balance: " + extractedFirstAccountBalance);
        
        //Here we want to fail the assertion on purpose by not writing the correct amount available in the account, and since we are using the softAssert, the assertion will continue onto the next assertion
        softAssert.assertEquals(extractedFirstAccountBalance, "60000000000", "The Account Balance are not matching!!");		
		
		//7. Using the Object of a JsonPath created above, and let's also validate the currency of the first account, and we check if it is the type of currency we want (COSMIC_COINS)
		//   remember we used the queryParam to search for the balance)	
	
        String extractedFirstAccountCurrency = jsonPath.getString("accounts[0].currency");
        System.out.println("The Account Currency: " + extractedFirstAccountCurrency);
        softAssert.assertEquals(extractedFirstAccountCurrency, "COSMIC_COINS", "The Account Currency are not matching!!");	
        
        
        //8. Using the Object of a JsonPath created above, let's check when the account was created
        
      //7. Using the Object of a JsonPath created above, and let's also validate the currency of the first account, and we check if it is the type of currency we want (COSMIC_COINS)
      		//   remember we used the queryParam to search for the balance)	
      	
         String extractedFirstAccountCreationDate = jsonPath.getString("accounts[0].createdAt");
         System.out.println("The Account Currency: " + extractedFirstAccountCreationDate);
         softAssert.assertEquals(extractedFirstAccountCreationDate, "2024-09-09T07:22:30.000Z", "The Account creation Dates are not matching!!");		
      	
         //Using the softAssert makes it look like it is skipping each assert, now how do we make sure it does perform all the assertions? we use the .assertAll()
         
         softAssert.assertAll();
		
	}

}
