package com.example.piggybankapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesFragment extends Fragment
{

    TextView t1, t2;



    TextView date ;
    ImageView addExpense;

    TextView year;
    ListView listView ;

    private List<Map<String, String>> expensesList = new ArrayList<>();
    private MyAdapter adapter; // Replace YourAdapter with your actual adapter class



    public ExpensesFragment() {

    }


    public void updateData(String name, String amount) {
        // Create a new expense map
        Map<String, String> newExpense = new HashMap<>();
        newExpense.put("name", name);
        newExpense.put("amount", amount);

        // Add the new expense to your list
        expensesList.add(newExpense);

        // Update your adapter if you are using one
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        // Find the existing instance of ExpensesFragment using the consistent tag
        ExpensesFragment expensesFragment = (ExpensesFragment) requireActivity()
                .getSupportFragmentManager().findFragmentByTag("expenses_fragment");

        if (expensesFragment != null) {
            // Call the updateData method of ExpensesFragment
            expensesFragment.updateData(name, amount);
        }
    }


    public static ExpensesFragment newInstance(String param1, String param2)
    {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        date= view.findViewById(R.id.date);
        year = view.findViewById(R.id.year);

        // for test

        String[] expenseNameArray = {"Expense1", "Expense2", "Expense3"};
        String[] expenseAmountArray = {"$10", "$20", "$30"};

        // Instantiate the adapter
        adapter = new MyAdapter(getContext(), expenseNameArray, expenseAmountArray);

        // Find the ListView by its ID and set the adapter
        ListView listView = view.findViewById(R.id.expenses_list); // Make sure the ID matches the one in your layout
        listView.setAdapter(adapter);


        // Retrieve data from arguments
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            String month = bundle.getString("selected_month", "");
            String selectedYear = bundle.getString("year", "");

            date.setText(month);
            year.setText(selectedYear);
        }




        t1 = view.findViewById(R.id.textView);
        t2 =  view.findViewById(R.id.textView3);


       




        addExpense = view.findViewById(R.id.add_btn);

        addExpense.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AddExpenseFragment(),"add_expense_fragment");
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


    // MyAdapter for list view
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String expenseName[];
        String expenseAmount[];

        MyAdapter(Context c, String expenseName[], String expenseAmount[]) {
            super(c, R.layout.row, R.id.textView1, expenseName); // Use expenseName as the data source
            this.context = c;
            this.expenseName = expenseName;
            this.expenseAmount = expenseAmount;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View row = layoutInflater.inflate(R.layout.row2, parent, false);

            TextView expenseNameTextView = row.findViewById(R.id.textView1);
            TextView expenseAmountTextView = row.findViewById(R.id.textView2);


            expenseNameTextView.setText(expenseName[position]);
            expenseAmountTextView.setText(expenseAmount[position]);

            return row;
        }
    }



}