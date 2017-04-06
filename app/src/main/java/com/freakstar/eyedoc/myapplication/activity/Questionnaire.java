package com.freakstar.eyedoc.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.freakstar.eyedoc.myapplication.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Questionnaire extends AppCompatActivity {
    public static final String URL="http://76113b01.ngrok.io/getstr";
    public static final String KEY_DATA="data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Button button=(Button)findViewById(R.id.proceedFurther);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendData();
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Intent intent=new Intent(getApplicationContext(),DiseaseDescription.class);
                        startActivity(intent);
                    }
                }, 5000);*/
               Intent intent=new Intent(getApplicationContext(),DiseaseDescription.class);
                startActivity(intent);

            }
        });
    }
    public void sendData()
    {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        { @Override public Map getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");


            return headers;
        }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String string="1,0,0,1,1,0,0,0,0,0.6,0.1";
                params.put(KEY_DATA,string);


                return params;
            }

        };

       MyApplication.getInstance().addToRequestQueue(jsonObjReq);
    }
}
