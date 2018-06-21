package com.team.villevich.banktest.RegressTC;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//Login Flow with sms

@RunWith(AndroidJUnit4.class)
public class LoginFlow {
    private UiDevice mDevice;
    private String WELCOME_MSG = "Welcome to Golden Sand Bank.\n" +
            "To start activation process\n" +
            "enter your login.",
            LOGIN = "autorobo",
            DASHBOARD_BAR_TITLE = "GOLDEN SAND BANK",
            //Title of start screen
            LOCKEDAPP_MES = "Your Golden Sand Bank\n" +
                            "application is locked",
            GET_STARTED_MES ="Get started now",
            ENTER_PASSCODE_MES = "Enter your passcode";

    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void test1() throws UiObjectNotFoundException, InterruptedException {
        mDevice.pressHome();
        try {
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e){
            mDevice.pressHome();
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.clickAndWaitForNewWindow();
        }
        TimeUnit.SECONDS.sleep(8);

        // There are different types of first screen we will detect current,
        // and use suitable type of interaction
        UiObject startMessageOption1 = mDevice.findObject(new UiSelector().text(GET_STARTED_MES));
        UiObject startMessageOption2 = mDevice.findObject(new UiSelector().text(LOCKEDAPP_MES));
        if (startMessageOption1.exists()){
            UiObject loginBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_login"));
            loginBtn.click();

        }else if (startMessageOption2.exists()){
            UiObject unlockBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_unlock"));
            unlockBtn.click();
        } else {
            mDevice.pressKeyCode(0x00000091);
            mDevice.pressKeyCode(0x00000091);
            mDevice.pressKeyCode(0x00000092);
            mDevice.pressKeyCode(0x00000092);
            mDevice.pressKeyCode(0x00000093);
            mDevice.pressKeyCode(0x00000093);
            mDevice.pressKeyCode(0x00000094);
            mDevice.pressKeyCode(0x00000094);
        }

    }

    //First login to app
    @Test
    public void test2() throws UiObjectNotFoundException, InterruptedException {
        String smsCode;


        UiObject welcomeMessage = mDevice.findObject(new UiSelector().text(WELCOME_MSG));
        try {
            TimeUnit.SECONDS.sleep(1);
            welcomeMessage.isFocusable();

        } catch(UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);

            welcomeMessage.isFocusable();
        }

        UiObject loginView = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        loginView.setText(LOGIN);
        UiObject nextStepBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        try {
            nextStepBtn.click();

        } catch(UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            nextStepBtn.click();
        }
        UiObject smsCodeView =mDevice.findObject(new UiSelector().resourceId("net.everythingandroid.smspopup:id/smsCode"));
        do{
            TimeUnit.SECONDS.sleep(1);
        }
            while(!smsCodeView.isEnabled());
        smsCode = smsCodeView.getText().substring(1);
        mDevice.pressBack();
        UiObject smsCodeET =mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        smsCodeET.setText(smsCode);
        nextStepBtn.click();
        for(int i=0; i<2;i++){
            TimeUnit.SECONDS.sleep(1);
            mDevice.pressKeyCode(0x00000091);
            mDevice.pressKeyCode(0x00000091);
            mDevice.pressKeyCode(0x00000092);
            mDevice.pressKeyCode(0x00000092);
            mDevice.pressKeyCode(0x00000093);
            mDevice.pressKeyCode(0x00000093);
            mDevice.pressKeyCode(0x00000094);
            mDevice.pressKeyCode(0x00000094);
        }
        UiObject finishLoginBtn =mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        finishLoginBtn.click();
        try{
            UiObject topBarDashboard = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            UiObject topBarDashboard = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        }
    }
}