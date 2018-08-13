package com.team.villevich.banktest;

import android.content.Context;
import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class TestClass {
    private UiDevice device;
    private static Context context;

    @Before
    public void startMainActivityFromHomeScreen()  {
        device = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void test() throws UiObjectNotFoundException {

        UiScrollable test = new UiScrollable(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/rv_transactions_history"));
        test.scrollForward();

    }
}