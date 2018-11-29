package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BalanceHomeFragment extends Fragment {
    Fragment fragment;
    TextView tv_balance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = this;
        View view = inflater.inflate(R.layout.fragment_home_balance, container, false);
        tv_balance = view.findViewById(R.id.tv_balance);
        tv_balance.setOnClickListener(clickListener);

        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View view) {
            String type = "test";
            String userID = ((MainActivity) getActivity()).getCurrentUser();
            HomeBackgroundWorker homeBackgroundWorker = new HomeBackgroundWorker(fragment);
            homeBackgroundWorker.execute(type, userID);
        }
    };

    public void tester(String reply) {
        Log.d("BalanceHomeFragment", "Reply from background worker: " + reply);
        tv_balance.setText(reply);
    }
}
