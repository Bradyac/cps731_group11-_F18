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

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    String userID;
    String userRecord;
    TextView fname;
    TextView lname;
    TextView email;

    TextView trans1desc, trans1type, trans2desc, trans2type, trans3desc, trans3type, trans1amount, trans2amount, trans3amount;

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

        trans1desc = (TextView) view.findViewById(R.id.trans_1_desc);
        trans2desc = (TextView) view.findViewById(R.id.trans_2_desc);
        trans3desc = (TextView) view.findViewById(R.id.trans_3_desc);

        trans1type = (TextView) view.findViewById(R.id.trans_1_type);
        trans2type = (TextView) view.findViewById(R.id.trans_2_type);
        trans3type = (TextView) view.findViewById(R.id.trans_3_type);

        trans1amount= (TextView) view.findViewById(R.id.trans_1_amount);
        trans2amount= (TextView) view.findViewById(R.id.trans_2_amount);
        trans3amount= (TextView) view.findViewById(R.id.trans_3_amount);

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
        try {
            JSONArray jsonArr = new JSONArray(result);

            for (int i = 0; i < jsonArr.length(); i++)
            {
                JSONObject jObject = jsonArr.getJSONObject(i);
//                Log.d("finally", "Reply: " + jsonObj);
                if (i == 0) {
                    trans1desc.setText(jObject.getString("description"));
                    trans1type.setText(jObject.getString("type"));
                    trans1amount.setText(jObject.getString("amount"));
                }
                if (i == 1) {
                    trans2desc.setText(jObject.getString("description"));
                    trans2type.setText(jObject.getString("type"));
                    trans2amount.setText(jObject.getString("amount"));
                }
                if (i == 3) {
                    trans3desc.setText(jObject.getString("description"));
                    trans3type.setText(jObject.getString("type"));
                    trans3amount.setText(jObject.getString("amount"));
                }
            }




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
