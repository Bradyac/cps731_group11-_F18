package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FPActivityFragment extends Fragment {
    ArrayList<String> transArr;
    ArrayList<String> user1Arr;
    ArrayList<String> user2Arr;
    ArrayList<String> typeArr;
    ArrayList<String> descArr;
    ArrayList<String> amtArr;
    ArrayList<String> dateArr;
    ArrayList<String> listViewItems;
    private ListView lv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_activities, container, false);
    }
    /*
    public void transferData(ArrayList<String> transArrq,ArrayList<String>  user1Arrq,ArrayList<String>  user2Arrq,ArrayList<String>  typeArrq,ArrayList<String>  descArrq,ArrayList<String>  amtArrq,ArrayList<String>  dateArrq) {
        // Logic to manipulate transferred data
        transArr = transArrq;
        user1Arr = user1Arrq;
        user2Arr = user2Arrq;
        typeArr = typeArrq;
        descArr =descArrq;
        amtArr =amtArrq;
        dateArr =dateArrq;
    }*/
}

