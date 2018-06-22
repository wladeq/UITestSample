package com.team.villevich.banktest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class TestClass{
    private UiDevice mDevice;
    String GBP_PERSONAL_NUMBER = "61NW BK00 0001 0000 5912 0";

    @Before
    public void startMainActivityFromHomeScreen()  {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void test() throws ParseException {
   /*
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        String formated = format.format(cal.getTime());
        Log.i("current date 1",formated);
        cal.roll(Calendar.YEAR, -1);
        Log.i("current date 2", format.format(cal.getTime()).toUpperCase());
*/
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        String formated = format.format(cal1.getTime());

        Calendar cal = Calendar.getInstance();
        String dateSample = "22 JUN 2018";
        cal.setTime(format.parse(dateSample));

        Log.i("date1", cal.getTime().toString());
        Log.i("date2", cal1.getTime().toString());

        Date date1 = format.parse(dateSample);
        Date date2 = format.parse(formated);



        if (date1.equals(date2)){
            Log.i("QQQ", "EQUALS");
        } else if (date1.before(date2)) {
            Log.i("QQQ", "BEFORE");
        } else if (date1.after(date2)){
            Log.i("QQQ", "AFTER");
        }
    }

}
