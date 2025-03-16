package com.medirect.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import com.medirect.api.utils.ConfigManager;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.config().getBaseUrl();
    }
}