//package com.medirect.api.listeners;
//
//import com.medirect.api.validator.AnnotationValidator;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//public class CustomTestListener implements ITestListener {
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        AnnotationValidator.assertAll(); // Call assertAll() on test success
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        AnnotationValidator.assertAll(); // Call assertAll() on test failure
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        AnnotationValidator.assertAll(); // Call assertAll() on test skipped
//    }
//}