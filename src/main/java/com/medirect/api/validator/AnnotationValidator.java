package com.medirect.api.validator;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class AnnotationValidator {
    // Use ThreadLocal to store SoftAssert instances for each test method
    private static final ThreadLocal<SoftAssert> softAssertThreadLocal = ThreadLocal.withInitial(SoftAssert::new);

    public static void validateResponse(Method method, Response response) {
        if (method.isAnnotationPresent(ValidateResponse.class)) {
            ValidateResponse validateResponse = method.getAnnotation(ValidateResponse.class);

            // Initialize SoftAssert if not already done
            SoftAssert softAssert = getSoftAssert();

            Allure.step("Verify response status code", () ->
                    softAssert.assertEquals(response.getStatusCode(), validateResponse.statusCode(),
                            "Status code does not match!")
            );

            Allure.step("Verify Content Type", () ->
                    softAssert.assertTrue(response.getContentType().contains(validateResponse.contentType().toString()),
                            String.format(
                                    "Response content type does not match! Expected [%s] but found [%s]",
                                    validateResponse.contentType().toString(),
                                    response.getContentType()
                            )
                    )
            );

            Allure.step("Verify response time", () ->
                    softAssert.assertTrue(response.getTime() <= validateResponse.maxResponseTime(),
                            String.format(
                                    "Response time exceeds maximum allowed! Expected [%s] to be lower than [%s]",
                                    response.getTime(),
                                    validateResponse.maxResponseTime()
                            )
                    )
            );
        }
    }

    // Call this method at the end of each test to evaluate all soft assertions
    public static void assertAll() {
        SoftAssert softAssert = softAssertThreadLocal.get();
        if (softAssert != null) {
            try {
                softAssert.assertAll();
            } finally {
                softAssertThreadLocal.remove();
            }
        }
    }

    private static SoftAssert getSoftAssert() {
        if (softAssertThreadLocal.get() == null) {
            softAssertThreadLocal.set(new SoftAssert());
        }

        return softAssertThreadLocal.get();
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        Allure.step("Verify equals", () ->
            getSoftAssert().assertEquals(actual, expected, message)
        );
    }

    public static void assertTrue(boolean condition, String message) {
        Allure.step("Verify true", () ->
                getSoftAssert().assertTrue(condition, message)
        );
    }
}