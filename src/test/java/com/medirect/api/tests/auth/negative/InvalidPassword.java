package com.medirect.api.tests.auth.negative;

import com.medirect.api.BaseTest;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.models.AuthDto;
import com.medirect.api.utils.ConfigManager;
import com.medirect.api.utils.RestClient;
import com.medirect.api.validator.AnnotationValidator;
import com.medirect.api.validator.ValidateResponse;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Auth")
@Feature("Negative Tests")
@Listeners({ValidateResponseListener.class})
public class InvalidPassword extends BaseTest {
    private static final AuthDto payload = authPayload();

    private static AuthDto authPayload() {
        return AuthDto.builder()
                .username(ConfigManager.config().getUsername())
                .password("invalid")
                .build();
    }

    @Test()
    @Story("Invalid credentials")
    @Description("Test to validate invalid password")
    @Severity(SeverityLevel.CRITICAL)
    @ValidateResponse(statusCode = 401, contentType = ContentType.TEXT)
    public void testInvalidPassword(ITestContext context) {
        Response response = RestClient.post("/auth", payload);
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }
}