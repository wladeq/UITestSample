package com.team.villevich.banktest.RegressTC;

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
            APP_LOCKED_ALERT = "App Locked",
            LOCKED_APP_TEXT = "Your Golden Sand Bank\n" +
                    "application is locked";

    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    //LOGIN ON EXISTING USER WITH ACTIVE VV
    //@Test
    public void loginMethod() throws UiObjectNotFoundException, InterruptedException {
        mDevice.pressHome();
        UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton1.click();
        TimeUnit.SECONDS.sleep(6);
        try {
            UiObject loginBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_login"));
            loginBtn.click();
        } catch(UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(6);
            UiObject loginBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_login"));
            loginBtn.click();
        }
        UiObject loginInput = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        loginInput.setText(KLIENT_LOGIN);
        UiObject next = mDevice.findObject(new UiSelector().textMatches("NEXT STEP"));
        next.click();

        mDevice.pressHome();
        UiObject messages = mDevice.findObject(new UiSelector().textMatches("Wiadomości"));
        messages.click();
        TimeUnit.SECONDS.sleep(2);
        UiObject messages1 = mDevice.findObject(new UiSelector().textContains("Golden Sand").className("android.widget.TextView"));
        messages1.click();
        TimeUnit.SECONDS.sleep(3);
        mDevice.click(529,964);

        String copyText = null;
        try {
            UiObject codeField = mDevice.findObject(new UiSelector().resourceId("android:id/alertTitle"));
            copyText = codeField.getText();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            mDevice.click(529,964);
            UiObject codeField = mDevice.findObject(new UiSelector().resourceId("android:id/alertTitle"));
            copyText = codeField.getText();
        }

        mDevice.pressHome();
        UiObject appButton = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton.click();
        TimeUnit.SECONDS.sleep(1);

        UiObject smsCodeView = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        smsCodeView.setText(copyText);
        TimeUnit.SECONDS.sleep(1);

        UiObject next2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        next2.click();

        TimeUnit.SECONDS.sleep(1);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000094);

        TimeUnit.SECONDS.sleep(1);

        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000094);

        mDevice.pressBack();

        UiObject login = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        login.click();
        TimeUnit.SECONDS.sleep(3);

    }

    //LON-83
    @Test
    public void test1() throws UiObjectNotFoundException, InterruptedException {

        mDevice.pressHome();
        try {
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
            } catch (UiObjectNotFoundException e){
            mDevice.pressHome();
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
        }
        TimeUnit.SECONDS.sleep(6);
        try {
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));

        } catch(UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(6);
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));
        }
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

    //Test VV LON-83, Test poprawnego logowania
    //@Test
    public void test2() throws UiObjectNotFoundException, InterruptedException {

        //Text check

            UiObject titleId = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_title"));
            UiObject titleBody = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_content"));
            UiObject okBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultPositive"));
            UiObject cancelBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultNegative"));
            try {

            assertThat(CONFIRM_IT_TITLE, is(equalTo(titleId.getText())));
            assertThat(CONFIRM_ID_BODY, is(equalTo(titleBody.getText())));
            assertThat(OK_BUTTON, is(equalTo(okBtn.getText())));
            assertThat(CANCEL_BUTTON, is(equalTo(cancelBtn.getText())));
            } catch (UiObjectNotFoundException e){
                TimeUnit.SECONDS.sleep(1);
                assertThat(CONFIRM_IT_TITLE, is(equalTo(titleId.getText())));
                assertThat(CONFIRM_ID_BODY, is(equalTo(titleBody.getText())));
                assertThat(OK_BUTTON, is(equalTo(okBtn.getText())));
                assertThat(CANCEL_BUTTON, is(equalTo(cancelBtn.getText())));
            }

        //FIRST PART OF TC - •	START VIDEO CHAT NOW - rozpoczyna proces weryfikacji użytkownika
        //BEFORE START VV ACTIVITY CHECK
        okBtn.click();
        UiObject vvBarTitle = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
        UiObject vvStartBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_video_call"));
        UiObject vvLaterBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_cancel_verification"));
        UiObject vvBody = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_video_verification_hint_1"));
        assertThat(VV_BAR_TITLE, is(equalTo(vvBarTitle.getText())));
        assertThat(VV_START_CALL_BUTTON, is(equalTo(vvStartBtn.getText())));
        assertThat(DO_LATER_BUTTON, is(equalTo(vvLaterBtn.getText())));
        assertThat(VV_BODY, is(equalTo(vvBody.getText())));

        //AFTER START VV CHECK
        vvStartBtn.click();
        TimeUnit.SECONDS.sleep(4);
        UiObject checkConditions = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/verification_header_text"));
        assertThat(CHECK_CONDITION, is(equalTo(checkConditions.getText())));
        TimeUnit.SECONDS.sleep(5);

        try{
            UiObject disconntectBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/button_disconnect_text"));
            disconntectBtn.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(3);
            UiObject disconntectBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/button_disconnect_text"));
            disconntectBtn.click();
        }
        try{
            UiObject confirmBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/button_disconnect_confirm"));
            confirmBtn.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            UiObject confirmBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/button_disconnect_confirm"));
            confirmBtn.click();
        }

        //TEMPORARY PART !! REWRITE AFTER REPAIR !!
        UiObject backToLogin = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_login"));
        backToLogin.click();

        //SECOND TC STEP
        // I'll do it later - przenosi do ekranu "8.0 Dashboard/Home", "8.3 Dashboard DEMO"
        //SECOND LOGIN
        try {
            TimeUnit.SECONDS.sleep(3);
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));

        } catch(UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(4);
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));
        }
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000094);
        //AFTER LOGIN, DASHBOARD CHECK
        try {

            assertThat(CONFIRM_IT_TITLE, is(equalTo(titleId.getText())));
            assertThat(CONFIRM_ID_BODY, is(equalTo(titleBody.getText())));
            assertThat(OK_BUTTON, is(equalTo(okBtn.getText())));
            assertThat(CANCEL_BUTTON, is(equalTo(cancelBtn.getText())));
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            assertThat(CONFIRM_IT_TITLE, is(equalTo(titleId.getText())));
            assertThat(CONFIRM_ID_BODY, is(equalTo(titleBody.getText())));
            assertThat(OK_BUTTON, is(equalTo(okBtn.getText())));
            assertThat(CANCEL_BUTTON, is(equalTo(cancelBtn.getText())));
        }
        //PRESS OK
        okBtn.click();
        //BEFORE START VV ACTIVITY CHECK
        assertThat(VV_BAR_TITLE, is(equalTo(vvBarTitle.getText())));
        assertThat(VV_START_CALL_BUTTON, is(equalTo(vvStartBtn.getText())));
        assertThat(DO_LATER_BUTTON, is(equalTo(vvLaterBtn.getText())));
        assertThat(VV_BODY, is(equalTo(vvBody.getText())));
        vvLaterBtn.click();
        try {
            UiObject demoAppBar = mDevice.findObject(new UiSelector().text("DEMO APP. VERIFY YOUR ACCOUNT TO GET FULL ACCESS.").index(1));
            demoAppBar.isFocusable();
        }catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            UiObject demoAppBar = mDevice.findObject(new UiSelector().text("DEMO APP. VERIFY YOUR ACCOUNT TO GET FULL ACCESS.").index(1));
            demoAppBar.isFocusable();
        }
        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
        sideMenu.click();
        UiObject logOut = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/iv_logout"));
        logOut.click();
        UiObject logOutOK = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultPositive"));
        logOutOK.click();
        TimeUnit.SECONDS.sleep(3);
    }

    //LON-19 Test blokowania aplikacji
    //@Test
    public void test3() throws UiObjectNotFoundException, InterruptedException {

        //Pętła niepoprawnych passcodów do blokowania appki
        for(int i = 0; i <2;i++) {
            try {
                UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
                assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));

            } catch (UiObjectNotFoundException e) {
                TimeUnit.SECONDS.sleep(4);
                UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
                assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));
            }
            mDevice.pressKeyCode(0x00000091);
            mDevice.pressKeyCode(0x00000091);
            mDevice.pressKeyCode(0x00000092);
            mDevice.pressKeyCode(0x00000092);
            mDevice.pressKeyCode(0x00000093);
            mDevice.pressKeyCode(0x00000093);
            mDevice.pressKeyCode(0x00000094);
            mDevice.pressKeyCode(0x00000093);

            try {
                TimeUnit.SECONDS.sleep(2);
                UiObject loginFailedTv = mDevice.findObject(new UiSelector().text("Login failed"));
                assertThat(LOGIN_FAILED_ALERT, is(equalTo(loginFailedTv.getText())));

            } catch (UiObjectNotFoundException e) {
                TimeUnit.SECONDS.sleep(2);
                UiObject loginFailedTv = mDevice.findObject(new UiSelector().text("Login failed"));
                assertThat(LOGIN_FAILED_ALERT, is(equalTo(loginFailedTv.getText())));
            }
            UiObject okBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultPositive"));
            okBtn.click();
        }

        try {
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));

        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(4);
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));
        }

        //Tezecia próba z innym pop-upem
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000093);
        try {
            UiObject appLockedTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_title"));
            assertThat(APP_LOCKED_ALERT, is(equalTo(appLockedTV.getText())));

        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(2);
            UiObject appLockedTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_title"));
            assertThat(APP_LOCKED_ALERT, is(equalTo(appLockedTV.getText())));
        }
        UiObject okBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/md_buttonDefaultPositive"));

        //Sprawdzenie, czy został wyświetłony ekran zablokowanej appki
        okBtn.click();
        try {
            TimeUnit.SECONDS.sleep(5);
            UiObject appAfterLockTV = mDevice.findObject(new UiSelector().text(LOCKED_APP_TEXT));
            appAfterLockTV.isFocusable();

        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(2);
            UiObject appAfterLockTV = mDevice.findObject(new UiSelector().text(LOCKED_APP_TEXT));
            appAfterLockTV.isFocusable();
        }
    }
}
