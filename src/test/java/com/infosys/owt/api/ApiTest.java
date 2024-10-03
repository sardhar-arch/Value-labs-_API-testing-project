package com.infosys.owt.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.restful-api.dev";
    }

    @Test
    public void addNewDevice() {
        String payload = "{\n" +
                "  \"name\": \"Apple Max Pro 1TB\",\n" +
                "  \"data\": {\n" +
                "    \"year\": 2023,\n" +
                "    \"price\": 7999.99,\n" +
                "    \"CPU model\": \"Apple ARM A7\",\n" +
                "    \"Hard disk size\": \"1 TB\"\n" +
                "  }\n" +
                "}";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/objects");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");
        String createdAt = response.jsonPath().getString("createdAt");
       int year = response.jsonPath().getInt("data.year");
        double price = response.jsonPath().getDouble("data.price");

        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(createdAt, "CreatedAt should not be null");
        Assert.assertEquals(name, "Apple Max Pro 1TB");
        Assert.assertEquals(year, 2023);
        Assert.assertEquals(price, 7999.99);
    }
}

