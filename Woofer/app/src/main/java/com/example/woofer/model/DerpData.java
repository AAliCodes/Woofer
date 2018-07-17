package com.example.woofer.model;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import com.example.woofer.AsyncHTTPPost;
import com.example.woofer.R;
import com.example.woofer.adapter.FriendAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 2017/05/12.
 */
public class DerpData{

    public RecyclerView getRecView() {
        return recView;
    }

    public void setRecView(RecyclerView recView) {
        this.recView = recView;
    }

    public FriendAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FriendAdapter adapter) {
        this.adapter = adapter;
    }

    public ArrayList getListData() {
        return listData;
    }

    public void setListData(ArrayList listData) {
        this.listData = listData;
    }

    private RecyclerView recView;
    private FriendAdapter adapter;
    private ArrayList listData;

    //return dataset with updates pertaining to Current User
    public static List<ListItem> getUpdates(String CurrentUserEmail){
        final List<ListItem> data = new ArrayList<>();
        ContentValues params = new ContentValues();
        params.put("Email",CurrentUserEmail);
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
            }
        };
        asyncHttpPost.execute();
    return data;
    }





    //return dataset with friends pertaining to current user
    public static List<ListItem> getFriends(String CurrentUserEmail){

        final List<ListItem> data = new ArrayList<>();
        ContentValues params = new ContentValues();
        params.put("Email",CurrentUserEmail);
        String address = "http://lamp.ms.wits.ac.za/~s1036074/ListFriends.php";
        String response = "";


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
                    //listener.onTaskCompleted();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        asyncHttpPost.execute();
        return data;
    }



}
