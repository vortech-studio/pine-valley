package com.vortech.pinevalleyclub.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.tabs.TabLayout;
import com.vortech.pinevalleyclub.R;
import com.vortech.pinevalleyclub.api.RetrofitClient;
import com.vortech.pinevalleyclub.model.LoginResponse;
import com.vortech.pinevalleyclub.presenter.FragmentAdapter;
import com.vortech.pinevalleyclub.storage.SharedPrefManager;
import com.vortech.pinevalleyclub.util.LanguageManager;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MaterialButton languageButton;
    FragmentAdapter fragmentAdapter;
    LanguageManager languageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewPager2);
        languageButton = findViewById(R.id.btnLanguage);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sign_in));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sign_up));

        languageManager = new LanguageManager(this);

        languageButton.setOnClickListener(view -> {
            showChangeLanguageDialog();
        });



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {


            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
                super.onPageSelected(position);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private  void showChangeLanguageDialog(){
        final  String [] languages = {"English", "Swahili"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthenticationActivity.this);
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
