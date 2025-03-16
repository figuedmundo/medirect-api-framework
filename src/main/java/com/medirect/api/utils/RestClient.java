package com.medirect.api.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestClient {
    private static final String BASE_URL = ConfigManager.config().getBaseUrl();

    private static RequestSpecification givenRequest() {
        RequestSpecification request = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

//        // Add headers if provided
//        if (headers != null) {
//            request.headers(headers);
//        }

        return request;
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
