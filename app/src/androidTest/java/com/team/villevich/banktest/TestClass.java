package com.team.villevich.banktest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestClass{
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen()  {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void test() throws UiObjectNotFoundException, InterruptedException {
        UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton1.clickAndWaitForNewWindow();
    }
}
