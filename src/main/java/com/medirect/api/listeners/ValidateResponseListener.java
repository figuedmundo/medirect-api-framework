package com.medirect.api.listeners;

import com.medirect.api.validator.AnnotationValidator;
import com.medirect.api.validator.ValidateResponse;
import io.restassured.response.Response;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Method;

public class ValidateResponseListener extends TestListenerAdapter {
    @Override
    public void onTestSuccess(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();

        if (method.isAnnotationPresent(ValidateResponse.class)) {
            Object responseAttribute = result.getTestContext().getAttribute("apiResponse");

            if (responseAttribute instanceof Response) {
                AnnotationValidator.validateResponse(method, (Response) responseAttribute);
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        onTestSuccess(result); // Also validate if the test fails
    }
}