package com.vortech.pinevalleyclub.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.vortech.pinevalleyclub.R;

public class SplashActivity extends AppCompatActivity {

    TextView textView;
    LottieAnimationView lottieAnimationView;
    Animation scaleAnimation;
    Animation fadeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.textView);
        lottieAnimationView = findViewById(R.id.animationView);
        textView.setVisibility(View.INVISIBLE);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.golf);
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        //Play Sound
        new Handler().postDelayed(() -> {
            mp.start();
        }, 1000);

        //Scale Text
        new Handler().postDelayed(() -> {
            textView.setVisibility(View.VISIBLE);
            textView.startAnimation(scaleAnimation);
        }, 1400);

        //Translate Text and fade out golfer
        new Handler().postDelayed(() -> {
            moveViewToScreenCenter(textView);
            lottieAnimationView.startAnimation(fadeAnimation);
        }, 2400);

        //Remove golfer
        new Handler().postDelayed(() -> {
            lottieAnimationView.setVisibility(View.INVISIBLE);
        }, 3400);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, AuthenticationActivity.class);
            startActivity(i);
            finish();
        }, 6000);
    }

    private void moveViewToScreenCenter( View view )
    {
        ConstraintLayout root = (ConstraintLayout) findViewById( R.id.constraintLayout );
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int originalPos[] = new int[2];
        view.getLocationOnScreen( originalPos );

        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

        TranslateAnimation anim = new TranslateAnimation( 0, xDest - originalPos[0] , 0, yDest - originalPos[1] );
        anim.setDuration(1500);
        anim.setFillAfter( true );
        view.startAnimation(anim);
    }
}