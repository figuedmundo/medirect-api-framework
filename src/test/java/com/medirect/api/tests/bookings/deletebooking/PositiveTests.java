package com.medirect.api.tests.bookings.deletebooking;

import com.medirect.api.BaseTest;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.models.BookingDatesDto;
import com.medirect.api.models.BookingDto;
import com.medirect.api.models.CreateBookingDto;
import com.medirect.api.utils.RestClient;
import com.medirect.api.utils.Utils;
import com.medirect.api.validator.AnnotationValidator;
import com.medirect.api.validator.ValidateResponse;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("Delete Booking")
@Feature("Positive Tests")
@Listeners({ValidateResponseListener.class})
public class PositiveTests extends BaseTest {
    private static final BookingDto booking = generateBooking();
    private static int bookingId;

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

    @BeforeClass
    public void PreCondition() {
        RestClient.authenticate();
        Response response = RestClient.post("/booking", booking);
        CreateBookingDto createBookingDto = response.as(CreateBookingDto.class);
        bookingId = createBookingDto.getBookingid();
    }

    @Test()
    @Story("Delete Booking")
    @Description("Test to validate successful deletion of a booking")
    @Severity(SeverityLevel.CRITICAL)
    @ValidateResponse(statusCode = 201, contentType = ContentType.TEXT)
    public void testDeleteBooking(ITestContext context) {
        Response response = RestClient.delete("/booking/" + bookingId);
        context.setAttribute("apiResponse", response);
        AnnotationValidator.assertAll();
    }
}