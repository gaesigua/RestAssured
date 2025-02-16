package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadAllUserAccounts {

	@Test
	public void readAllUserAccounts() {

		// 0. Let's create a variable for the baseURI

		String baseURI = "https://template.postman-echo.com";

		// 1. Let's create a variable for the Resource/Endpoint

		String endpoint = "/api/v1/accounts";

		// 2. Let's create a reference variable for the Response interface
		// the reference variable will hold the given (baseURI, Headers, Authorization,
		// Payload/Body, QueryParameters), when (get), and then (extract, response)

		Response response = given()
				.baseUri(baseURI).header("api-key", "OMpqVWAH.UC80wyXTtPwhDgAUdCTx6")
				.header("Content-Type", "application/json; charset=utf-8").

				when()
				.get(endpoint).
				then()
				.extract().response();

		// 3. Let's validate the status code and perform the assertion

		int actualStatusCode = response.getStatusCode();

		System.out.println("The Status Code is: " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);

		// 4. Let's validate the Response Time and perform the assertion

		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);

		System.out.println("The Response Time is: " + actualResponseTime);
		if (actualResponseTime <= 2000) {
			System.out.println("The Response Time is within the range");
		} else {
			System.out.println("The Response Time is out of range!!");
		}

        //5. Let's validate the Header and perform assertion

		String actualHeaderContentType = response.getHeader("Content-Type");

		System.out.println("The Header Content Type is: " + actualHeaderContentType);
		Assert.assertEquals(actualHeaderContentType, "application/json; charset=utf-8");

        //6. Let's validate the Body

		String actualBody = response.getBody().asPrettyString();
		System.out.println("The body content is: " + actualBody);

        //7. Let's validate if the first id's content is available, and if it is available we will print it out

		JsonPath jsonPath = new JsonPath(actualBody);

		String extractedBodyContent = jsonPath.getString("accounts[0].id");

		if (extractedBodyContent != null) {
			System.out.println("The first id of the body is: " + extractedBodyContent);
		} else {
			System.out.println("The body is empty!!");
		}
	}
}
