package com.example.woofer;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woofer.adapter.FriendAdapter;
import com.example.woofer.adapter.WooferAdapter;
import com.example.woofer.model.DerpData;
import com.example.woofer.model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Updates extends AppCompatActivity {

    String CurrentUserEmail = "";

    //For recyclerView
    private  RecyclerView recView;
    private WooferAdapter adapter;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        CurrentUserEmail = intent.getStringExtra("key");
        System.out.println("Current Email onactivate " + CurrentUserEmail );

        //My interface stuff
        ShowUpdates();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                insertUpdate(view);
            }
        });
    }

    public List<ListItem> getUpdates(){
        final List<ListItem> data = new ArrayList<>();
        ContentValues params = new ContentValues();
        params.put("Email",CurrentUserEmail);
        System.out.println("Current Email " + CurrentUserEmail );
        AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/GetUpdates.php",params) {
            @Override
            protected void onPostExecute(String output) {
                System.out.println("Output : " + output);
                try{
                    JSONArray all = new JSONArray(output);
                    for (int i=0; i<all.length(); i++){
                        JSONObject item = all.getJSONObject(i);
                        ListItem li = new ListItem();
                        li.setImageResid(android.R.drawable.ic_popup_reminder);
                        li.setTitle(item.getString("USER_EMAIL"));
                        li.setUpdate(item.getString("STATUS_STATUS"));
                        data.add(li);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listData = (ArrayList)data;
                adapter.notifyDataSetChanged();

            }
        };
        asyncHttpPost.execute();
        return data;
    }

    public void ShowUpdates(){

//        final List<ListItem> data = new ArrayList<>();
//        ContentValues params = new ContentValues();
//        params.put("Email",CurrentUserEmail);
//        AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/GetUpdates.php",params) {
//            @Override
//            protected void onPostExecute(String output) {
//                System.out.println("Output : " + output);
//                try{
//                    JSONArray all = new JSONArray(output);
//                    for (int i=0; i<all.length(); i++){
//                        JSONObject item = all.getJSONObject(i);
//                        ListItem li = new ListItem();
//                        li.setImageResid(android.R.drawable.ic_popup_reminder);
//                        li.setTitle(item.getString("USER_EMAIL"));
//                        li.setUpdate(item.getString("STATUS_STATUS"));
//                        data.add(li);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                listData = (ArrayList)data;
//                adapter.notifyDataSetChanged();
//
//            }
//        };
//        asyncHttpPost.execute();

        List<ListItem> data = getUpdates();
        listData = (ArrayList)data;
        //Thread.sleep(1000, TimeUnit.MILLISECONDS);
        recView = (RecyclerView)findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WooferAdapter(listData, this);
        recView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }


    public void insertUpdate(View v){

        //build dialog
        final EditText e = new EditText(this);
        AlertDialog.Builder inputBox = new AlertDialog.Builder(this);
        inputBox.setTitle("Update Status");
        inputBox.setView(e);
        //inputBox.
        inputBox.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String entered = e.getText().toString();
                System.out.println("entered : " + entered);
                String DateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                System.out.println("DateTime : " + DateTime);

                //Insert update to DB
                ContentValues params = new ContentValues();

                params.put("Email",CurrentUserEmail);
                params.put("Update",entered);
                params.put("DateTime",DateTime);

                AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/InsertStatus.php",params) {
                    @Override
                    protected  void onPostExecute(String output) {

                        System.out.println("Output " + output);
                        //System.out.println("Output : " + processJSON(output));
                        List<ListItem> data = getUpdates();
                        listData = (ArrayList)data;
                        adapter.notifyDataSetChanged();
                        recView.invalidate();

                        finish();
                        startActivity(getIntent());
                    }


                };
                asyncHttpPost.execute();
                System.out.println("Insert completed");


            }


        });
        //inputBox.show().getWindow().getDecorView().getBackground().setColorFilter(@color/);
        //inputBox.
        inputBox.show();
        ShowUpdates();

//        List<ListItem> data = getUpdates();
//        listData = (ArrayList)data;
        //Thread.sleep(1000, TimeUnit.MILLISECONDS);
//        recView = (RecyclerView)findViewById(R.id.rec_list);
//        recView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new WooferAdapter(listData, this);
//        recView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        recView.invalidate();




    }


}
