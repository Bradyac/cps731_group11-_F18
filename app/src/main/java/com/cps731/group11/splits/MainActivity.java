package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.navigation);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(navigationListener);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.setItemHorizontalTranslationEnabled(false);
        bottomNavigationViewEx.setTextVisibility(false);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_friends:
                    selectedFragment = new FriendsFragment();
                    break;
                case R.id.navigation_new_transaction:
                    selectedFragment = new NewTransactionFragment();
                    break;
                case R.id.navigation_activity:
                    selectedFragment = new ActivityFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                default:
                    selectedFragment = new HomeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}
