package com.example.piggybankapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

// AddExpenseFragment.java
public class AddExpenseFragment extends Fragment {

    EditText expenseName;
    EditText amount;
    ImageView addBtn, cancelBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        expenseName = view.findViewById(R.id.expense_name);
        amount = view.findViewById(R.id.amount);
        addBtn = view.findViewById(R.id.add_btn2);
        cancelBtn = view.findViewById(R.id.cancel_btn2);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expenseName2 = expenseName.getText().toString();
                String amount2 = amount.getText().toString();
                // Log statements for debugging
                Log.d("AddExpenseFragment", "onClick: expenseName2 = " + expenseName2);
                Log.d("AddExpenseFragment", "onClick: amount2 = " + amount2);

                // Validate if fields are empty
                if (TextUtils.isEmpty(expenseName2) || TextUtils.isEmpty(amount2)) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("AddExpenseFragment", "onClick: Inside else block");
                    // Get the existing instance of ExpensesFragment
                    ExpensesFragment expensesFragment = (ExpensesFragment) requireActivity()
                            .getSupportFragmentManager().findFragmentByTag("fragment_expenses");

                    if (expensesFragment != null)
                    {
                        Log.d("AddExpenseFragment", "onClick: Found ExpensesFragment");
                        expensesFragment.updateData(expenseName2, amount2);
                        Log.d("updateData", "updateData in add expense fragment, expenseName2 = " + expenseName2);
                    }
                    else
                    {
                        // Log statement to check if expensesFragment is null
                        Log.d("AddExpenseFragment", "onClick: ExpensesFragment is null");
                    }


                    // Navigate back to ExpensesFragment if needed
                    try
                    {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();  // Remove AddExpenseFragment from the back stack
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }


                InsertDataInFirebase();








            }
        });



        return view;
    }

    //
    public void InsertDataInFirebase()
    {
        String year = "2023";
        String month = "january";
        Log.d("add expense to firebase", "InsertDataInFirebase called");
        Map<String,Object> map = new HashMap<>();
        map.put("name",expenseName.getText().toString());
        map.put("amount_to_spend",amount.getText().toString());
        map.put("amount_spent","0");
        //for test
        if (!map.isEmpty()) {
            // Map is not empty, do something
            Log.d("add expense to firebase", "Map is not empty");
        } else {
            // Map is empty
            Log.d("add expense to firebase", "Map is empty");
        }

        DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference()
                .child("years").child(year).child("months").child(month).child("expenses");

        expensesRef.push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                       // Toast.makeText(getContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
                        Log.d("add expense to firebase", "onSuccess called");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Toast.makeText(getContext(), "Error while adding data", Toast.LENGTH_SHORT).show();
                        Log.d("add expense to firebase", "onFailure called");
                    }
                });
    }

}


