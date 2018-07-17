package com.example.woofer;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void Login(View v){

        final EditText edtEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText edtPassword = (EditText) findViewById(R.id.editTextPassword);
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();


        //get password from db
        ContentValues params = new ContentValues();

        params.put("Email",email);
        AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/Login.php",params) {
            @Override
            protected  void onPostExecute(String output) {

                System.out.println(output);
                System.out.println("Output : " + processJSON(output));
                TextView t = (TextView) findViewById(R.id.textViewMessage);
                if (output.equals("[]")){
                    t.setText("Username does not exist. Please Register by clicking 'Sign Up'");
                }else
                if (!(password.equals(processJSON(output)))){

                    t.setText("incorrect Password Entered");
                }else
                if ((password.equals("")) || (email.equals(""))) {

                    t.setText("Please enter a valid username-password combination");
                }else
                {
                    t.setText("Correct Password Entered");
                    Intent myIntent = new Intent(login_activity.this, Interim.class);
                    myIntent.putExtra("key", edtEmail.getText().toString()); //Optional parameters
                    login_activity.this.startActivity(myIntent);

                }
            }
        };
        asyncHttpPost.execute();
    }

    public String processJSON( String json){
        String Res = "";
        try {
            JSONArray all = new JSONArray(json);
            RelativeLayout r = (RelativeLayout) findViewById(R.id.content_login_activity);
            //LinearLayout l = new LinearLayout(this);
            for (int i=0; i<all.length(); i++){
                JSONObject item=all.getJSONObject(i);
                String USER_EMAIL = item.getString("USER_EMAIL");
                String PASSWORD = item.getString("PASSWORD");
                String NAME = item.getString("NAME");
                String SURNAME = item.getString("SURNAME");
                Res = Res + PASSWORD;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Res;
    }

    public void SignUp(View v){
        Intent myIntent = new Intent(login_activity.this, SignUp.class);
        //myIntent.putExtra("key", value); //Optional parameters
        login_activity.this.startActivity(myIntent);
    }



}
