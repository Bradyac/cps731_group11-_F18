package com.cps731.group11.splits;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FriendsBackgroundWorker extends AsyncTask<String,String,String> {

    Fragment fragment;
    ArrayList<String> friendsList;

    FriendsBackgroundWorker(Fragment input){
        fragment = input;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        friendsList = new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if (type.equalsIgnoreCase("Friends")) {
            String id = params[1];
            String login = "http://splits.atwebpages.com/getFriends.php";
            try {
                URL url = new URL(login);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String client_request = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(client_request);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

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
                return server_reply;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.contains("FRIEND")) {
            System.out.println("Reply: " + s);
            ((FriendsFragment)fragment).storeFriends(s);
        }
    }

}
