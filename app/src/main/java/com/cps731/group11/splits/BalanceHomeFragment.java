package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BalanceHomeFragment extends Fragment {
    Fragment fragment;
    TextView tv_balance, tv_borrowed, tv_lent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = this;
        View view = inflater.inflate(R.layout.fragment_home_balance, container, false);
        tv_balance = view.findViewById(R.id.tv_balance);
        tv_borrowed = view.findViewById(R.id.tv_home_borrowed);
        tv_lent = view.findViewById(R.id.tv_home_lent);

        String type = "test";
        String userID = ((MainActivity) getActivity()).getCurrentUser();
        HomeBackgroundWorker homeBackgroundWorker = new HomeBackgroundWorker(fragment);
        homeBackgroundWorker.execute(type, userID);

        return view;
    }


    // Refresh fragment when it comes into view
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }

    public void transferData(String reply) {
        Log.d("BalanceHomeFragment", "Reply from background worker: " + reply);
        String[] split = reply.split(",");

        tv_borrowed.setText(split[0]);
        tv_lent.setText(split[1]);
        double balance = Double.parseDouble(split[0]) - Double.parseDouble(split[1]);
        double rounded = Math.round(balance * 100.0) / 100.0;

        tv_balance.setText(Double.toString(rounded));
    }
}
