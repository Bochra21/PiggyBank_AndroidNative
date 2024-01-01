package com.example.piggybankapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AmountSpentFragment extends Fragment {


    EditText spentAmount;
    ImageView addBtn, cancelBtn;
     List<ExpenseModel> expensesList = new ArrayList<>();

    public AmountSpentFragment() {
        // Required empty public constructor
    }


    public static AmountSpentFragment newInstance(String param1, String param2) {
        AmountSpentFragment fragment = new AmountSpentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }






    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_amount_spent, container, false);

        spentAmount = view.findViewById(R.id.amount);
        addBtn = view.findViewById(R.id.add_btn2);
        cancelBtn = view.findViewById(R.id.cancel_btn2);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spentAmountString = spentAmount.getText().toString();

                Log.d("spentAmountFragment", "onClick: spentAmountString = " + spentAmountString);

                // Validate if fields are empty
                if (TextUtils.isEmpty(spentAmountString)) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("spentAmountFragment", "onClick: Inside else block");

                    // Retrieve clicked position from arguments
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        int clickedPosition = bundle.getInt("clicked_position", -1);

                        // Ensure position is valid and update spent amount
                        if (clickedPosition >= 0 && clickedPosition < expensesList.size()) {
                            ExpensesFragment expensesFragment = (ExpensesFragment) requireActivity()
                                    .getSupportFragmentManager().findFragmentByTag("fragment_expenses");

                            if (expensesFragment != null) {
                                Log.d("spentAmountFragment", "onClick: Found ExpensesFragment");
                                expensesFragment.updateSpentAmount(spentAmountString, clickedPosition);
                            } else {
                                Log.d("spentAmountFragment", "onClick: ExpensesFragment is null");
                            }

                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.fragment_container, new ExpensesFragment(), "fragment_expenses");
                            fragmentTransaction.addToBackStack("fragment_expenses"); // Add to back stack
                            fragmentTransaction.commit();

                        }
                    }
                }
            }
        });

        return view;
    }






 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_amount_spent, container, false);

        spentAmount = view.findViewById(R.id.amount);
        addBtn = view.findViewById(R.id.add_btn2);
        cancelBtn = view.findViewById(R.id.cancel_btn2);

        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String spentAmountString = spentAmount.getText().toString();


                Log.d("spentAmountFragment", "onClick: spentAmountString = " + spentAmountString);

                // Validate if fields are empty
                if (TextUtils.isEmpty(spentAmountString) ) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("spentAmountFragment", "onClick: Inside else block");
                    // Get the existing instance of ExpensesFragment
                    ExpensesFragment expensesFragment = (ExpensesFragment) requireActivity()
                            .getSupportFragmentManager().findFragmentByTag("fragment_expenses");

                    if (expensesFragment != null)
                    {
                        Log.d("spentAmountFragment", "onClick: Found spentAmountFragment");
                        expensesFragment.updateSpentAmount(spentAmountString, clickedPosition);

                    }
                    else
                    {
                        // Log statement to check if expensesFragment is null
                        Log.d("spentAmountFragment", "onClick: spentAmountFragment is null");
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
            }
        });



        return view;
    }*/
}