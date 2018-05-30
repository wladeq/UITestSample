package com.team.villevich.banktest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
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

/*        UiObject ibanField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_from")
                .text(GBP_PERSONAL_NUMBER));

        int transactionsIndex=1;

        UiObject filteredTransaction = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
        filteredTransaction.clickAndWaitForNewWindow(1);
        Log.i("SAMPLELOG","I'm in transaction details");
        ibanField.waitForExists(1);
        Log.i("SAMPLELOG","I'm in transaction details and i checked existing of requested field");*/
        UiObject ibanField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_from")
                .text(GBP_PERSONAL_NUMBER));
        ibanField.isEnabled();
        MyThread thread = new MyThread();
        thread.run();
 //       mDevice.pressKeyCode(0x00000004);
/*
       Log.i("SAMPLELOG","I'm back");

        ++transactionsIndex;
        Log.i("SAMPLELOG","pre-increment");*/
    }

    public class MyThread{
        public void run(){
            mDevice.pressKeyCode(0x00000004);
        }
    }
}
