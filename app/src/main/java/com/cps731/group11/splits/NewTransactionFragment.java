package com.cps731.group11.splits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.EditText;

import java.util.ArrayList;

public class NewTransactionFragment extends Fragment {
    EditText nt_Amount, nt_Description;
    Spinner nt_Friends;
    RadioGroup transactionTypes;
    String userID;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems = new ArrayList<>();
    Fragment fragment;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = this;
        userID = ((MainActivity)getActivity()).getCurrentUser();
        NewTransactionBackgroundWorker newTransactionBackgroundWorker = new NewTransactionBackgroundWorker(fragment);
        newTransactionBackgroundWorker.execute("Friends", userID);
        nt_Amount = getActivity().findViewById(R.id.et_ntAmount);
        nt_Description = getActivity().findViewById(R.id.et_ntDescription);
        nt_Friends = getActivity().findViewById(R.id.spin_ntFriends);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.fragment_new_transaction,R.id.spin_ntFriends,listItems);
        nt_Friends.setAdapter(adapter);
        transactionTypes = getActivity().findViewById(R.id.radio_transactionTypes);

        View view = inflater.inflate(R.layout.fragment_new_transaction, container, false);
        return view;
    }
    public void createTransaction(View view){
        String str_ntAmount = nt_Amount.getText().toString();
        String str_ntDescription = nt_Description.getText().toString();
        String str_ntFriends = nt_Friends.getSelectedItem().toString();

        int checkedRadioID = transactionTypes.getCheckedRadioButtonId();
        RadioButton checkedRadio = (RadioButton)transactionTypes.findViewById(checkedRadioID);
        String str_ntRadio = (String)checkedRadio.getText();

        NewTransactionBackgroundWorker newTransactionBackgroundWorker = new NewTransactionBackgroundWorker(fragment);
        newTransactionBackgroundWorker.execute("Transaction",userID,str_ntAmount,str_ntDescription,str_ntFriends,str_ntRadio);
    }
}
