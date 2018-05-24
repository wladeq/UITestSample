package com.team.villevich.banktest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class TestClass {
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void test() throws UiObjectNotFoundException, InterruptedException {
        UiObject sourceOfWealth = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_SOW_IS_SAVINGS__Q__Text"));
        sourceOfWealth.click();

    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("[0-9]","").replaceAll("-","");
    }
}
