package com.medirect.api.tests.bookings.deletebooking;

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

@Epic("Delete Booking")
@Feature("Negative Tests")
@Listeners({ValidateResponseListener.class})
public class NegativeTests extends BaseTest {

    @Test()
    @Story("Delete non existing booking")
    @Description("Test to validate server response when booking id doesn't exist")
    @Severity(SeverityLevel.MINOR)
    @ValidateResponse(statusCode = 404, contentType = ContentType.TEXT)
    public void testDeleteNonExistingBooking(ITestContext context) {
        Response response = RestClient.delete("/booking/" + 999999);
        context.setAttribute("apiResponse", response);
        AnnotationValidator.assertAll();
    }
}