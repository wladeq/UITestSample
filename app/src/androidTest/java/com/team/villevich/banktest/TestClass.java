package com.team.villevich.banktest;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class TestClass {
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen()  {
        device = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void test() throws Exception {
        List<String> personalAccIbans = new LinkedList<>();
        List<UiObject2> ibanFieldsContainer = device.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_subtitle"));
        List<UiObject2> valueFieldsContainer = device.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_available_funds"));


        for(int i = 0; i<valueFieldsContainer.size();i++) {
            if (valueFieldsContainer.get(i).getText().substring(0, 3).equals("GBP")) {
                personalAccIbans.add(0,ibanFieldsContainer.get(i).getText());

            } else if (valueFieldsContainer.get(i).getText().substring(0, 3).equals("USD")) {
                personalAccIbans.add(1,ibanFieldsContainer.get(i).getText());

            } else if (valueFieldsContainer.get(i).getText().substring(0, 3).equals("EUR")){
                personalAccIbans.add(2,ibanFieldsContainer.get(i).getText());

            } else {
                throw new Exception("Shoto poszlo po pizde");
            }
        }
        for(int i = 0; i<=2;i++){
            Log.wtf(personalAccIbans.get(i),"**QWE");
        }
    }
}