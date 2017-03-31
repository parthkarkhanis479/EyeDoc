package com.freakstar.eyedoc.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.freakstar.eyedoc.myapplication.R;

public class Questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Button button=(Button)findViewById(R.id.proceedFurther);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Intent intent=new Intent(getApplicationContext(),DiseaseDescription.class);
                        startActivity(intent);
                    }
                }, 5000);
                /*Intent intent=new Intent(getApplicationContext(),DiseaseDescription.class);
                startActivity(intent);*/
            }
        });
    }
}
