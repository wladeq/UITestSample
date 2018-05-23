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
public class SampleTest {


    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void login() throws UiObjectNotFoundException, InterruptedException {
        mDevice.pressHome();

        //Run bank application
        UiObject allAppsButton = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));

            allAppsButton.click();
            TimeUnit.SECONDS.sleep(7);

        //mDevice.pressHome();
        //Press 1
        mDevice.click(100,800);
        mDevice.click(100,800);
        //Press 2
        mDevice.click(300,800);
        mDevice.click(300,800);
        //Press 3
        mDevice.click(600,800);
        mDevice.click(600,800);
        //Press 4
        mDevice.click(100,900);
        mDevice.click(100,900);

    }

    @Test
    public void ownTransfer() throws UiObjectNotFoundException, InterruptedException {
        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));

            sideMenu.clickAndWaitForNewWindow();


        UiObject payment = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_transfer"));
        payment.clickAndWaitForNewWindow();

        UiObject to = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/ell_to_account"));
        to.click();

        UiScrollable test = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        test.scrollForward();

        UiObject name = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(4));
        name.click();


        UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        amount.setText("50");
        TimeUnit.SECONDS.sleep(1);

        UiObject confirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm.click();

        UiObject confirm1 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm1.click();

        UiObject okBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
        okBtn.click();

    }
    @Test
    public void Payment() throws UiObjectNotFoundException, InterruptedException {
        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
        sideMenu.clickAndWaitForNewWindow();

        UiObject payment = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_payment"));
        payment.clickAndWaitForNewWindow();

        UiObject nameEdit = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_name"));
        nameEdit.setText(generateString());

        UiObject adress1 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_address_first_line"));
        adress1.setText("Some adress");


        UiObject adress2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_address_second_line"));
        adress2.setText("Some second adress");

        UiScrollable test = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        test.scrollForward();

        UiObject coutry = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_dropdown_item_text"));
        coutry.click();

        TimeUnit.SECONDS.sleep(1);

        mDevice.click(300,800);

        UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        amount.setText("25");


        UiObject ibanNumber = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_account_number"));
        ibanNumber.setText("GI21NWBK000001690890930");

        UiObject title = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_payment_title"));
        title.setText(generateString());

        UiObject confirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm.click();

        UiObject confirm2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm2.click();

        TimeUnit.SECONDS.sleep(1);

        //Press 1
        mDevice.click(100,800);
        mDevice.click(100,800);
        //Press 2
        mDevice.click(300,800);
        mDevice.click(300,800);
        //Press 3
        mDevice.click(600,800);
        mDevice.click(600,800);
        //Press 4
        mDevice.click(100,900);
        mDevice.click(100,900);

        //temporary
        UiObject closeError = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_close"));
        closeError.click();

    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }

}

