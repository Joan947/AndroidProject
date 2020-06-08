package com.google.firebase.ml.md.java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.ml.md.R;

import java.util.ArrayList;
import java.util.Locale;

import javax.annotation.Nullable;

public class VoiceActivity extends AppCompatActivity {

    TextToSpeech tts;
    TextView textView;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        textView = findViewById(R.id.texttv);
        imageButton= findViewById(R.id.voicebtn);
         tts = new TextToSpeech(VoiceActivity.this, new TextToSpeech.OnInitListener() {
        @Override
         public void onInit(int i) {

          if (i == TextToSpeech.SUCCESS) {

              String text = textView.getText().toString();
              tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
          }
        }
        });
    imageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSpeechInput();
        }
    });

    }

   // @Override
    //protected void onResume() {
     //   super.onResume();
       // getSpeechInput();
    //}

    public void getSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now....");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result.get(0).toLowerCase().equals("item")) {
                startActivity(new Intent(this, ClassifierActivity.class));
            } else if (result.get(0).toLowerCase().equals("barcode")) {
                startActivity(new Intent(this, FinalBarcodeActivity.class));
            } else if (result.get(0).toLowerCase().equals("read")) {
                startActivity(new Intent(this, OcrCaptureActivity.class));
            }
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
   }

}