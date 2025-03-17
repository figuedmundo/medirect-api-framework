package com.medirect.api.tests.bookings.updatebooking;

import com.medirect.api.BaseTest;
import com.medirect.api.listeners.ValidateResponseListener;
import com.medirect.api.models.BookingDatesDto;
import com.medirect.api.models.BookingDto;
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

import java.util.Map;

@Epic("Update Booking")
@Feature("Negative Tests")
@Listeners({ValidateResponseListener.class})
public class NegativeTests extends BaseTest {
    private static final BookingDto updatedBooking = generateBooking();

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
    @Story("Update Non existing Booking")
    @Description("Test to validate failure of update of a non existing booking")
    @Severity(SeverityLevel.CRITICAL)
    @ValidateResponse(statusCode = 404, contentType = ContentType.TEXT)
    public void testUpdateNonExistingBooking(ITestContext context) {
        Response response = RestClient.put("/booking/" + 999999, updatedBooking, Map.of("Cookie", "token=" + RestClient.getToken()));
        context.setAttribute("apiResponse", response);
        AnnotationValidator.assertAll();
    }
}