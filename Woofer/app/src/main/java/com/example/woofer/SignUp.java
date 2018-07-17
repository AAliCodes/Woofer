package com.example.woofer;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

    public void Insert(View v){
        //declare variables
        String sName, sSurname, sPassword, sConfirm, sEmail;
        //declare editTexts
        EditText Name = (EditText)findViewById(R.id.editTextName);
        EditText Surname = (EditText)findViewById(R.id.editTextSurname);
        EditText Password = (EditText)findViewById(R.id.editTextPassword);
        EditText Confirm = (EditText)findViewById(R.id.editTextConfirm);
        EditText Email = (EditText)findViewById(R.id.editTextEmail);
        //Assign Values to variables
        sPassword = Password.getText().toString();
        sConfirm = Confirm.getText().toString();

        //Confirm passwords alike
        TextView t = (TextView)findViewById(R.id.textViewError);
        if(!(sPassword.equals(sConfirm))){

            t.setText("Passwords do not match");
        }else
        {
            t.setText("Passwords match");
            System.out.println("else entered");
            //insert into database
            ContentValues params = new ContentValues();

            params.put("Email",Email.getText().toString());
            params.put("Name",Name.getText().toString());
            params.put("Surname",Surname.getText().toString());
            params.put("Password",Password.getText().toString());

            AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/InsertUser.php",params) {
                @Override
                protected  void onPostExecute(String output) {

                    System.out.println("Output " + output);
                    //System.out.println("Output : " + processJSON(output));

                }
            };
            asyncHttpPost.execute();
            System.out.println("Insert completed");
        }

        finish();
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

}
