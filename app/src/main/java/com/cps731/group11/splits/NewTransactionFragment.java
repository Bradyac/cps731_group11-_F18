package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NewTransactionFragment extends Fragment {
    EditText nt_Amount, nt_Description;
    Spinner nt_Friends;
    RadioGroup transactionTypes;
    String userID;
    ArrayAdapter<String> adapter;
    Fragment fragment;
    Button btn_ntCreateTransaction;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_transaction, container, false);
        fragment = this;
        userID = ((MainActivity)getActivity()).getCurrentUser();
        NewTransactionBackgroundWorker newTransactionBackgroundWorker = new NewTransactionBackgroundWorker(fragment);
        newTransactionBackgroundWorker.execute("Friends", userID);
        nt_Amount = view.findViewById(R.id.et_ntAmount);
        nt_Description = view.findViewById(R.id.et_ntDescription);
        transactionTypes = view.findViewById(R.id.radio_transactionTypes);
        btn_ntCreateTransaction = view.findViewById(R.id.btn_ntCreateTransaction);
        btn_ntCreateTransaction.setOnClickListener(clickListener);

        return view;
    }
    public void storeFriends(String friendsReply){
        nt_Friends = getActivity().findViewById(R.id.spin_ntFriends);

        ArrayList<String> spinnerNames = new ArrayList<String>();
        Map<String, String> spinnerValues = new HashMap<String, String>();
        String friendsData = friendsReply.substring(6,friendsReply.length());
        Scanner scanner = new Scanner(friendsData).useDelimiter(",");
        while(scanner.hasNext()){
            String name = scanner.next();
            spinnerNames.add(scanner.next());
            spinnerValues.put(name,scanner.next());
        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        nt_Friends.setAdapter(adapter);
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        public void onClick(View view) {
            String str_ntAmount = nt_Amount.getText().toString();
            String str_ntDescription = nt_Description.getText().toString();
            String str_ntFriends = nt_Friends.getSelectedItem().toString();

            int checkedRadioID = transactionTypes.getCheckedRadioButtonId();
            RadioButton checkedRadio = (RadioButton) transactionTypes.findViewById(checkedRadioID);
            String str_ntRadio = (String) checkedRadio.getText();

            NewTransactionBackgroundWorker newTransactionBackgroundWorker = new NewTransactionBackgroundWorker(fragment);
            newTransactionBackgroundWorker.execute("Transaction", userID, str_ntAmount, str_ntDescription, str_ntFriends, str_ntRadio);
        }
    };
}
