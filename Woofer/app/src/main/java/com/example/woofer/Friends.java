package com.example.woofer;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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
import java.util.concurrent.TimeUnit;

public class Friends extends AppCompatActivity implements FriendAdapter.ItemClickCallback{

    //Required Strings
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    boolean Explored = false;
    String CurrentUserEmail = "";
    //For recyclerView
    private RecyclerView recView;
    private FriendAdapter adapter;
    private ArrayList listData;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFriend(view);
            }
        });

        //get CurrentUserEmail
        Intent intent = getIntent();
        CurrentUserEmail = intent.getStringExtra("key");

        //show friends
        Toolbar t = (Toolbar)findViewById(R.id.toolbar);
        t.setTitle("My Friends");
        showFriends(CurrentUserEmail);
    }

    public void showFriends(String temp){

        if (temp.equals(CurrentUserEmail)){
            Toolbar t = (Toolbar)findViewById(R.id.toolbar);
            t.setTitle("My Friends");
        }
        final List<ListItem> data = new ArrayList<>();
        ContentValues params = new ContentValues();
        params.put("Email",temp);
        final Context context = this;

        AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/ListFriends.php",params) {
            @Override
            protected void onPostExecute(String output) {
                System.out.println("Output : " + output);
                try{
                    JSONArray all = new JSONArray(output);
                    for (int i=0; i<all.length(); i++){
                        JSONObject item = all.getJSONObject(i);
                        ListItem li = new ListItem();
                        li.setImageResid(android.R.drawable.ic_popup_reminder);
                        li.setTitle(item.getString("FRIEND_2_EMAIL"));
                        li.setUpdate("");
                        data.add(li);
                        System.out.println((item.getString("FRIEND_2_EMAIL")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listData = (ArrayList)data;
                adapter.notifyDataSetChanged();

            }
        };
        asyncHttpPost.execute();


        listData = (ArrayList)data;
        recView = (RecyclerView)findViewById(R.id.rec_friend_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendAdapter(listData, this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

    }

    //add friend using handle
    //ADD CHECKING MEASURES
    public void AddFriend(View v){

        System.out.println(CurrentUserEmail);
        //build dialog
        final EditText e = new EditText(this);


        AlertDialog.Builder inputBox = new AlertDialog.Builder(this);
        inputBox.setTitle("Add Friend");
        inputBox.setView(e);
        inputBox.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String entered = e.getText().toString();
                System.out.println("entered : " + entered);
                //Insert update to DB
                ContentValues params = new ContentValues();

                params.put("Friend1",CurrentUserEmail);
                params.put("Friend2",entered);

                if (!(CurrentUserEmail).equals(entered)) {
                    AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/AddFriend.php", params) {
                        @Override
                        protected void onPostExecute(String output) {

                            System.out.println("Output " + output);
                            System.out.println("Output " + CurrentUserEmail);
                            //System.out.println("Output : " + processJSON(output));

                            finish();
                            startActivity(getIntent());

                        }
                    };
                    asyncHttpPost.execute();
                    System.out.println("Insert completed");
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("You cannot be friends with yourself.");


                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener()     {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }


        });
        inputBox.show();
        adapter.notifyDataSetChanged();


    }




    @Override
    public void onItemClick(int p) {
        final ListItem item = (ListItem) listData.get(p);
        System.out.println("title: " + item.getTitle());

        //bring up menu
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Options");

        builder.setPositiveButton("Show Friends", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things
                Toolbar t = (Toolbar)findViewById(R.id.toolbar);
                t.setTitle(item.getTitle() + "'s Friends");
                showFriends(item.getTitle());
                Explored = true;
                //listData = (ArrayList)DerpData.getFriends(item.getTitle());
                //adapter.notifyDataSetChanged();
                recView.invalidate();
                //adapter.notifyDataSetChanged();
                //recView.invalidate();
            }
        });

        builder.setNegativeButton("Add as Friend", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                System.out.println("entered : " + item.getTitle());
                //Insert update to DB
                ContentValues params = new ContentValues();

                params.put("Friend1",CurrentUserEmail);
                params.put("Friend2",item.getTitle());

                if (!(CurrentUserEmail.equals(item.getTitle()))){
                    AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1036074/AddFriend.php",params) {
                        @Override
                        protected  void onPostExecute(String output) {

                            System.out.println("Output " + output);
                            System.out.println("Output " + CurrentUserEmail);
                            }
                    };
                    asyncHttpPost.execute();
                    System.out.println("Insert completed");
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("You cannot be friends with yourself.");


                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener()     {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }




            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener()     {
            public void onClick(DialogInterface dialog, int id) {
                //do things
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void onBackPressed() {
        if(Explored){

            showFriends(CurrentUserEmail);
            Explored = false;
        }
        else{
            finish();
        }
    }

    @Override
    public void onSecondaryIconClick(int p) {

    }
}
