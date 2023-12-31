package com.example.piggybankapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
     int clickedPosition = -1;

    //private List<Map<String, String>> expensesList = new ArrayList<>();
    private MyAdapter adapter; // Replace YourAdapter with your actual adapter class
    private List<ExpenseModel> expensesList = new ArrayList<>();


    public ExpensesFragment() {

    }



    // Inside the updateData method in ExpensesFragment
    public void updateData(String expenseName, String amount) {
        ExpenseModel newExpense = new ExpenseModel(expenseName, amount);
        expensesList.add(newExpense);

        Log.d("expensesListcontent", "expensesList in update data = " + expensesList);

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
    // Inside the updateSpentAmount method in ExpensesFragment
    // Inside the updateSpentAmount method in ExpensesFragment
    public void updateSpentAmount(String spentAmount) {
        Log.d("spentAmountFragment", "update SpentAmount called successfully " );

        // Iterate through the expensesList to find the corresponding item
        for (ExpenseModel expense : expensesList) {
            // Add your logic to identify the corresponding item (e.g., by expenseName)
            // For now, assuming you have a method to identify the correct item
            if (expenseMatchesClickedItem(expense, clickedPosition)) {
                // Update the spentAmount of the corresponding item
                expense.setSpentAmount(spentAmount);

                // Notify the adapter that the data has changed
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    Log.d("spentAmountFragment", "Notify the adapter that the data has changed: done");
                }

                // Break the loop once the item is found
                break;
            }

        }
    }

    // Replace this method with your actual logic to identify the correct item
    // Inside ExpensesFragment class
    private boolean expenseMatchesClickedItem(ExpenseModel expense, int clickedPosition) {
        // Add your logic to match the expense with the clicked item
        // For now, comparing based on position
        return clickedPosition >= 0 && clickedPosition < expensesList.size() &&
                expense.equals(expensesList.get(clickedPosition));
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


        // Instantiate the adapter with the expensesList
        adapter = new MyAdapter(getContext(), expensesList);

        // Find the ListView by its ID and set the adapter
        ListView listView = view.findViewById(R.id.expenses_list);
        listView.setAdapter(adapter);

        // Declare an array to hold the clicked position
        final int[] clickedPosition = {-1};

// Update your item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Store the clicked position for later use
                clickedPosition[0] = position;

                // Handle item click here
                ExpenseModel clickedExpense = expensesList.get(position);
                Log.d("ItemClicked", "Clicked on: " + clickedExpense.getExpenseName());

                // Logic for what should happen when an item is clicked
                try
                {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_container, new AmountSpentFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

/*
        String[] expenseNameArray = {"Expense1", "Expense2", "Expense3"};
        String[] expenseAmountArray = {"$10", "$20", "$30"};*/

       /* // Instantiate the adapter
        adapter = new MyAdapter(getContext(), expenseNameArray, expenseAmountArray);

        // Find the ListView by its ID and set the adapter
        ListView listView = view.findViewById(R.id.expenses_list); // Make sure the ID matches the one in your layout
        listView.setAdapter(adapter);*/


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
                try
                {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                     fragmentTransaction.replace(R.id.fragment_container, new AddExpenseFragment(),"add_expense_fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    // Handle the exception or log the details
                }
            }
        });

        return view;

    }


    // MyAdapter for list view
 /*   class MyAdapter extends ArrayAdapter<String> {

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
    }*/

    // MyAdapter.java

    public class MyAdapter extends ArrayAdapter<ExpenseModel> {

        private Context context;
        private List<ExpenseModel> expensesList;

        MyAdapter(Context c, List<ExpenseModel> expensesList) {
            super(c, R.layout.row2, R.id.textView1, expensesList);
            this.context = c;
            this.expensesList = expensesList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View row = layoutInflater.inflate(R.layout.row2, parent, false);

            TextView expenseNameTextView = row.findViewById(R.id.textView1);
            TextView expenseAmountTextView = row.findViewById(R.id.textView2);
            ProgressBar progressBar = row.findViewById(R.id.progressBar2);

            // Get the ExpenseModel at the current position
            ExpenseModel currentExpense = expensesList.get(position);

            // Set data to views
            expenseNameTextView.setText(currentExpense.getExpenseName());
            expenseAmountTextView.setText("0/" + currentExpense.getAmount());

            // Convert spentAmount to an integer
            String spentAmountString = currentExpense.getSpentAmount();
            int amountSpent = TextUtils.isEmpty(spentAmountString) ? 0 : Integer.parseInt(spentAmountString);

            // Update the progress of the ProgressBar
            progressBar.setMax(Integer.parseInt(currentExpense.getAmount()));
            progressBar.setProgress(amountSpent);

            return row;
        }

        @Override
        public int getCount() {
            return expensesList.size();
        }

        @Nullable
        @Override
        public ExpenseModel getItem(int position) {
            return expensesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }


}