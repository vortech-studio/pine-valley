package com.vortech.pinevalleyclub.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Window;

import com.google.android.material.button.MaterialButton;
import com.vortech.pinevalleyclub.R;
import com.vortech.pinevalleyclub.model.Club;
import com.vortech.pinevalleyclub.model.Service;
import com.vortech.pinevalleyclub.presenter.ClubsAdapter;
import com.vortech.pinevalleyclub.util.LanguageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Club> clubList;
    LanguageManager languageManager;
    MaterialButton languageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.scaffoldBackground)));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#2FC07F\">" + getString(R.string.clubs) + "</font>"));
        setContentView(R.layout.activity_home);

        languageManager = new LanguageManager(this);

        languageButton = findViewById(R.id.btnLanguage);
        languageButton.setOnClickListener(view -> {
            showChangeLanguageDialog();
        });

        recyclerView = findViewById(R.id.recyclerView);
        initializeData();
        initializeRecyclerView();
    }

    private void initializeData() {
        clubList = new ArrayList<>();
        clubList.add(new Club("The Cascades", "Hurghada", "Egypt", R.string.the_cascades_desc,
                "26.853961, 33.9564042", "+20 65 3562 685", "1250", "18",
                R.drawable.royal_county_down_img, "4.6", Arrays.asList(new Service(
                getResources().getString(R.string.swimming),
                R.drawable.baseline_pool_24))));
        clubList.add(new Club("Heritage Golf Club", "Bel Ombre", "Mauritius", R.string.heritage_golf_club_desc,
                "-20.503102,57.407967", "+230 266 9777", "6969", "9",
                R.drawable.heritage_golf_club_img, "5.0", Arrays.asList(new Service(
                getResources().getString(R.string.swimming),
                R.drawable.baseline_pool_24), new Service(
                getResources().getString(R.string.library),
                R.drawable.baseline_library_books_24),new Service(
                getResources().getString(R.string.cycle),
                R.drawable.baseline_directions_bike_24))));
        clubList.add(new Club("Le Touessrok", "Ile Aux Cerfs", "Mauritius", R.string.le_touessorok_desc,
                "-20.271134,57.802549", "+230 402 7400", "4200", "18",
                R.drawable.le_touessrok, "4.4", Arrays.asList(new Service(
                getResources().getString(R.string.restaurant),
                R.drawable.baseline_restaurant_menu_24), new Service(
                getResources().getString(R.string.gym),
                R.drawable.baseline_fitness_center_24),new Service(
                getResources().getString(R.string.hike),
                R.drawable.baseline_hiking_24))));
        clubList.add(new Club("Mazagan Golf Club", "El Jadida", "Morocco", R.string.mazagan_golf_club_desc,
                "33.2645589,-8.4738499", "+212 5 2338 8000", "5137", "18",
                R.drawable.mazagan_golf_club_img, "4.5", Arrays.asList(new Service(
                getResources().getString(R.string.bar),
                R.drawable.baseline_sports_bar_24), new Service(
                getResources().getString(R.string.gym),
                R.drawable.baseline_fitness_center_24),new Service(
                getResources().getString(R.string.library),
                R.drawable.baseline_library_books_24))));
        clubList.add(new Club("Windhoek Golf Club", "Windhoek", "Namibia", R.string.windhoek_golf_club,
                "-22.6179738,17.0736775", "+26461258498", "4200", "16",
                R.drawable.windhoek_golf_club_img, "4.5", Arrays.asList(new Service(
                getResources().getString(R.string.walk),
                R.drawable.baseline_park_24), new Service(
                getResources().getString(R.string.gym),
                R.drawable.baseline_fitness_center_24),new Service(
                getResources().getString(R.string.restaurant),
                R.drawable.baseline_restaurant_menu_24))));
        clubList.add(new Club("Lemuria Golf Course", "Praslin Island", "Seychelles", R.string.lemuria_golf_course_desc,
                "-4.2999341,55.6798502", "+248 4281 281", "3468", "18",
                R.drawable.lemuria_golf_course_img, "4.8", Arrays.asList(new Service(
                getResources().getString(R.string.restaurant),
                R.drawable.baseline_restaurant_menu_24), new Service(
                getResources().getString(R.string.gym),
                R.drawable.baseline_fitness_center_24),new Service(
                getResources().getString(R.string.hike),
                R.drawable.baseline_hiking_24))));
        clubList.add(new Club("Fancourt Links", "George", "South Africa", R.string.fancourt_linkk_desc,
                "-33.9675348,22.4015862", "+27448040844", "6924", "18",
                R.drawable.fancourt_linkk_img, "4.8", Arrays.asList(new Service(
                getResources().getString(R.string.hike),
                R.drawable.baseline_hiking_24), new Service(
                getResources().getString(R.string.gym),
                R.drawable.baseline_fitness_center_24),new Service(
                getResources().getString(R.string.restaurant),
                R.drawable.baseline_restaurant_menu_24))));
        clubList.add(new Club("Gary Player Golf Course", "Sun City", "South Africa", R.string.gary_player_golf_course_desc,
                "-25.3458577,27.0986054", "+27145571245", "7384", "18",
                R.drawable.gary_player_golf_course_img, "4.9", Arrays.asList(new Service(
                getResources().getString(R.string.restaurant),
                R.drawable.baseline_restaurant_menu_24), new Service(
                getResources().getString(R.string.gym),
                R.drawable.baseline_fitness_center_24),new Service(
                getResources().getString(R.string.hike),
                R.drawable.baseline_hiking_24))));
        clubList.add(new Club("Flamingo Golf Course", "Monastir", "Tunisia", R.string.flamingo_golf_course_desc,
                "35.7454948,10.79988", "+216 73 500 283", "1357", "18",
                R.drawable.flamingo_golf_course_img, "4.8", Arrays.asList(new Service(
                getResources().getString(R.string.restaurant),
                R.drawable.baseline_restaurant_menu_24), new Service(
                getResources().getString(R.string.bar),
                R.drawable.baseline_sports_bar_24),new Service(
                getResources().getString(R.string.cycle),
                R.drawable.baseline_directions_bike_24))));
        clubList.add(new Club("Leopard Rock", "Mutare", "Zimbabwe", R.string.leopard_rock_golf_desc,
                "-20.271134,57.802549", "+230 402 7400", "4200", "18",
                R.drawable.leopard_rock_golf_img, "4.2", Arrays.asList(new Service(
                getResources().getString(R.string.restaurant),
                R.drawable.baseline_restaurant_menu_24), new Service(
                getResources().getString(R.string.gym),
                R.drawable.baseline_fitness_center_24),new Service(
                getResources().getString(R.string.hike),
                R.drawable.baseline_hiking_24))));

    }

    private void initializeRecyclerView() {
        ClubsAdapter clubsAdapter = new ClubsAdapter(clubList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(clubsAdapter);
    }

    private  void showChangeLanguageDialog(){
        final  String [] languages = {"English", "Swahili"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Choose language...");
        builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    Log.d("Language", "en");
                    languageManager.updateResources("en");
                    recreate();
                }
                else if(i==1){
                    Log.d("Language", "sw");
                    languageManager.updateResources("sw");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }
}