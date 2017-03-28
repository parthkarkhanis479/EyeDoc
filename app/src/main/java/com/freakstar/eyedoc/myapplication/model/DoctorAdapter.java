package com.freakstar.eyedoc.myapplication.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.freakstar.eyedoc.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SATISH on 10/11/2015.
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    List<Doctors> mItems;
    Context context;
    public DoctorAdapter(Context context) {
        super();
        this.context=context;
        mItems = new ArrayList<Doctors>();
        Doctors species = new Doctors();
       species.name="Radiance Eye Clinic";
        species.address="Address: 104, Juhu Versova Link Road, Kapaswadi, Near Zaf Fitness Club, Andheri West, Mumbai, Maharashtra 400053";
        mItems.add(species);

        species = new Doctors();
        species.name="Kaya Eye Clinic";
        species.address="Address: 101, 1st Floor,Gold Crest, N.S. Road Number 10, Next to HSBC Bank, JVPD, Scheme, Juhu, Mumbai, Maharashtra 400049";
        mItems.add(species);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_doctors, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Doctors nature = mItems.get(i);
        holder.docname.setText(nature.name);
        holder.docaddress.setText(nature.address);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView docname;
        public TextView docaddress;


        public ViewHolder(View itemView) {
            super(itemView);

            docname = (TextView) itemView.findViewById(R.id.doc_name);
            docaddress = (TextView) itemView.findViewById(R.id.doc_address);

        }
    }
}
