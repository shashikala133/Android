package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
      private ListView citiesList;
      private Spinner spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citiesList.findViewById(R.id.citiesList);
        spinner2 = findViewById(R.id.spinner2);
        ArrayList<String> students = new ArrayList<String>();
        students.add("shashi");
        students.add("siri");
        students.add("prathvi");
        students.add("keerthi");
        ArrayAdapter<String> studentadapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1,students
        );
        spinner2.setAdapter(studentadapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,students.get(i)+"selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> cities = new ArrayList<String>();
        cities.add("kundapura");
        cities.add("nitte");
        cities.add("udupi");
        cities.add("mangalore");
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(
                MainActivity.this, android.R.layout.simple_list_item_1,cities
        );
        citiesList.setAdapter(citiesAdapter);
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, cities.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
}