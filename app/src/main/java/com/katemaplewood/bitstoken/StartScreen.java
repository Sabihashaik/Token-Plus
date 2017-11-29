package com.katemaplewood.bitstoken;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

    Button mmain,help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        mmain=(Button)findViewById(R.id.Firebase_btnT);
        help=(Button)findViewById(R.id.help);


        mmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent1);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getApplicationContext(),help.class);
                finish();
                startActivity(intent2);
            }
        });
    }
}
