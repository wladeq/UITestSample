package com.team.villevich.banktest;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.CLIPBOARD_SERVICE;

@RunWith(AndroidJUnit4.class)
public class TestClass {
    private UiDevice mDevice;
    String clipboardText;

    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void test() throws UiObjectNotFoundException {
        UiObject testInput = mDevice.findObject(new UiSelector().textMatches("com.Slack:id/message_input_field"));

        new Thread(){
            public void run(){
                Context context = InstrumentationRegistry.getContext();
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                clipboardText = clipboard.getText().toString();

            }
        };

    }
}
