package com.medirect.api;

import com.medirect.api.models.BookingDto;
import com.medirect.api.models.DatesDto;
import com.medirect.api.utils.RestClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingTest extends BaseTest {

    @Test
    public void testCreateBooking() {
        BookingDto booking = new BookingDto();
        booking.firstname = "John";
        booking.lastname = "Doe";
        booking.totalprice = 150;
        booking.depositpaid = true;

        DatesDto dates = new DatesDto();
        dates.checkin = "2024-06-01";
        dates.checkout = "2024-06-10";
        booking.datesDto = dates;

        booking.additionalneeds = "Breakfast";

        Response response = RestClient.post("/booking", booking);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("booking.firstname"), "John");
    }
}