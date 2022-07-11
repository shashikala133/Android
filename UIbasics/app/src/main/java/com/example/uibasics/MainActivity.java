package com.example.uibasics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private TextView txtHello;
    private EditText editTxtName;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHello:
               // Toast.makeText(this, "Hello button clicked", Toast.LENGTH_SHORT).show();
                txtHello.setText("Hello"+editTxtName.getText().toString());
                break;
            case R.id.editTxtName:
                Toast.makeText(this, "Attempting to type something", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHello = findViewById(R.id.btnHello);
       btnHello.setOnClickListener(this);
       /*btnHello.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               Toast.makeText(MainActivity.this, "LONG PRESS", Toast.LENGTH_LONG).show();
               return true;
           }
       });
*/

       // EditText editTxtName = findViewById(R.id.editTxtName);
       // TextView txtHello =findViewById(R.id.txtHello); -- scope issue
        txtHello = findViewById(R.id.txtHello);

        editTxtName = findViewById(R.id.editTxtName);
        editTxtName.setOnClickListener(this);

    }
}