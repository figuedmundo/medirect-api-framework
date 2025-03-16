package com.medirect.api.core;

import com.medirect.api.models.BookingDto;
import com.medirect.api.models.DatesDto;
import com.medirect.api.utils.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    public static BookingDto generateBooking() {
        BookingDto booking = new BookingDto();
        booking.setFirstname(Utils.randomString(6));
        booking.setLastname(Utils.randomString(8));
        booking.setTotalprice(Utils.randomInt(100, 1000));
        booking.setDepositpaid(Utils.randomBoolean());

        DatesDto dates = new DatesDto();
        dates.setCheckin(Utils.getDate());
        dates.setCheckout(Utils.getDate("+1d"));
        booking.setDates(dates);

        booking.setAdditionalneeds(Utils.randomString(10));
        return booking;
    }
}