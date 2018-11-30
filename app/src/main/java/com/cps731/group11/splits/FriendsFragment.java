package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cps731.group11.splits.adapter.FriendsAdapter;
import com.cps731.group11.splits.model.Friend;

import java.util.ArrayList;
import java.util.Scanner;

public class FriendsFragment extends Fragment {

    private LinearLayoutManager layoutManager;
    private FriendsAdapter friendsAdapter;
    String userID;
    Friend[] list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userID = ((MainActivity)getActivity()).getCurrentUser();
        FriendsBackgroundWorker friendsBackgroundWorker = new FriendsBackgroundWorker(this);
        friendsBackgroundWorker.execute("Friends", userID);

        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.friendList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FriendsAdapter friendsAdapter = new FriendsAdapter(list);
        recyclerView.setAdapter(friendsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false);
    }

    public void storeFriends(String s){
        ArrayList<Friend> friendList = new ArrayList<Friend>();
        String serverReply = s.substring(6,s.length());
        Scanner scanner = new Scanner(serverReply).useDelimiter(",");
        while(scanner.hasNext()){
            friendList.add(new Friend(scanner.next(), Integer.parseInt(scanner.next())));
        }
        list = friendList.toArray(new Friend[friendList.size()]);
    }
}
