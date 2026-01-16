package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addCity;
    LinearLayout addConfirmation;
    Button deleteCity;
    EditText newCity;
    Button addCityConfirmation;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
//        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        addCity = findViewById(R.id.add_city);
        addConfirmation = findViewById(R.id.addConfirmation);
        deleteCity = findViewById(R.id.delete_city);
        deleteCity.setEnabled(false);
        newCity = findViewById(R.id.cityToAdd);
        addCityConfirmation = findViewById(R.id.confirm_button);
        newCity.setText("");
        newCity.setHint("Enter New City");
        selectedPosition = -1;

        // Set an OnClickListener on the button
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the hidden layout
                if (addConfirmation.getVisibility() == View.GONE) {
                    addConfirmation.setVisibility(View.VISIBLE); // Show the layout
                } else {
                    addConfirmation.setVisibility(View.GONE); // Hide the layout
                }
            }
        });

        addCityConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = newCity.getText().toString().trim();

                // If empty, do nothing (no Toast)
                if (city.isEmpty()) return;

                // Add city to list + refresh ListView
                dataList.add(city);
                cityAdapter.notifyDataSetChanged();

                // Clean up UI
                newCity.setText("");
                addConfirmation.setVisibility(View.GONE);

                // Optional: clear selection after list changes
                cityList.clearChoices();
                selectedPosition = -1;
                deleteCity.setEnabled(false);
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                cityList.setItemChecked(position, true);
                deleteCity.setEnabled(true);
            }
        });

        deleteCity.setOnClickListener(v -> {
            if (selectedPosition == -1) return;

            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();

            cityList.clearChoices();
            selectedPosition = -1;
            deleteCity.setEnabled(false);
        });
    }
}