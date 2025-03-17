package com.medirect.api.tests.auth.positive;

import com.medirect.api.BaseTest;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.models.AuthDto;
import com.medirect.api.models.TokenDto;
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
@Feature("Positive Tests")
@Listeners({ValidateResponseListener.class})
public class CreateToken extends BaseTest {
    private static final AuthDto payload = authPayload();

    private static AuthDto authPayload() {
        return AuthDto.builder()
                .username(ConfigManager.config().getUsername())
                .password(ConfigManager.config().getPassword())
                .build();
    }

    @Test()
    @Story("Create Token")
    @Description("Test to validate successful creation of a token")
    @Severity(SeverityLevel.CRITICAL)
    @ValidateResponse(statusCode = 200, contentType = ContentType.JSON, maxResponseTime = 200)
    public void testCreateToken(ITestContext context) {
        Response response = RestClient.post("/auth", payload);
        context.setAttribute("apiResponse", response);
        TokenDto responsePayload = response.as(TokenDto.class);

        AnnotationValidator.assertTrue(responsePayload.token != null, "Token is null");
        AnnotationValidator.assertTrue(!responsePayload.token.isEmpty(), "Token is empty");
        AnnotationValidator.assertTrue(!responsePayload.token.isBlank(), "Token is Blank");
        AnnotationValidator.assertAll();
    }
}