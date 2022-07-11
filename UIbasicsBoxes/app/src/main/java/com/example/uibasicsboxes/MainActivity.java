package com.example.uibasicsboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CheckBox checkBoxHarry,checkBoxMatrix,checkBoxJoker;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBoxHarry = findViewById(R.id.checkBoxHarry);
        checkBoxMatrix = findViewById(R.id.checkBoxMatrix);
        checkBoxJoker = findViewById(R.id.checkBoxJoker);

        progressBar = findViewById(R.id.progressBar);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    progressBar.incrementProgressBy(10);
                    SystemClock.sleep(500);
                }
            }
        });
        thread.start();
        progressBar.getProgress();

        if(checkBoxHarry.isChecked()){
            Toast.makeText(MainActivity.this, "You have watched HarryPotter,yayy!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "You need to watch Harry Potter!!", Toast.LENGTH_SHORT).show();
        }
        checkBoxHarry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(MainActivity.this, "You have watched HarryPotter,yayy!!", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(MainActivity.this, "You need to watch Harry Potter!!", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}