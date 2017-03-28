package com.freakstar.eyedoc.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freakstar.eyedoc.myapplication.R;
import com.freakstar.eyedoc.myapplication.model.DoctorAdapter;

public class NearbyClinics extends AppCompatActivity {
    public RecyclerView mRecyclerTransaction;
    private DoctorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_clinics);
        mRecyclerTransaction = (RecyclerView)findViewById(R.id.doctorsList);
        mAdapter = new DoctorAdapter(this);
        mRecyclerTransaction.setAdapter(mAdapter);
        mRecyclerTransaction.setLayoutManager(new LinearLayoutManager(this));
    }
}
