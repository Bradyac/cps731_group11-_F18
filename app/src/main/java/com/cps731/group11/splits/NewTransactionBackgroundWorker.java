package com.cps731.group11.splits;

import android.os.AsyncTask;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public class NewTransactionBackgroundWorker extends AsyncTask<String, String, String>{
    Fragment fragment;
    ArrayList<String> friendsList;

    NewTransactionBackgroundWorker (Fragment input){
        fragment = input;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        friendsList = new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        System.out.println(type);
        if (type.equalsIgnoreCase("Friends")) {
            String id = params[1];
            try {
                URL url = new URL("http://splits.atwebpages.com/getFriends.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String server_reply = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    server_reply += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(server_reply);
                return server_reply;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("Transaction")) {
            String id = params[1];
            String amount = params[2];
            String description = params[3];
            String friend = params[4];
            String checkedRadio = params[5];
            String login_url = "http://splits.atwebpages.com/newTransaction.php";
            try {
                // Connection setup
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Request to server
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String client_request = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(checkedRadio, "UTF-8")
                        + URLEncoder.encode("friend", "UTF-8") + "=" + URLEncoder.encode(friend, "UTF-8")
                        + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(amount, "UTF-8")
                        + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");
                bufferedWriter.write(client_request);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // Reply from server
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String server_reply = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    server_reply += line;
                }
                bufferedReader.close();
                inputStream.close();

                // Close connection
                httpURLConnection.disconnect();
                return server_reply;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
