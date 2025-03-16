package com.medirect.api.dataproviders;

import com.medirect.api.core.TestDataGenerator;
import org.testng.annotations.DataProvider;

public class BookingDataProvider {

    @DataProvider(name = "bookingDto")
    public static Object[][] createBookingData() {
        return new Object[][]{
                {TestDataGenerator.generateBooking()}
        };
    }
}
