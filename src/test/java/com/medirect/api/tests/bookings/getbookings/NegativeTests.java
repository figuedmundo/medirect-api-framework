package com.medirect.api.tests.bookings.getbookings;

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
    @Story("Date invalid format")
    @Description("Test to validate server response when date format is invalid")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 400, contentType = ContentType.TEXT, maxResponseTime = 200)
    public void testGetBookingByID(ITestContext context) {
        Response response = RestClient.get("/booking?checkin=invalid");
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }
}