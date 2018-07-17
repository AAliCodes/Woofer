package com.example.woofer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Interim extends AppCompatActivity {

    String CurrentUserEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interim);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get user's email
        Intent intent = getIntent();
        CurrentUserEmail = intent.getStringExtra("key");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void Updates(View v){
        Intent myIntent = new Intent(Interim.this, Updates.class);
        myIntent.putExtra("key", CurrentUserEmail); //Optional parameters
        Interim.this.startActivity(myIntent);
    }

    public void Friends(View v){
        Intent friendIntent = new Intent(Interim.this, Friends.class);
        friendIntent.putExtra("key", CurrentUserEmail); //Optional parameters
        Interim.this.startActivity(friendIntent);
    }

}
