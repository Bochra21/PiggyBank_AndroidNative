package com.example.piggybankapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import com.example.piggybankapp.ExpensesFragment; // adjust the package name accordingly


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    String amount[] = {"0 DT", "200 DT", "300 DT", "400 DT","0 DT", "200 DT", "300 DT", "400 DT","0 DT", "200 DT", "300 DT", "400 DT"};
    String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    ListView listView;

    TextView year;

    // TODO: Rename and change types of parameters

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        //  args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.listView);
        year = view.findViewById(R.id.year_text);

        MyAdapter adapter = new MyAdapter(requireContext(), months, amount);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



               //  Intent intent = new Intent(requireContext(), HomeActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("selected_month", months[position]);
                bundle.putString("year", year.getText().toString());

                // Remove this line, as it's unnecessary
                // intent.putExtras(bundle);
                ExpensesFragment expensesFragment = new ExpensesFragment();
                expensesFragment.setArguments(bundle);
                // Replace current fragment with ExpensesFragment
                try {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, expensesFragment);
                    fragmentTransaction.addToBackStack(null); // Optional, for back navigation
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception or log the details
                }

                /*

                // now put title and description to another activity
                intent.putExtra("title", months[position]);
                intent.putExtra("description", amount[position]);
                // also put your position
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
                */

            }
        });

        return view;
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];

        MyAdapter(Context c, String title[], String description[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View row = layoutInflater.inflate(R.layout.row, parent, false);

            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }
}
