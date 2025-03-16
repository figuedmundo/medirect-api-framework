package com.medirect.api.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestClient {
    private static final String BASE_URL = ConfigManager.config().getBaseUrl();
    private static final String USERNAME = ConfigManager.config().getUsername();
    private static final String PASSWORD = ConfigManager.config().getPassword();
    private static String token;

    private static RequestSpecification givenRequest() {
        RequestSpecification request = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filter(new AllureRestAssured());

//        // Add headers if provided
//        if (headers != null) {
//            request.headers(headers);
//        }

        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }

        return request;
    }

    public static void authenticate() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\" }")
                .post(BASE_URL + "/auth");

        if (response.getStatusCode() == 200) {
            token = response.jsonPath().getString("token");
        } else {
            throw new RuntimeException("Authentication failed. Status code: " + response.getStatusCode());
        }
    }

    public static Response get(String endpoint) {
        return givenRequest()
                .when()
                .get(endpoint)
                .then()
                .log().all() // Log request/response for debugging
                .extract()
                .response();
    }

    public static Response post(String endpoint, Object body) {
        return givenRequest()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object body) {
        return givenRequest()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        return givenRequest()
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
