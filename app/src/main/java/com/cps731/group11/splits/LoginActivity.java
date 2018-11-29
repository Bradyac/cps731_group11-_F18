package com.cps731.group11.splits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText et_email, et_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
    }

    public void OnLogin(View view) {
        String type = "login";
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        LoginBackgroundWorker loginBackgroundWorker = new LoginBackgroundWorker(this);
        loginBackgroundWorker.execute(type, email, password);


    }
}
