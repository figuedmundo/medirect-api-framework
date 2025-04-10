package com.medirect.api.validator;

import io.restassured.http.ContentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateResponse {
    int statusCode();
    ContentType contentType() default ContentType.JSON;
    long maxResponseTime() default 200;
}