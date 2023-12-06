package com.example.piggybankapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesFragment extends Fragment
{



    TextView date ;
    ImageView addExpense;

    TextView year;

    public ExpensesFragment() {

    }


    public static ExpensesFragment newInstance(String param1, String param2)
    {
        ExpensesFragment fragment = new ExpensesFragment();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        date= view.findViewById(R.id.date);
        year = view.findViewById(R.id.year);
        // Retrieve data from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String month = bundle.getString("selected_month", "");
            String selectedYear = bundle.getString("year", "");

            date.setText(month);
            year.setText(selectedYear);
        }


        addExpense = view.findViewById(R.id.add_btn);

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AddExpenseFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception or log the details
                }
            }
        });

        return view;

    }
}