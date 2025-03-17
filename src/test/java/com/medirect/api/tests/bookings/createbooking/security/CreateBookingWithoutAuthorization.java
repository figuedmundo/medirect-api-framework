package com.medirect.api.tests.bookings.createbooking.security;

import com.medirect.api.BaseTest;
import com.medirect.api.validator.AnnotationValidator;
import com.medirect.api.validator.ValidateResponse;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.models.BookingDatesDto;
import com.medirect.api.models.BookingDto;
import com.medirect.api.utils.RestClient;
import com.medirect.api.utils.Utils;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Create Booking")
@Feature("Security Tests")
@Listeners({ValidateResponseListener.class})
public class CreateBookingWithoutAuthorization extends BaseTest {
    private static final BookingDto booking = generateBooking();

    private static BookingDto generateBooking() {
        return BookingDto.builder()
                .firstname(Utils.randomString(6))
                .lastname(Utils.randomString(8))
                .totalprice(Utils.randomInt(100, 1000))
                .depositpaid(Utils.randomBoolean())
                .bookingdates(BookingDatesDto.builder()
                        .checkin(Utils.getDate())
                        .checkout(Utils.getDate("+1d"))
                        .build())
                .additionalneeds(Utils.randomString(10))
                .build();
    }

    @Test()
    @Story("Create Booking without Authorization")
    @Description("Test to validate unauthorized access to create a booking")
    @Severity(SeverityLevel.CRITICAL)
    @ValidateResponse(statusCode = 403, contentType = ContentType.TEXT, maxResponseTime = 1000)
    public void testCreateBookingWithoutAuthorization(ITestContext context) {
        Response response = RestClient.post("/booking", booking);
        context.setAttribute("apiResponse", response);

        AnnotationValidator.assertAll();
    }
}