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

        UiScrollable scroll12 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll12.scrollForward();
        scroll12.scrollForward();
        UiObject checkBox = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_STATEMENT1__Q__Text"));
        checkBox.click();
        UiObject checkBox2 = mDevice.findObject(new UiSelector().index(0).text("No"));
        checkBox2.click();
        UiScrollable scroll13 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll13.scrollForward();
        UiObject checkBox3 = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_STATEMENT_CRS__Q__Text"));
        checkBox3.click();
        UiScrollable scroll14 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll14.scrollForward();

        UiObject checkBox4 = mDevice.findObject(new UiSelector().text("I accept all of the following statements"));
        checkBox4.click();

        UiScrollable scroll15 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll15.scrollForward();
        UiObject checkBox5 = mDevice.findObject(new UiSelector().textStartsWith("In the event that the Bank intends"));
        checkBox5.click();
        UiScrollable scroll16 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll16.scrollForward();
        UiObject checkBox6 = mDevice.findObject(new UiSelector().textContains("No").className("android.view.View"));
        checkBox6.click();
        UiObject checkBox7 = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_STATEMENT_PER__Q__Text"));
        checkBox7.click();
        mDevice.click(420,550);
        UiObject finish = mDevice.findObject(new UiSelector().text("SEND"));
        finish.click();
        TimeUnit.SECONDS.sleep(5);
        mDevice.click(52,103);
        TimeUnit.SECONDS.sleep(1);

    }


    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("[0-9]","").replaceAll("-","");
    }
}
