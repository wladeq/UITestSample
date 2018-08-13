package com.team.villevich.banktest;

import android.Manifest;
import android.app.LauncherActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    public static final String OTP_REGEX = "[0-9]{1,6}";
    String number = "Golden Sand";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!=null)super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_SMS},
                1);
//
//        SmsReceiver.bindListener(new SmsListener() {
//            @Override
//            public void messageReceived(String messageText) {
//
//                //From the received text string you may do string operations to get the required OTP
//                //It depends on your SMS format
//                Log.e("Message",messageText);
//                Toast.makeText(MainActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();
//
//                // If your OTP is six digits number, you may use the below code
//
//                Pattern pattern = Pattern.compile(OTP_REGEX);
//                Matcher matcher = pattern.matcher(messageText);
//                String otp="";
//                while (matcher.find())
//                {
//                    otp = matcher.group();
//                }
//                Toast.makeText(MainActivity.this,"OTP: "+ otp ,Toast.LENGTH_LONG).show();
//            }
//        }};
        /*
*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Uri uriSms = Uri.parse("content://sms/inbox");
                    final Cursor cursor = getContentResolver().query(uriSms,
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
                            Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                    // other 'case' lines to check for other
                    // permissions this app might request
                }
            }
        }}
            private void readSms () {

                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                assert cursor != null;
                if (cursor.moveToFirst()) {
                    do {
                        String msgData = "";
                        for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                            msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                        }
                        //use msgData
                        Log.wtf("MSG_CONTENT", msgData);
                    } while (cursor.moveToNext());
                } else {

                }
            }
}