package TestCases;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class ReadAllProducts {
	@Test
	public void readAllProducts() {

		// 1. given: all input details (baseURI, Headers, Authorization, Payload/Body,
		// QueryParameters)
		// 2. when: submit api requests (Http method, Endpoint/Resource)
		// 3. then: validate response (Status code, Headers, responseTime, Payload/Body)

		given().baseUri("http://universities.hipolabs.com/search?country=United+States")
				.header("Content-Type", "application/json").auth().preemptive().basic("Admin", "admin123").
		when()
				.get("").
		
		then().statusCode(200);
	}

}
