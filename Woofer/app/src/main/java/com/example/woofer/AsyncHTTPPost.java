package com.example.woofer;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncHTTPPost extends AsyncTask<String, String, String> {
    String address;
    ContentValues parameters;
    String res;

    Context context;

    //private final ProgressDialog dialog = new ProgressDialog(Friends.this);


    public AsyncHTTPPost( String address, ContentValues parameters) {
        //this.context = context;
        //this.listener = listener;
        this.address = address;
        this.parameters = parameters;
    }




    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(address);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            if (parameters.size() > 0) {
                Uri.Builder builder = new Uri.Builder();
                for (String s : parameters.keySet()) {
                    builder.appendQueryParameter(s,
                            parameters.getAsString(s));
                }
                String query = builder.build().getEncodedQuery();
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String response = br.readLine();
            res = response;
            br.close();
            //System.out.println("Output" + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    @Override
    protected void onPostExecute(String output){
        //do Something with the output
        //listener.onTaskCompleted();
        res = output;
        System.out.println("Output : " + res);
        //TextView t = (TextView)((MainActivity)context).findViewById(R.id.textView);
        //t.setText(output);

    }


}