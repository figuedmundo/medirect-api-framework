package com.medirect.api.tests.bookings.updatebooking;

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

@Epic("Update Booking")
@Feature("Security Tests")
@Listeners({ValidateResponseListener.class})
public class SecurityTests extends BaseTest {
    private static final BookingDto booking = generateBooking();
    private static final BookingDto updatedBooking = generateBooking();
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
    @Story("Update Booking only with Authorization token")
    @Description("Test to validate success update of a booking without Cookie")
    @Severity(SeverityLevel.NORMAL)
    @ValidateResponse(statusCode = 200, contentType = ContentType.JSON)
    public void testUpdateBookingOnlyWithAuthorizationToken(ITestContext context) {
        Response response = RestClient.put("/booking/" + bookingId, updatedBooking);
        context.setAttribute("apiResponse", response);
        BookingDto bookingDto = response.as(BookingDto.class);

        AnnotationValidator.assertEquals(bookingDto, updatedBooking, "Bookings does not match");
        AnnotationValidator.assertAll();
    }

    @Test()
    @Story("Update Booking only with Cookie")
    @Description("Test to validate success update of a booking without Authorization Token")
    @Severity(SeverityLevel.MINOR)
    @ValidateResponse(statusCode = 200, contentType = ContentType.JSON)
    public void testUpdateBookingOnlyWithAuthorizationCookie(ITestContext context) {
        RestClient.deleteToken();
        Response response = RestClient.put("/booking/" + bookingId, updatedBooking, Map.of("Cookie", "token=" + RestClient.getToken()));
        context.setAttribute("apiResponse", response);
        BookingDto bookingDto = response.as(BookingDto.class);

        AnnotationValidator.assertEquals(bookingDto, updatedBooking, "Bookings does not match");
        AnnotationValidator.assertAll();
    }

    @Test()
    @Story("Update Booking without Authorization")
    @Description("Test to validate failure of update of a booking without Authorization Token")
    @Severity(SeverityLevel.CRITICAL)
    @ValidateResponse(statusCode = 403, contentType = ContentType.TEXT)
    public void testUpdateBookingOnlyWithoutAuthorization(ITestContext context) {
        RestClient.deleteToken();
        Response response = RestClient.put("/booking/" + bookingId, updatedBooking);
        context.setAttribute("apiResponse", response);
        AnnotationValidator.assertAll();
    }
}