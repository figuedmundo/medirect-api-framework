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
@Feature("Boundary Tests")
@Listeners({ValidateResponseListener.class})
public class BoundaryTests extends BaseTest {

    @Test()
    @Story("Minimum Booking Id")
    @Description("Test to validate server response when booking id is minimum")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 200, contentType = ContentType.JSON, maxResponseTime = 200)
    public void testGetBookingByID(ITestContext context) {
        Response response = RestClient.get("/booking/" + 1);
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }

    @Test()
    @Story("Max Integer ID")
    @Description("Test to validate server response when booking id is maximum")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 404, contentType = ContentType.TEXT, maxResponseTime = 200)
    public void testInvalidId(ITestContext context) {
        Response response = RestClient.get("/booking/abc");
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }
}