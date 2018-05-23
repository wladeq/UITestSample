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

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class Preparing {
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void cleanCache() throws UiObjectNotFoundException, InterruptedException {
        mDevice.pressHome();
        UiObject sideMenu = mDevice.findObject(new UiSelector().textMatches("Ustawienia"));
        sideMenu.clickAndWaitForNewWindow();
        UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        scroll.scrollForward();
        UiObject appClick = mDevice.findObject(new UiSelector().textMatches("Aplikacje"));
        appClick.clickAndWaitForNewWindow();
        TimeUnit.SECONDS.sleep(1);
        UiScrollable scroll2 = new UiScrollable(new UiSelector().className("android.view.ViewGroup"));
        scroll2.scrollForward();
        UiScrollable scroll3 = new UiScrollable(new UiSelector().className("android.view.ViewGroup"));
        scroll3.scrollForward();
        UiObject bankAppSet = mDevice.findObject(new UiSelector().textMatches("Golden Sand Bank"));
        bankAppSet.click();
        UiObject memory = mDevice.findObject(new UiSelector().resourceId("androidhwext:id/preference_emui_root"));
        memory.click();
        UiObject cleanDate = mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/button_2"));
        cleanDate.click();
        UiObject accept = mDevice.findObject(new UiSelector().resourceId("android:id/button1"));
        accept.click();
        mDevice.pressHome();

    }
}
