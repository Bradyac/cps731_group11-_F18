package com.cps731.group11.splits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    // Initial Android Studio commit
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void OnLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
