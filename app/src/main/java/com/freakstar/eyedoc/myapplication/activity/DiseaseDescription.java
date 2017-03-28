package com.freakstar.eyedoc.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freakstar.eyedoc.myapplication.R;

public class DiseaseDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_description);
        String symptom="1.\tRed eyes.\n" +
                "2.\tItching, which may be severe.\n" +
                "3.\tDry, cracked eyes.\n" +
                "4.\tBlurry eyesight.\n" +
                "5.\tSwelling, burning or tenderness.\n";
        TextView textView=(TextView)findViewById(R.id.symp_textview);
        textView.setText(symptom);
        TextView textView1=(TextView)findViewById(R.id.treat_textview);
        String treatment="1.\tTreatment\n" +
                "2.\treducing your exposure to irritants\n" +
                "3.\tavoiding allergens\n" +
                "emollients\n" +
                "topical corticosteroids\n" +
                "oral corticosteroids";
        textView1.setText(treatment);
        Button button=(Button)findViewById(R.id.location);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),NearbyClinics.class);
                startActivity(intent);
            }
        });
    }
}
