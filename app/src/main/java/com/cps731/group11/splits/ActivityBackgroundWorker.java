package com.cps731.group11.splits;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import java.sql.Blob;
import java.util.ArrayList;

public class ActivityBackgroundWorker extends AsyncTask<String,Void,String> {
    Fragment fragment;
    Context context;
    AlertDialog alertDialog;

    ActivityBackgroundWorker(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String activity_url = "http://splits.atwebpages.com/activity.php";
        String resultString = "";
        switch(type) {
            case "activity":

                String user_id1 = params[1];
                try {
                    // Connection setup
                    URL url = new URL(activity_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    // Request to server
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String client_request = URLEncoder.encode("user_id1", "UTF-8") + "=" + URLEncoder.encode(user_id1,"UTF-8");
                    bufferedWriter.write(client_request);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // Reply from server
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    ArrayList<String> server_reply = new ArrayList<String>();

                    String line;
                    while((line = bufferedReader.readLine()) != null) {

                        String temp = String.join(",", line);
                        String temp2 = temp.replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                        resultString = resultString.concat(temp2);
                        resultString = resultString.concat("\n");

                        server_reply.add(line);
                        server_reply.add("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();

                    // Close connection
                    httpURLConnection.disconnect();



                    return resultString;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return "";
    }

    @Override
    protected void onPostExecute(String server_reply) {
        super.onPostExecute(server_reply);
        if(server_reply.equals("error!")) {
            // Login Failed: Alert that login failedretrieve data.");
            alertDialog.show();
        } else {
            // Login Success:
            ArrayList<String> transArr = new ArrayList<String>();
            ArrayList<String> user1Arr = new ArrayList<String>();
            ArrayList<String> user2Arr = new ArrayList<String>();
            ArrayList<String> typeArr = new ArrayList<String>();
            ArrayList<String> descArr = new ArrayList<String>();
            ArrayList<String> amtArr = new ArrayList<String>();
            ArrayList<String> dateArr = new ArrayList<String>();
            String lines[] = server_reply.split("\\r?\\n");
            for (int i=0;i<lines.length;i++){
                String[] transRow = lines[i].split(",");
                transArr.add(transRow[0]);
                user1Arr.add(transRow[1]);
                user2Arr.add(transRow[2]);
                typeArr.add(transRow[3]);
                descArr.add(transRow[4]);
                amtArr.add(transRow[5]);
                dateArr.add(transRow[6]);
            }

            // USE THIS TO TRANSFER DATA. SET DATA TO PARAM. THIS METHOD IS IN ActivityFragment.java
            //((FPActivityFragment) fragment).transferData(transArr, user1Arr, user2Arr, typeArr, descArr, amtArr, dateArr);


            Intent intent = new Intent(context, FPActivityFragment.class);
            intent.putExtra("TRANSACTION_IDS", transArr);
            intent.putExtra("USER_IDS1", user1Arr);
            intent.putExtra("USER_IDS2", user2Arr);
            intent.putExtra("TYPES", typeArr);
            intent.putExtra("DESCRIPTIONS", descArr);
            intent.putExtra("AMOUNTS", amtArr);
            intent.putExtra("DATES", dateArr);



        }
    }
}