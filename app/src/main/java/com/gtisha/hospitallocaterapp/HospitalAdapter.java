package com.gtisha.hospitallocaterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private List<Hospital> hospitalDataList;

    public HospitalAdapter(List<Hospital> hospitalList) {
        this.hospitalDataList = hospitalList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false);
        return new HospitalViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitalDataList.get(position);
        holder.hospitalName.setText(hospital.getName());
        holder.hospitalLocation.setText(hospital.getLocation());

        // Load the logo using Glide
        Glide.with(holder.hospitalLogo.getContext())
                .load(hospital.getLogoUrl())
                .into(holder.hospitalLogo);
    }


    @Override
    public int getItemCount() {
        return hospitalDataList.size();
    }

    public void updateList(List<Hospital> filteredList) {
        hospitalDataList = filteredList;
        notifyDataSetChanged();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView hospitalName, hospitalLocation;
        ImageView hospitalLogo;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            hospitalName = itemView.findViewById(R.id.hospitalName);
            hospitalLocation = itemView.findViewById(R.id.hospitalLocation);
            hospitalLogo = itemView.findViewById(R.id.hospitalLogo);
        }
    }
}
