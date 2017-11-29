package com.katemaplewood.bitstoken;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class tklist extends AppCompatActivity {

    WebView w1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tokenlist);


        w1=(WebView)findViewById(R.id.web);

        //Getting WebApp
        w1.loadUrl("");


    }
}
