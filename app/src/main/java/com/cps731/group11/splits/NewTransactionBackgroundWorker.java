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

public class NewTransactionBackgroundWorker extends AsyncTask<String, String, String>{
    Context context;
    NewTransactionBackgroundWorker (Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params){
        String type = params[0];
        String nt_url = "http://splits.atwebpages.com/newTransaction.php";
        if(type.equals("newTransaction")){
            try{
                URL url = new URL(nt_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
