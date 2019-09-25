package com.mezan.worldpopulation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {
    private ArrayList<DetailsObject> Details;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView KEY,VAL;

        public MyViewHolder(View view) {
            super(view);
            KEY = view.findViewById(R.id.key);
            VAL = view.findViewById(R.id.val);
        }
    }


    public DetailsAdapter(ArrayList<DetailsObject> detailsObjects) {
        this.Details = detailsObjects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detailscountry, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DetailsObject detailsObject = Details.get(position);
        holder.KEY.setText(detailsObject.getKeys());
        holder.VAL.setText(detailsObject.getVals());

    }

    @Override
    public int getItemCount() {
        return Details.size();
    }
}

