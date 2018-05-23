package com.team.villevich.banktest;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class Login {
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        //mDevice.pressHome();

    }

   @Test
    public void login() throws InterruptedException, UiObjectNotFoundException {
        UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton1.click();
        TimeUnit.SECONDS.sleep(6);
        UiObject loginBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_login"));
        loginBtn.click();

        UiObject loginInput = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        loginInput.setText("kkinglog1");
        UiObject next = mDevice.findObject(new UiSelector().textMatches("NEXT STEP"));
        next.click();
        mDevice.pressHome();
        UiObject messages = mDevice.findObject(new UiSelector().textMatches("Wiadomości"));
        messages.clickAndWaitForNewWindow(100);
        UiObject messages1 = mDevice.findObject(new UiSelector().textContains("Golden Sand"));
        messages1.clickAndWaitForNewWindow(100);
        TimeUnit.SECONDS.sleep(2);

        mDevice.click(210,1048);
        UiObject copy = mDevice.findObject(new UiSelector().textMatches("Kopiuj do schowka"));
        copy.click();
        mDevice.pressHome();
        UiObject appButton = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton.click();
        TimeUnit.SECONDS.sleep(1);
        UiObject smsCodeView = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        smsCodeView.click();
        smsCodeView.longClick();
        TimeUnit.SECONDS.sleep(1);
        mDevice.click(172,332);
        UiObject next2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        next2.click();

        TimeUnit.SECONDS.sleep(1);
        //Press 1
        mDevice.click(220,830);
        mDevice.click(220,830);
        //Press 2
        mDevice.click(360,830);
        mDevice.click(360,830);
        //Press 3
        mDevice.click(505,835);
        mDevice.click(505,835);
        //Press 4
        mDevice.click(214,937);
        mDevice.click(214,937);

       TimeUnit.SECONDS.sleep(1);

       //Press 1
       mDevice.click(220,830);
       mDevice.click(220,830);
       //Press 2
       mDevice.click(360,830);
       mDevice.click(360,830);
       //Press 3
       mDevice.click(505,835);
       mDevice.click(505,835);
       //Press 4
       mDevice.click(214,937);
       mDevice.click(214,937);

        mDevice.pressBack();

        UiObject login = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        login.click();
    }
//@Test
public void test() throws UiObjectNotFoundException, RemoteException {

}
}
