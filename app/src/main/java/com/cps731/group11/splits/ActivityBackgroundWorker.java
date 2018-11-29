package com.cps731.group11.splits;

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
import java.net.URL;
import java.net.URLEncoder;

public class ActivityBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;

    ActivityBackgroundWorker(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Activity Status");
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://splits.atwebpages.com/activity.php";
        switch(type) {
            case "activity":
                String email = params[1];
                String user_id1 = params[2];
                String user_id2 = params[3];
                String transtype = params[4];
                String desc = params[5];
                String amount = params[6];



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
                    String client_request = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                            + URLEncoder.encode("user_id1", "UTF-8") + "=" + URLEncoder.encode(user_id1,"UTF-8") + "&"
                            + URLEncoder.encode("user_id1", "UTF-8") + "=" + URLEncoder.encode(user_id1,"UTF-8") + "&"
                            + URLEncoder.encode("user_id2", "UTF-8") + "=" + URLEncoder.encode(user_id2,"UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(transtype,"UTF-8") + "&"
                            + URLEncoder.encode("desc", "UTF-8") + "=" + URLEncoder.encode(desc,"UTF-8") + "&"
                            + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(amount,"UTF-8");
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
    protected void onPostExecute(String server_reply) {
        if(server_reply.equals("failed")) {
            // Login Failed: Alert that login failed
            alertDialog.setMessage("Login failed. Please try again.");
            alertDialog.show();
        } else {
            // Login Success:
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("CURRENT_USER_ID", server_reply);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}