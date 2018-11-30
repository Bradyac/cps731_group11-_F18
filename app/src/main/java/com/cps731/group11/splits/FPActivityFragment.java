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
        listViewItems = new ArrayList<String>();
        lv = (ListView) getActivity().findViewById(R.id.activity_activities_dynamic);





        for (int i =0;i<transArr.size();i++){
            String listViewItem = "You " + typeArr.get(i) + " " + amtArr.get(i) + " with " + user2Arr.get(i) + " for " + descArr.get(i) + " on " + dateArr.get(i) + ".";
            listViewItems.add(listViewItem);
}
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, listViewItems);

        lv.setAdapter(arrayAdapter);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_history, container, false);
    }
    public void transferData(ArrayList<String> transArrq,ArrayList<String>  user1Arrq,ArrayList<String>  user2Arrq,ArrayList<String>  typeArrq,ArrayList<String>  descArrq,ArrayList<String>  amtArrq,ArrayList<String>  dateArrq) {
        // Logic to manipulate transferred data
        transArr = transArrq;
        user1Arr = user1Arrq;
        user2Arr = user2Arrq;
        typeArr = typeArrq;
        descArr =descArrq;
        amtArr =amtArrq;
        dateArr =dateArrq;
    }
}
    //fragment_activity.xml
