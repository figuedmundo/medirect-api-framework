package com.medirect.api.tests.bookings.getbookingbyid;

import com.medirect.api.BaseTest;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.utils.RestClient;
import com.medirect.api.validator.AnnotationValidator;
import com.medirect.api.validator.ValidateResponse;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Get Booking by ID")
@Feature("Negative Tests")
@Listeners({ValidateResponseListener.class})
public class NegativeTests extends BaseTest {

    @Test()
    @Story("Booking non existing")
    @Description("Test to validate server response when booking id doesn't exist")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 404, contentType = ContentType.TEXT, maxResponseTime = 200)
    public void testGetBookingByID(ITestContext context) {
        Response response = RestClient.get("/booking/" + 999999999);
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }

    @Test()
    @Story("Invalid Id")
    @Description("Test to validate server response when booking id is invalid")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 400, contentType = ContentType.TEXT, maxResponseTime = 200)
    public void testInvalidId(ITestContext context) {
        Response response = RestClient.get("/booking/abc");
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }
}