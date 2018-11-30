package com.cps731.group11.splits;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ProfileBackgroundWorker extends AsyncTask<String,Void,String> {
    Fragment fragment;

    ProfileBackgroundWorker(Fragment fragment) {this.fragment = fragment;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String web_url = "http://splits.atwebpages.com/getUser.php";
        switch (type) {
            case "test":
                String userID = params[1];

                try {
                    // Connection setup
                    URL url = new URL(web_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    // Request to server
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String client_request = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userID,"UTF-8");
                    bufferedWriter.write(client_request);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // Reply from server
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String server_reply = "";
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
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

                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("ProfileBackgroundWorker", "Reply: " + s);
        ((ProfileFragment) fragment).setUserInfo(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}