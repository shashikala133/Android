package com.example.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onRegisterBtnClick(View view){
        TextView txtFirstname = findViewById(R.id.edtFirstName);
        TextView txtLastname = findViewById(R.id.edtLastName);
        TextView txtEmail = findViewById(R.id.edtEmail);
         EditText edtTextFirstName=findViewById(R.id.textFirstName);
         EditText edtTextLastName = findViewById(R.id.textLastName);
         EditText edtTextEmail = findViewById(R.id.textEmail);
        txtFirstname.setText("First name: "+edtTextFirstName.getText().toString());
        txtLastname.setText("Last name: "+edtTextLastName.getText().toString());
        txtEmail.setText("Email: "+edtTextEmail.getText().toString());
    }
}