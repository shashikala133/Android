package com.example.uichallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText editTextName,editTextTextEmail,editTextPassword,editTextPasswordRepeat;
    private Button btnRegister,btnPickImage;
    private TextView txtGender,txtCountry,txtAgreement,txtWarnName,txtWarnEmail,txtWarnPassword,txtWarnRepeat;
    private Spinner spinnerCountry;
    private RadioGroup rgGender;
    private CheckBox agreementCheck;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Yet to talk about",Toast.LENGTH_SHORT).show();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRegister();
            }
        });
    }
    private void initRegister(){
        Log.d(TAG,"initRegister:Started");

        if(validateData()){
            if(agreementCheck.isChecked()){
                showSnackbar();
            }else{
                Toast.makeText(MainActivity.this, "You need to agree to licence agreement", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  void showSnackbar(){
        Log.d(TAG,"showSnackbar:Started");
            txtWarnName.setVisibility(View.GONE);
            txtWarnEmail.setVisibility(View.GONE);
            txtWarnPassword.setVisibility(View.GONE);
            txtWarnRepeat.setVisibility(View.GONE);

            String name = editTextName.getText().toString();
            String Email = editTextTextEmail.getText().toString();
            String country = spinnerCountry.getSelectedItem().toString();
            String gender = "";
            switch(rgGender.getCheckedRadioButtonId()){
                case R.id.rbMale:
                    gender = "Male";
                    break;
                case R.id.rbFemale:
                    gender = "Female";
                    break;
                case R.id.rbOther:
                    gender = "other";
                    break;
                default:
                    gender= "unknown";
                    break;
            }
            String SnackText = "name: "+name + "\n" + "Email: "+Email + "\n" + "Country: "+country + "\n" + "Gender : " + gender;
            Log.d(TAG,"snackbar: Snack bar Text" + SnackText);

        Snackbar.make(parent,SnackText,Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       editTextName.setText("");
                       editTextTextEmail.setText("");
                       editTextPassword.setText("");
                       editTextPasswordRepeat.setText("");
                    }
                }).show();
    }
    private boolean validateData(){
        Log.d(TAG,"ValidateData:Started");
        if(editTextName.getText().toString().equals("")){
            txtWarnName.setVisibility(View.VISIBLE);
            txtWarnName.setText("Enter your name:");
            return false;
        }
        if(editTextTextEmail.getText().toString().equals("")){
            txtWarnEmail.setVisibility(View.VISIBLE);
            txtWarnEmail.setText("Enter your Email:");
            return false;
        }
        if(editTextPassword.getText().toString().equals(" ")){
            txtWarnPassword.setVisibility(View.VISIBLE);
            txtWarnPassword.setText("Enter your password:");
            return false;
        }
        if(editTextPasswordRepeat.getText().toString().equals(" ")){
            txtWarnRepeat.setVisibility(View.VISIBLE);
            txtWarnRepeat.setText("Re Enter your Password:");
            return false;
        }
        if(!editTextPassword.getText().toString().equals(editTextPasswordRepeat.getText().toString())){
           txtWarnRepeat.setVisibility(View.VISIBLE);
           txtWarnRepeat.setText("Password doesn't match");
           return false;
        }
     return true;
    }
    private void initViews(){
        Log.d(TAG,"initViews:Started");
        editTextName = findViewById(R.id.editTextName);
        editTextTextEmail = findViewById(R.id.editTextTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordRepeat = findViewById(R.id.editTextPasswordRepeat);

        btnPickImage = findViewById(R.id.btnPickImage);
        btnRegister = findViewById(R.id.btnRegister);

        txtGender = findViewById(R.id.txtGender);
        txtCountry = findViewById(R.id.txtCountry);
        txtAgreement = findViewById(R.id.txtAgreement);
        txtWarnEmail = findViewById(R.id.txtWarnEmail);
        txtWarnName = findViewById(R.id.txtWarnName);
        txtWarnPassword = findViewById(R.id.txtWarnPassword);
        txtWarnRepeat = findViewById(R.id.txtWarnRepeat);

        spinnerCountry = findViewById(R.id.spinnerCountry);
        rgGender = findViewById(R.id.rgGender);
        agreementCheck = findViewById(R.id.agreementCheck);
        parent = findViewById(R.id.parent);


    }
}