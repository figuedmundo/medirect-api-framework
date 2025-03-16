package com.medirect.api;

import com.medirect.api.core.ValidateResponse;
import com.medirect.api.core.ValidateResponseListener;
import com.medirect.api.dataproviders.BookingDataProvider;
import com.medirect.api.models.BookingDto;
import com.medirect.api.utils.RestClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("API Automation")
@Feature("Booking API Tests")
@Listeners(ValidateResponseListener.class)
public class BookingTest extends BaseTest {

    @BeforeClass
    public void setup() {
        RestClient.authenticate();
    }

    @Test(dataProvider = "bookingDto", dataProviderClass = BookingDataProvider.class)
    @ValidateResponse(statusCode = 200, maxResponseTime = 3000)
    public void testCreateBooking(BookingDto booking, ITestContext context) {
        Response response = RestClient.post("/booking", booking);
        context.setAttribute("apiResponse", response);

    }

    @Test
    @ValidateResponse(statusCode = 200, maxResponseTime = 3000, contentType = ContentType.JSON)
    public void getBookings(ITestContext context) {
        Response response = RestClient.get("/booking");

        // Store response in Test Context for automatic validation
        context.setAttribute("apiResponse", response);

//        Assert.assertEquals(response.getStatusCode(), 200);
    }
}