package com.example.sgajraj.androidchathead;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,ChatHeadService.class));
        finish();
    }
}
