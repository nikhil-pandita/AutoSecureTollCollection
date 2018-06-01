package com.example.nik.willwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences prefs;
    EditText nameEDT;
    EditText questionEDT;
    EditText answerEDT;
    EditText phoneEDT;
    Button SaveDetailsButton;
    Button AddGeoLocation;
    Button plswork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);


        phoneEDT = (EditText)findViewById(R.id.phonenumber);
        nameEDT = (EditText) findViewById(R.id.nameEDT);
        questionEDT = (EditText)findViewById(R.id.questionEDT);
        answerEDT = (EditText)findViewById(R.id.answerEDT);
        SaveDetailsButton = (Button)findViewById(R.id.saveBTN) ;
//        AddGeoLocation = (Button) findViewById(R.id.Adddgeolocation);


            nameEDT.setText(prefs.getString("name", ""));
            phoneEDT.setText(prefs.getString("phone", ""));
            questionEDT.setText(prefs.getString("question", ""));
            answerEDT.setText(prefs.getString("answer", ""));
//        SaveAnswer.setText(prefs.getString("answer", ""));

       /* AddGeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Geolocation.class);
                startActivity(i);
            }
        });*/

        SaveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putBoolean("firstTime", false).commit();
                prefs.edit().putString("name", nameEDT.getText().toString()).commit();
                prefs.edit().putString("phone", phoneEDT.getText().toString()).commit();
                prefs.edit().putString("question", questionEDT.getText().toString()).commit();
                prefs.edit().putString("answer", answerEDT.getText().toString()).commit();
                Toast.makeText(MainActivity.this,
                        "Details Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent (MainActivity.this, MainScreen.class);
                startActivity(i);
            }
        });
    }
}
