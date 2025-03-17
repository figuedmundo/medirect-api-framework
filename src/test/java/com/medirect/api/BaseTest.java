package com.medirect.api;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class BaseTest {

    @BeforeClass
    public void setup(ITestContext context) {
        context.setAttribute("apiResponse", null);
    }
}