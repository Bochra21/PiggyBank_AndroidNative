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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    EditText salary;
    TextView date ;
    ImageView addExpense;

    TextView year;
    ListView listView ;
    List<ExpenseModel> expensesList = new ArrayList<>();

    int clickedPosition = -1;

    //private List<Map<String, String>> expensesList = new ArrayList<>();
    private MyAdapter adapter; // Replace YourAdapter with your actual adapter class



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


    public void updateSpentAmount(String spentAmount, int position)
    {
        Log.d("spentAmountFragment", "updateSpentAmount called successfully ");

        // Ensure position is valid
        if (position >= 0 && position < expensesList.size()) {
            // Get the ExpenseModel at the specified position
            ExpenseModel expense = expensesList.get(position);

            // Update the spentAmount of the corresponding item
            expense.setSpentAmount(spentAmount);

            // Notify the adapter that the data has changed
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                Log.d("spentAmountFragment", "Notify the adapter that the data has changed: done");
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


    // Method to fetch data from Firebase
    private void fetchDataFromFirebase()
    {
        String year = "2023";
        String month = "january";

        DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference()
                .child("years").child(year).child("months").child(month).child("expenses");

        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing data before updating with new data
                expensesList.clear();

                for (DataSnapshot expenseSnapshot : dataSnapshot.getChildren()) {
                    // Map the data from Firebase to your ExpenseModel
                    String expenseName = (String) expenseSnapshot.child("name").getValue();
                    String amount = (String) expenseSnapshot.child("amount_to_spend").getValue();
                    String amountSpent = (String) expenseSnapshot.child("amount_spent").getValue();

                    ExpenseModel expense = new ExpenseModel(expenseName, amount,amountSpent);
                    expensesList.add(expense);
                }

                // Notify the adapter that the data has changed
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors during data retrieval
                Log.e("ExpensesFragment", "Error fetching data from Firebase: " + databaseError.getMessage());
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        date= view.findViewById(R.id.date);
        year = view.findViewById(R.id.year);
        salary = view.findViewById(R.id.editTextText);

        // salary to firebase



        // Instantiate the adapter with the expensesList
        adapter = new MyAdapter(getContext(), expensesList);

        // Find the ListView by its ID and set the adapter
        ListView listView = view.findViewById(R.id.expenses_list);
        listView.setAdapter(adapter);

        // Declare an array to hold the clicked position
        final int[] clickedPosition = {-1};

        // Fetch data from Firebase when the fragment is created
        fetchDataFromFirebase();

// Update your item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Pass clicked position to AmountSpentFragment
                Bundle bundle = new Bundle();
                bundle.putInt("clicked_position", position);

                AmountSpentFragment amountSpentFragment = new AmountSpentFragment();
                amountSpentFragment.setArguments(bundle);

                // Replace current fragment with AmountSpentFragment
                try {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, amountSpentFragment, "fragment_amount_spent");
                    fragmentTransaction.addToBackStack(null); // Optional, for back navigation
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception or log the details
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