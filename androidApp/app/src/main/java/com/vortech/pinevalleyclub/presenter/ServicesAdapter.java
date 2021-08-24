package com.vortech.pinevalleyclub.presenter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vortech.pinevalleyclub.R;
import com.vortech.pinevalleyclub.model.Club;
import com.vortech.pinevalleyclub.model.Service;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {

    private static final String TAG = "ServicesAdapter";
    List<Service> servicesList;

    public ServicesAdapter(List<Service> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {

        Service service = servicesList.get(position);
        holder.serviceNameTextView.setText(service.getName());
        holder.serviceImageView.setImageResource(service.getImageSrc());
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    class ServicesViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "ServicesAdapter";

        ConstraintLayout expandableLayout;
        TextView serviceNameTextView;
        ImageView serviceImageView;

        public ServicesViewHolder(@NonNull final View itemView) {
            super(itemView);

            serviceNameTextView = itemView.findViewById(R.id.serviceTextView);
            serviceImageView = itemView.findViewById(R.id.serviceImageView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
        }
    }
}
