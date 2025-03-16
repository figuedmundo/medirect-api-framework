package com.medirect.api.core;

import com.medirect.api.core.ValidateResponse;
import io.restassured.response.Response;
import org.testng.Assert;

import java.lang.reflect.Method;

public class AnnotationValidator {
    public static void validateResponse(Method method, Response response) {
        if (method.isAnnotationPresent(ValidateResponse.class)) {
            ValidateResponse validateResponse = method.getAnnotation(ValidateResponse.class);

            // Validate status code
            Assert.assertEquals(response.getStatusCode(), validateResponse.statusCode(),
                    "Status code does not match!");

            // Validate content type
            Assert.assertTrue(response.getContentType().contains(validateResponse.contentType().toString()),
                    "Response content type does not match!");

            // Validate response time
            Assert.assertTrue(response.getTime() <= validateResponse.maxResponseTime(),
                    "Response time exceeds maximum allowed!");
        }
    }
}
