package com.team.villevich.banktest.RegressTC;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//
// SPRAWDZENIA FLOW VIDEO VERYFIKACJI, LOGOWANIA I BLOKOWANIA APPKI
// KLIENT MA BYĆ Z VV I DEMO APP
// APPKA MA BYĆ ZALOGOWANA
//

@RunWith(AndroidJUnit4.class)

public class FirstFlow {
    private UiDevice mDevice;
    private String WELCOME_MSG = "Welcome to Golden Sand Bank.\n" +
            "To start activation process\n" +
            "enter your login.",
            LOGIN = "testvideo",
            DASHBOARD_BAR_TITLE = "GOLDEN SAND BANK",
    //Title of start screen
    LOCKEDAPP_MES = "Your Golden Sand Bank\n" +
            "application is locked",
            GET_STARTED_MES ="Get started now",
            ENTER_PASSCODE_MES = "Enter your passcode";

    //TEXT TO COMPARE WITH UI ELEMENTS
    private String CANCEL_BUTTON = "CANCEL",
            VV_BAR_TITLE = "VIDEO VERIFICATION",
            VV_START_CALL_BUTTON = "START VIDEO CALL NOW",
            DO_LATER_BUTTON = "I'll do it later",
            VV_BODY = "Be aware you must have your passport or ID ready. Note, it has to be the same document you used in the e-application form.\n" +
                    "\n" +
                    " You should be in a bright and quiet place.\n" +
                    "\n" +
                    " Good internet connection is required. You must be the only person in the camera view.",
            OK_BUTTON = "OK",
            CONFIRM_ID_BODY = "To activate the account you must go through our verification process",
            CONFIRM_IT_TITLE = "Confirm identity",
            CHECK_CONDITION = "Checking the technical conditions",
            KLIENT_LOGIN = "user9595",
            LOGIN_ENTER_PASSCODE = "Enter your passcode",
            LOGIN_FAILED_ALERT = "Login failed",
            APP_LOCKED_ALERT = "App Locked";

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

        UiObject passcodeReq =mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_security_req"));
        passcodeReq.click();
        TimeUnit.SECONDS.sleep(1);
        mDevice.pressBack();
        TimeUnit.SECONDS.sleep(1);

        UiObject clickPasscode =mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        clickPasscode.click();

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
        UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        scrollable.scrollForward();

        UiObject finishLoginBtn =mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        finishLoginBtn.click();
        TimeUnit.SECONDS.sleep(1);

    }

    @Test
    public void test3() throws UiObjectNotFoundException, InterruptedException {
        UiObject okBtn =mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultPositive"));
        okBtn.click();
        TimeUnit.SECONDS.sleep(1);
        UiObject doLater =mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_cancel_verification"));
        doLater.click();
        TimeUnit.SECONDS.sleep(1);
        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
        try {
            sideMenu.click();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            sideMenu.click();
        }
        UiObject logout = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/iv_logout"));
        try {
            logout.click();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            logout.click();
        }
        UiObject positiveLogout = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultPositive"));
        try {
            positiveLogout.click();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            positiveLogout.click();
        }
        TimeUnit.SECONDS.sleep(3);

        UiObject checkLogin = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
        try {
            checkLogin.isEnabled();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(2);
            checkLogin.isEnabled();
        }

        //Block app
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000092);
        TimeUnit.SECONDS.sleep(2);
        UiObject failedPinOk = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultPositive"));
        try {
            failedPinOk.click();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            failedPinOk.click();
        }
        TimeUnit.SECONDS.sleep(1);

        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000092);
        TimeUnit.SECONDS.sleep(2);
        try {
            failedPinOk.click();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            failedPinOk.click();
        }
        TimeUnit.SECONDS.sleep(1);

        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000092);
        TimeUnit.SECONDS.sleep(2);
        try {
            failedPinOk.click();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            failedPinOk.click();
        }
        TimeUnit.SECONDS.sleep(1);



    }
}
