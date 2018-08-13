package com.team.villevich.banktest;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SmsReader extends AppCompatActivity{
    public void readSms(){
Context context1 = getApplicationContext();

        String number = "Golden Sand";
        Uri uriSms = Uri.parse("content://sms/inbox");
        final Cursor cursor = context1.getContentResolver().query(uriSms,
                new String[]{"_id", "address", "date", "body"}, null, null, null);

        while (cursor.moveToNext()) {
            String address = cursor.getString(1);
            if (address.equals(number)) {
                String msg = cursor.getString(3);
                System.out.println(address);

                Log.wtf("URINA ", address);
                Log.wtf("URINA ", msg);
                Log.wtf("URINA ", cursor.getString(2));
                //readSms();
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }
    }
}
