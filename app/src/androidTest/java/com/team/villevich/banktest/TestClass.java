package com.team.villevich.banktest;

import android.os.RemoteException;
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
    private String KLIENT_NAME = "ADAM";
    private String KLIENT_SURNAME = "ADAMMM";
    private String KLIENT_EMAIL = "so34g@ggfh.asf";
    private String KLIENT_LOGIN = "us23h360ghv";


    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void test() throws UiObjectNotFoundException {
        UiObject demoAppBar = mDevice.findObject(new UiSelector().text("DEMO APP. VERIFY YOUR ACCOUNT TO GET FULL ACCESS.").index(1));
        demoAppBar.isFocusable();
    }


    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("[0-9]","").replaceAll("-","");
    }
}
