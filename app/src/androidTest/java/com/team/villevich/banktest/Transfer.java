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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class Transfer {

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }
    //@Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    @Test
    public void firstAction() throws InterruptedException {
        //mDevice.pressHome();
        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
        try {
            sideMenu.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiObject payment = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_transfer"));
        try {
            payment.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiObject to = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/ell_to_account"));
        try {
            to.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiScrollable test = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        try {
            test.scrollForward();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject name = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(4));
        try {
            name.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        try {
            amount.setText("50");

            TimeUnit.SECONDS.sleep(1);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiObject confirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        try {
            confirm.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiObject confirm1 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        try {
            confirm1.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiObject okBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
        try {
            okBtn.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

}

