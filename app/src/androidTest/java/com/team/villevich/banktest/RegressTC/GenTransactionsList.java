package com.team.villevich.banktest.RegressTC;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class GenTransactionsList {
    private UiDevice mDevice;
    List<String> personalAccIbans = new ArrayList<String>();
    List<String> savingsAccIbans = new ArrayList<String>();


    private String LOGIN_ENTER_PASSCODE = "Enter your passcode",
            DASHBOARD_BAR_TITLE = "GOLDEN SAND BANK",
            TRANSFER_BAR_TITLE = "TRANSFER";
    private boolean isExchangeExists=false;
    private int currentBalance, moneyAmount = 10;

    private String balanceFieldValue, fromAccNrFieldValue;
    @Before
    public void startMainActivityFromHomeScreen()  {
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void test1() throws UiObjectNotFoundException {
        mDevice.pressHome();

        try {
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
        } catch (UiObjectNotFoundException e){
            mDevice.pressHome();
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
        }
        UiObject2 element = mDevice.wait(Until.findObject(By.res("com.ailleron.longbank.gtest:id/tv_passcode_hint")),10000);
        element.click();
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000094);
        UiObject2 element1 = mDevice.wait(Until.findObject(By.text("PERSONAL ACCOUNTS")),5000);
        element1.isEnabled();

    }


    @Test
    public void test2() throws UiObjectNotFoundException {

        //Get personal acc Ibans
        UiObject2 personalAccounts = mDevice.findObject(By.text("PERSONAL ACCOUNTS"));
        personalAccounts.click();
        UiObject2 elementPersonal = mDevice.wait(Until.findObject(By.text("SHOW MORE")),3000);
        elementPersonal.isEnabled();
        mDevice.swipe(0,720,720,720,20);
        List<UiObject2> personalAccIbansList = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_subtitle"));
        for (int i = 0; i < personalAccIbansList.size(); i++) {
            personalAccIbans.add(personalAccIbansList.get(i).getText());
            Log.e("Number is ",personalAccIbans.get(i));
        }
        UiObject2 bottomDashboardBtn = mDevice.findObject(By.res("com.ailleron.longbank.gtest:id/tab_home"));
        bottomDashboardBtn.click();

        //Get seving Ibans
        UiObject2 element1 = mDevice.wait(Until.findObject(By.text("SAVINGS")),3000);
        element1.click();
        UiObject2 elementSavings = mDevice.wait(Until.findObject(By.text("SHOW MORE")),3000);
        elementSavings.isEnabled();
        mDevice.swipe(0,720,720,720,20);
        List<UiObject2> savignsAccIbansList = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_subtitle"));
        for (int i = 0; i < savignsAccIbansList.size(); i++) {
            savingsAccIbans.add(savignsAccIbansList.get(i).getText());
            Log.e("Number is ",savingsAccIbans.get(i));
        }
        UiObject2 bottomDashboardBtn1 = mDevice.findObject(By.res("com.ailleron.longbank.gtest:id/tab_home"));
        bottomDashboardBtn1.click();
    }

    @Test
    public void test3() throws UiObjectNotFoundException, InterruptedException {
        int[] first = {1,2,3,5};
        for(int j = 0; j<=2;j++){
            for(int i =0; i<=3; i++) {
                Random generator = new Random();
                int amountOfTransfer = generator.nextInt(1000)+1;

                UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
                UiObject transferSideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_transfer"));

                //WCHODZIMY DO TRANSFER PRZEZ BOCZNE MENU
                sideMenu.click();
                transferSideMenu.click();

                UiObject toAccountViewGroup = mDevice.findObject(new UiSelector().text("To"));
                toAccountViewGroup.click();

                UiObject toAccountPersonal1 = mDevice.findObject(new UiSelector().classNameMatches("android.view.ViewGroup").index(first[i]));
                toAccountPersonal1.click();


                UiObject confrimTransfer = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
                UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
                amount.setText(amountOfTransfer+"");
                TimeUnit.SECONDS.sleep(1);

                confrimTransfer.click();
                TimeUnit.SECONDS.sleep(2);
                UiObject summaryConfirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
                summaryConfirm.click();

                try{
                UiObject finishTransaction = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
                finishTransaction.click();
                } catch (UiObjectNotFoundException e){
                    TimeUnit.SECONDS.sleep(2);
                    UiObject finishTransaction = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
                    finishTransaction.click();
                }
            }
        }
    }
}
