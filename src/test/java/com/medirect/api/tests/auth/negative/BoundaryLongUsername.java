package com.medirect.api.tests.auth.negative;

import com.medirect.api.BaseTest;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.models.AuthDto;
import com.medirect.api.utils.ConfigManager;
import com.medirect.api.utils.RestClient;
import com.medirect.api.utils.Utils;
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
public class BoundaryLongUsername extends BaseTest {
    private static final AuthDto payload = authPayload();

    private static AuthDto authPayload() {
        return AuthDto.builder()
                .username(Utils.randomString(1000))
                .password(ConfigManager.config().getPassword())
                .build();
    }

    @Test()
    @Story("Boundary long username")
    @Description("Test to validate boundary long username")
    @Severity(SeverityLevel.MINOR)
    @ValidateResponse(statusCode = 401, contentType = ContentType.TEXT)
    public void testBoundaryLongUsername(ITestContext context) {
        Response response = RestClient.post("/auth", payload);
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }
}