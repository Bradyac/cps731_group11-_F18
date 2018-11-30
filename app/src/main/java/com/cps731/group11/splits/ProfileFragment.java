package com.cps731.group11.splits;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ProfileFragment extends Fragment {
    String userID;
    String userRecord;
    TextView fname;
    TextView lname;
    TextView email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle arguments = this.getArguments();

        if (arguments != null && arguments.containsKey("USER_ID")){
            userID = this.getArguments().getString("USER_ID");
        }
        else {
            userID = ((MainActivity) getActivity()).getCurrentUser();
        }
        Log.d("TESTING", userID);

        fname = (TextView) view.findViewById(R.id.display_fname);
        lname = (TextView) view.findViewById(R.id.display_lname);
        email = (TextView) view.findViewById(R.id.display_email);

        String type = "getUser";

        ProfileBackgroundWorker profileBackgroundWorker = new ProfileBackgroundWorker(this);
        profileBackgroundWorker.execute(type, userID);


        ProfileBackgroundWorker profileBackgroundWorker2 = new ProfileBackgroundWorker(this);
        profileBackgroundWorker2.execute("getTransactions", userID);

        return view;
    }

    public void setUserInfo(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            fname.setText(jObject.getString("fname"));
            lname.setText(jObject.getString("lname"));
            email.setText(jObject.getString("email"));
        } catch (JSONException e) {
            // Oops
        }
    }

    // This will render the list of transactions for the user.
    public void renderTransactionList(String result) {

    }
}
