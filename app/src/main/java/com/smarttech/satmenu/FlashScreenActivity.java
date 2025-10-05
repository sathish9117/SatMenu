package com.smarttech.satmenu;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import com.smarttech.satmenu.module.base.ActivityBase;


public class FlashScreenActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashscreen);
        TitanicTextView tv = findViewById(R.id.my_text_view);
        // set fancy typeface
        Typeface face = Typeface.createFromAsset(getAssets(), "Satisfy-Regular.ttf");
        tv.setTypeface(face);

        // start animation
        new Titanic().start(tv);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displayInterstitial();
            }
        }, 5000);

    }

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
            Intent i = new Intent(getBaseContext(), TakeImageActivity.class);
            startActivity(i);
            FlashScreenActivity.this.finish();

    }
}

