package com.katemaplewood.bitstoken;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Main2Activity extends AppCompatActivity {

    private Button gtoken;
    private Button vtoken;
    private Button list;
    private String time, com;
    private EditText ecomment;
    private TimePicker mTimePicker;
    String prevtime, prevCom;

    private TextView t1;
    String oldVersion = "Ac0";
    String currToken;
    private DatabaseReference mDatabase, mRef;

    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Log.d("TrailCFB", "Child added event");
            oldVersion = "Ac" + (Integer.parseInt(oldVersion.substring(2)) + 1);
            currToken = oldVersion;
            mRef.setValue(currToken);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newacto);

        //Database References
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CodesAcc"); // Pointing to Codes
        mRef = FirebaseDatabase.getInstance().getReference("Current TokenAc"); //Pointing to OldVersion
        mDatabase.addChildEventListener(mChildEventListener);


        gtoken = (Button) findViewById(R.id.Firebase_btnT);
        ecomment = (EditText) findViewById(R.id.EcommentT);
        vtoken = (Button) findViewById(R.id.viewbtnT);
        list = (Button) findViewById(R.id.listT);
        t1 = (TextView) findViewById(R.id.textView);
        mTimePicker = (TimePicker) findViewById(R.id.TimeT);

        //OnClick listener for Get Token
        gtoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> dataMap = new HashMap<String, Object>();
                getInputData();
                dataMap.put("Token", oldVersion);
                dataMap.put("Time Slot", time);
                dataMap.put("Comments", com);

                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Log.d("TrailCFB", "Task Successful");
                            invokeAlert();


                        }
                    }
                });


            }
        });

        // View Token number Button
        vtoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                invokeAlertremove();
            }
        });

        //WebView of Token being called
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent innt = new Intent(Main2Activity.this, tklist.class);
                startActivity(innt);
            }
        });

    }


    public void invokeAlertremove() {

        if (prevCom != null && prevtime != null) {

            final AlertDialog.Builder a1 = new AlertDialog.Builder(this);
            a1.setTitle("Token Details");
            a1.setMessage("My Token number is: " + oldVersion + "\n" + "Comments: " + prevCom + "\n" + "Time Slot: " + prevtime);

            a1.setPositiveButton("Ok", null);
            a1.setNegativeButton("Remove Token", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("TrailCFB", "Remove Token");


                }
            })
                    .setCancelable(false)
                    .show();

        }

    else{
        final AlertDialog.Builder a1 = new AlertDialog.Builder(this);
        a1.setTitle("Token Details");
        a1.setMessage("My Token number is: " + oldVersion + "\n" + "Comments: " + "Fee Payment" + "\n" + "Time Slot: " + "13:10");

        a1.setPositiveButton("Ok", null);
        a1.setNegativeButton("Remove Token", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TrailCFB", "Remove Token");


            }
        })
                .setCancelable(false)
                .show();


    }

}

    //Alert after button click with details
    public void invokeAlert(){

                AlertDialog.Builder a1=new AlertDialog.Builder(this);
                a1.setTitle("Reminder");
                a1.setMessage("Your Token number is "+oldVersion);
                a1.setCancelable(true);
                a1.show();

    }

      //Get USER Comments and time
    private void getInputData(){
        //Get Time Data
        int hr=mTimePicker.getCurrentHour();
        int min=mTimePicker.getCurrentMinute();

        if(min<10) {
            time = hr + ":0" + min;
        }
        else
            time=hr+":"+min;


        //Get Comment Data
        com=ecomment.getText().toString();
        prevtime=time;
        prevCom=com;


        //Shared Pref
        SharedPreferences prefs=getSharedPreferences("PrevVal",0);
        prefs.edit().putString("PrevTime",prevtime).apply();
        prefs.edit().putString("PrevCom",prevCom ).apply();

    }

}



