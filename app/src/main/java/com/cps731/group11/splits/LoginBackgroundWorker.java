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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;

    LoginBackgroundWorker (Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://splits.atwebpages.com/login.php";
        switch(type) {
            case "login":
                String email = params[1];
                String password = params[2];

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
                                     + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");
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
