package com.medirect.api.tests.bookings.getbookings;

import com.medirect.api.BaseTest;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.models.*;
import com.medirect.api.utils.RestClient;
import com.medirect.api.utils.Utils;
import com.medirect.api.validator.AnnotationValidator;
import com.medirect.api.validator.ValidateResponse;
import groovy.transform.AutoImplement;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Get All Bookings")
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
    @Story("Get All Bookings")
    @Description("Test to validate successful retrieval of all bookings")
    @Severity(SeverityLevel.CRITICAL)
    @ValidateResponse(statusCode = 200, contentType = ContentType.JSON)
    public void testGetBookingByID(ITestContext context) {
        Response response = RestClient.get("/booking");
        context.setAttribute("apiResponse", response);
        List<BookingIdDto> bookingList = response.jsonPath().getList("", BookingIdDto.class);
        boolean bookingExists = bookingList.stream()
                .anyMatch(b -> b.getBookingid() == bookingId);

        AnnotationValidator.assertTrue(bookingExists, "Booking ID " + bookingId + " not found in the list!");
        AnnotationValidator.assertAll();
    }

    @Test()
    @Story("Get Bookings by firstname")
    @Description("Test to validate successful retrieval of bookings by firstname")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 200, contentType = ContentType.JSON)
    public void testGetBookingsByFirstname(ITestContext context) {
        Response response = RestClient.get("/booking?firstname=" + booking.getFirstname());
        context.setAttribute("apiResponse", response);
        List<BookingIdDto> bookingList = response.jsonPath().getList("", BookingIdDto.class);
        boolean bookingExists = bookingList.stream()
                .anyMatch(b -> b.getBookingid() == bookingId);

        AnnotationValidator.assertTrue(bookingExists, "Booking ID " + bookingId + " not found in the list!");
        AnnotationValidator.assertAll();
    }

    @Test()
    @Story("Get Bookings by checkin")
    @Description("Test to validate successful retrieval of bookings by firstname")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 200, contentType = ContentType.JSON)
    public void testGetBookingsByCheckin(ITestContext context) {
        Response response = RestClient.get("/booking?checkin=" + Utils.getDate("-1d"));
        context.setAttribute("apiResponse", response);
        List<BookingIdDto> bookingList = response.jsonPath().getList("", BookingIdDto.class);
        boolean bookingExists = bookingList.stream()
                .anyMatch(b -> b.getBookingid() == bookingId);

        AnnotationValidator.assertTrue(bookingExists, "Booking ID " + bookingId + " not found in the list!");
        AnnotationValidator.assertAll();
    }
}