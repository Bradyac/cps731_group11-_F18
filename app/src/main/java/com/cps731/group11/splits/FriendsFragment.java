package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cps731.group11.splits.adapter.FriendsAdapter;
import com.cps731.group11.splits.model.Friend;

import java.sql.Blob;

public class FriendsFragment extends Fragment {

    private LinearLayoutManager layoutManager;
    private FriendsAdapter friendsAdapter;
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false);
    }
}
