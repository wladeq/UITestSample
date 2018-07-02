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
        device.pressHome();
        try {
            UiObject appButton1 = device.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
        } catch (UiObjectNotFoundException e){
            device.pressHome();
            UiObject appButton1 = device.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
        }
        UiObject2 element = device.wait(Until.findObject(By.res("com.ailleron.longbank.gtest:id/tv_passcode_hint")),5000);

    }

}
