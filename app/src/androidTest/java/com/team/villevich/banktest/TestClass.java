package com.team.villevich.banktest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestClass{
    private UiDevice mDevice;
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen()  {
        //mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void test() throws UiObjectNotFoundException {
        UiObject2 ibanField = mDevice.wait(Until.findObject(By.res("com.ailleron.longbank.gtest:id/tv_account_number_from")
                .text("fd")),1000);
    }

    //            test1();
    //            mDevice.pressRecentApps();
    //            UiObject appButton3 = mDevice.findObject(new UiSelector().resourceId("com.android.systemui:id/recents_close_all_button"));
    //            appButton3.click();
}
