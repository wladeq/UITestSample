package com.team.villevich.banktest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class TestClass{
    private UiDevice mDevice;
    String GBP_PERSONAL_NUMBER = "61NW BK00 0001 0000 5912 0";

    @Before
    public void startMainActivityFromHomeScreen()  {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void test() throws UiObjectNotFoundException, InterruptedException {

        UiScrollable test = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        while(test.scrollForward()) {
            UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(15));
        }
        /*test.scrollIntoView(onTransactionClick);
        onTransactionClick.click();*/

    }

}
