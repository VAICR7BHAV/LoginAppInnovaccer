package com.example.loginappinnovaccer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class Checkout_Acticity extends AppCompatActivity
{
    String visitor_name,visitor_email,visitor_phone,host_phone,
            host_name,host_email,checkin,checkout;
    public void CheckOut(View v)
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final Date currentTime = Calendar.getInstance().getTime();
                    checkout=currentTime.toString();
                    String body="Name - " +visitor_name+
                            "\nPhone - " +visitor_phone+
                            "\nCheckin Time - "+checkin+
                            "\nCheckout Time- "+checkout+
                            "\nHost Name- "+host_name+
                            "\nHost email- "+host_email;
                    Log.d("abcde","In the thread");
                    GMailSender sender = new GMailSender("innovtestvg@gmail.com",
                            "innov_12345");
                    sender.sendMail("VISIT DETAILS", body,
                            "innovtestvg@gmail.com", visitor_email);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
        Intent i2=new Intent(Checkout_Acticity.this,MainActivity.class);
        i2.putExtra("Host_Email",host_email);
        i2.putExtra("Host_Name",host_name);
        i2.putExtra("Host_Phone",host_phone);
        startActivity(i2);
        //SmsManager smsManager = SmsManager.getDefault();
        //smsManager.sendTextMessage(visitor_phone, null, body, null, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__acticity);
        Intent i=getIntent();

        visitor_name=i.getStringExtra("Visitor_Name");
        visitor_email=i.getStringExtra("Visitor_Email");
        visitor_phone=i.getStringExtra("Visitor_Phone");
        host_email=i.getStringExtra("Host_Email");
        host_name=i.getStringExtra("Host_Name");
        host_phone=i.getStringExtra("Host_Phone");
        checkin=i.getStringExtra("CheckIn");
        Log.d("abcde",visitor_name+visitor_email+visitor_phone+
                host_email+host_name+checkin);
    }
}
