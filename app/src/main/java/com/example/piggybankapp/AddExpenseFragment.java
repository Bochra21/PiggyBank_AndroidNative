package com.example.piggybankapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpenseFragment extends Fragment
{


    EditText expenseName;
    EditText amount;

    ImageView addBtn, cancelBtn;


    public AddExpenseFragment()
    {

    }

    public static AddExpenseFragment newInstance(String param1, String param2) {
        AddExpenseFragment fragment = new AddExpenseFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        expenseName = view.findViewById(R.id.expense_name);
        amount = view.findViewById(R.id.amount);
        addBtn = view.findViewById(R.id.add_btn2);
        cancelBtn = view.findViewById(R.id.cancel_btn2);



        if (TextUtils.isEmpty(expenseName.getText().toString()) && TextUtils.isEmpty(amount.getText().toString())) {


            addBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    String expenseName2 = expenseName.getText().toString();
                    String amount2 = amount.getText().toString();

                    // Get the existing instance of ExpensesFragment
                    ExpensesFragment expensesFragment = (ExpensesFragment) requireActivity()
                            .getSupportFragmentManager().findFragmentByTag("expenses_fragment");

                    if (expensesFragment != null)
                    {
                        // Call the updateData method of ExpensesFragment
                        expensesFragment.updateData(expenseName2, amount2);
                    }

                    // Navigate back to ExpensesFragment if needed

                    try {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();  // Remove AddExpenseFragment from the back stack
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*
                    Bundle bundle = new Bundle();
                    bundle.putString("name", expenseName2);
                    bundle.putString("amount", amount2);
                    //
                    ExpensesFragment expensesFragment = new ExpensesFragment();
                    expensesFragment.setArguments(bundle);

                    try {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, expensesFragment, "expenses_fragment");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception or log the details
                        System.out.println(" fragment ex");
                    }


                    */


                }
            });




        } else {
           System.out.println(" one of the edit texts is null");
            Toast.makeText(getContext(), "Your message here", Toast.LENGTH_SHORT).show();

        }








        return view;
    }


}