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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vortech.pinevalleyclub.R;
import com.vortech.pinevalleyclub.model.Club;
import com.vortech.pinevalleyclub.model.Service;

import java.util.Arrays;
import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubViewHolder> {

    List<Club> clubsList;

    public ClubsAdapter(List<Club> clubsList) {
        this.clubsList = clubsList;
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.golf_club, parent,
                false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {

        Club club = clubsList.get(position);
        holder.clubNameTextView.setText(club.getName());
        holder.cityCountryTextView.setText(club.getCity() +", "+ club.getCountry());
        holder.descriptionTextView.setText(club.getDescription());
        holder.followersTextView.setText(club.getFollowers());
        holder.holesTextView.setText(club.getHoles() + " " + holder.clubNameTextView.getContext().getString(R.string.holes));
        holder.ratingTextView.setText(club.getRating());
        holder.clubBackgroundImageView.setImageResource(club.getImageSrc());

        boolean isExpanded = clubsList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        ServicesAdapter servicesAdapter = new ServicesAdapter(club.getServices());
        holder.servicesRecyclerView.setAdapter(servicesAdapter);

    }

    @Override
    public int getItemCount() {
        return clubsList.size();
    }

    class ClubViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout expandableLayout;
        TextView clubNameTextView, cityCountryTextView, descriptionTextView, followersTextView,
                holesTextView, ratingTextView;
        ImageView clubBackgroundImageView;
        ImageButton callButton, locationButton;
        RecyclerView servicesRecyclerView;

        public ClubViewHolder(@NonNull final View itemView) {
            super(itemView);

            clubNameTextView = itemView.findViewById(R.id.clubNameTextView);
            cityCountryTextView = itemView.findViewById(R.id.cityCountryTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            followersTextView = itemView.findViewById(R.id.followersText);
            holesTextView = itemView.findViewById(R.id.holesTextView);
            ratingTextView = itemView.findViewById(R.id.clubRatingTextView);
            clubBackgroundImageView = itemView.findViewById(R.id.clubBackgroundImageView);
            callButton = itemView.findViewById(R.id.callButton);
            locationButton = itemView.findViewById(R.id.locationButton);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            clubBackgroundImageView.setOnClickListener(view -> {
                Club club = clubsList.get(getAdapterPosition());

                club.setExpanded(!club.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });

            callButton.setOnClickListener(view -> {
                Club club = clubsList.get(getAdapterPosition());
                Uri number = Uri.parse("tel:" + club.getPhoneNumber());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                view.getContext().startActivity(callIntent);
            });

            locationButton.setOnClickListener(view -> {
                Club club = clubsList.get(getAdapterPosition());
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=:" + club.getLocation());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                view.getContext().startActivity(mapIntent);
            });

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            servicesRecyclerView = itemView.findViewById(R.id.servicesRecyclerView);
            servicesRecyclerView.setLayoutManager(layoutManager);
        }
    }
}
