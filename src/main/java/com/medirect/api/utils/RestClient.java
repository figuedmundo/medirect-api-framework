package com.medirect.api.utils;

import com.medirect.api.models.AuthDto;
import com.medirect.api.models.TokenDto;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import java.util.Map;

public class RestClient {
    private static final String BASE_URL = ConfigManager.config().getBaseUrl();
    private static final String USERNAME = ConfigManager.config().getUsername();
    private static final String PASSWORD = ConfigManager.config().getPassword();
    @Getter
    private static String token;

    static {
        RestAssured.filters(new AllureRestAssured()); // Attach API logs to Allure
    }

    private static RequestSpecification givenRequest(Map<String, String> headers) {
        RequestSpecification request = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
//                .filter(new AllureRestAssured());

        // Add headers if provided
        if (headers != null) {
            request.headers(headers);
        }

        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }

        return request;
    }

    public static void authenticate() {
        AuthDto authRequest = AuthDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .post(BASE_URL + "/auth");

        if (response.getStatusCode() == 200) {
            token = response.as(TokenDto.class).getToken();
        } else {
            throw new RuntimeException("Authentication failed. Status code: " + response.getStatusCode());
        }
    }

    public static void deleteToken() {
        token = null;
    }

    public static Response get(String endpoint) {
        return get(endpoint, null);
    }

    public static Response get(String endpoint, Map<String, String> headers) {
        return givenRequest(headers)
                .when()
                .get(endpoint)
                .then()
                .log().all() // Log request/response for debugging
                .extract()
                .response();
    }

    public static Response post(String endpoint, Object body) {
        return post(endpoint, body, null);
    }

    public static Response post(String endpoint, Object body, Map<String, String> headers) {
        return givenRequest(headers)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object body) {
        return put(endpoint, body, null);
    }

    public static Response put(String endpoint, Object body, Map<String, String> headers) {
        return givenRequest(headers)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response patch(String endpoint, Object body) {
        return patch(endpoint, body, null);
    }

    public static Response patch(String endpoint, Object body, Map<String, String> headers) {
        return givenRequest(headers)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        return delete(endpoint, null);
    }

    public static Response delete(String endpoint, Map<String, String> headers) {
        return givenRequest(headers)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
