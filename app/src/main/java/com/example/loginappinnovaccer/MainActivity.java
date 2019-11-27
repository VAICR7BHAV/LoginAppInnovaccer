package com.example.loginappinnovaccer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity
{
    String visitor_name,visitor_email,visitor_phone,designation,
            host_name,host_email,host_phone,checkin;
    boolean isHost;
    final int SMS_PERMISSION=1;
    TextView name_tv;
    TextView email_tv;
    TextView phone_tv;
    Button submit;
    DatabaseReference mDatabaseReference;
    Spinner desig;
    private void requestPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},SMS_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SMS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Permission Granted
                    //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT)
                            //.show();
                }
                else
                {
                    // Permission Denied
                    //Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                       //     .show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public void GetData(View view)
    {
        name_tv=(TextView)findViewById(R.id.NAME_TV);
        email_tv=(TextView)findViewById(R.id.EMAIL_TV);
        phone_tv=(TextView)findViewById(R.id.PHONE_Tv);

        desig=(Spinner)findViewById(R.id.designation_spinner);
        designation=desig.getSelectedItem().toString();
        submit=(Button)findViewById(R.id.submit);
        //Log.d("abcde",name+email+phone+designation);
        if(designation.equalsIgnoreCase("HOST"))
        {
            host_name=name_tv.getText().toString();
            host_email=email_tv.getText().toString();
            host_phone=phone_tv.getText().toString();
            Toast.makeText(getApplicationContext(),
                    host_name+" is the new host" ,
                    Toast.LENGTH_SHORT).show();
            isHost=true;
        }
        else if(isHost)
        {
            visitor_name=name_tv.getText().toString();
            visitor_email=email_tv.getText().toString();
            visitor_phone=phone_tv.getText().toString();

            Toast.makeText(getApplicationContext(),
                    visitor_name+" is the new visitor" ,
                    Toast.LENGTH_SHORT).show();
            final Date currentTime = Calendar.getInstance().getTime();
            checkin=currentTime.toString();
            Visitor_Data vd=new Visitor_Data(visitor_name,visitor_phone,visitor_email,checkin);
            mDatabaseReference.child("Visitor").push().setValue(vd);
            Log.d("abcde","Timestamp is"+currentTime);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        String body="Name - " +visitor_name+
                                "\nEmail -" +visitor_email+
                                "\nPhone - " +visitor_phone+
                                "\nCheckin Time - "+currentTime;
                        Log.d("abcde","In the thread");
                        GMailSender sender = new GMailSender("innovtestvg@gmail.com",
                                "innov_12345");
                        sender.sendMail("DETAILS OF VISITOR", body,
                                "innovtestvg@gmail.com", host_email);
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }

            }).start();
            String body="Name - " +visitor_name+
                    "\nEmail -" +visitor_email+
                    "\nPhone - " +visitor_phone+
                    "\nCheckin Time - "+currentTime;
            //Getting intent and PendingIntent instance
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(host_phone, null, body, null, null);
            Intent i=new Intent(MainActivity.this,Checkout_Acticity.class);
            i.putExtra("Visitor_Email",visitor_email);
            i.putExtra("Visitor_Name",visitor_name);
            i.putExtra("Visitor_Phone",visitor_phone);
            i.putExtra("Host_Email",host_email);
            i.putExtra("Host_Name",host_name);
            i.putExtra("Host_Phone",host_phone);
            i.putExtra("CheckIn",checkin);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    visitor_name+" Please wait for the host" ,
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        try
        {
            Intent i=getIntent();
            host_name=i.getStringExtra("Host_Name");
            host_email=i.getStringExtra("Host_Email");
            host_phone=i.getStringExtra("Host_Phone");
            isHost=true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        requestPermission();
    }
}
