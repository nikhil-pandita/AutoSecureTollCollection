package com.example.nik.willwork;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SecurityQ extends AppCompatActivity {

    public static SharedPreferences prefs;
    int flag = 0;
    String question;
    String answer;
    TextView Squestion;
    TextView Sanswer;
    Button speakButton;
    String mobilenumnber;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_q);

        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        Squestion = (TextView)findViewById(R.id.Squestion);
        Sanswer = (TextView)findViewById(R.id.VoiceInput);
        speakButton = (Button) findViewById(R.id.btnSpeak);

        mobilenumnber = prefs.getString("phone", "");

//        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);



        Squestion.setText(prefs.getString("question", ""));

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSpeechInput();
                question = prefs.getString("question", "");
                answer = prefs.getString("answer", "");
            }
        });
    }

    public void Checking(){

        String check = Sanswer.getText().toString();
        if (check.equals(answer)){
            Toast.makeText(SecurityQ.this,
                    "User Verified", Toast.LENGTH_LONG).show();
            Intent i = new Intent (SecurityQ.this, MainScreen.class);
            startActivity(i);
        }
        else{
            Toast.makeText(SecurityQ.this,
                    "Incorrect Answer", Toast.LENGTH_LONG).show();
                flag++;
                if(flag==3){
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            SecurityQ.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Unauthorised User");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.cancel);

                    // Setting Dialog Message
                    alertDialog.setMessage("User Unauthorised! Car is shutting Down! Message sent to user!!!!");
                    alertDialog.show();

                    Intent intent=new Intent(getApplicationContext(),SecurityQ.class);
                    PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                    SmsManager sms=SmsManager.getDefault();
                    sms.sendTextMessage(mobilenumnber, null, "Warning! Unauthorised user in your vehicle!", pi,null);
                }
        }

    }

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Sanswer.setText(result.get(0));
                    String check = Sanswer.getText().toString();
                    Checking();
                }
            }
            break;
        }

    }
}
