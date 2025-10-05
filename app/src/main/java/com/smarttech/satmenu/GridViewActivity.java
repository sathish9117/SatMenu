package com.smarttech.satmenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.smarttech.satmenu.multitouch.adapter.MyAdapter;
import com.smarttech.satmenu.module.base.ActivityBase;
import com.smarttech.satmenu.module.base.ApplicationHandler;
import com.smarttech.satmenu.module.base.Session;
import com.smarttech.satmenu.module.constants.AppConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.smarttech.satmenu.module.base.ApplicationHandler.IMAGES;

public class GridViewActivity extends ActivityBase implements
        OnItemClickListener {

    public int[] resImage;
    List<String> listGalleryImages;
    Bundle bundle;
    String catName;
    String[] planets;
    GridView gridView;
    Session session;
    File makeImageFolder;
    ApplicationHandler applicationHandler;
    Resources res;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_view_activity);
        session = Session.getInstance();
        res = getResources();
        applicationHandler = ApplicationHandler.getInstance();
        makeImageFolder = applicationHandler.getOrCreateFolder(Environment
                        .getExternalStorageDirectory().getAbsolutePath(),
                IMAGES.FrameImages);
        makeImageFolder = applicationHandler.getOrCreateFolder(
                makeImageFolder.getAbsolutePath(), IMAGES.Cache);


        listGalleryImages = new ArrayList<String>();
        catName = session.getCategoriesName();

        //	planets = res.getStringArray(R.array.Birthday);


        /*for (String string : getcati()) {
            listGalleryImages.add(string);
		}*/

        getcati();

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(context, resImage));
        gridView.setOnItemClickListener(this);



	/*this.interstitialAds = new InterstitialAd(this, getString(R.string.interstitial_ID));
        this.interstitialAds.setAdListener(this);
		interstitialAds.loadAd(adRequest);*/

    }

    private String[] getcati() {
        // TODO Auto-generated method stub

        if (catName.equalsIgnoreCase("Birthday")) {
            resImage = AppConstants.BIRTHDAY;

        }
        if (catName.equalsIgnoreCase("Christmas")) {
            resImage = AppConstants.CHRISHMASH;
        }
        if (catName.equalsIgnoreCase("Flower And Frame")) {
            resImage = AppConstants.FLOWER_AND_FRAME;
        }
        if (catName.equalsIgnoreCase("Heart and Love")) {
            resImage = AppConstants.HEART_AND_LOVE;
        }
        if (catName.equalsIgnoreCase("Kid")) {
            resImage = AppConstants.kID;
        }
        if (catName.equalsIgnoreCase("Simple")) {
            resImage = AppConstants.SIMPLE;
        }
        if (catName.equalsIgnoreCase("Wedding")) {
            resImage = AppConstants.WEDDING;
        }

        if (catName.equalsIgnoreCase("<category_name>")) {
            resImage = AppConstants.SAMPLE;
        }
        return planets;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        Intent intentGalleryViewActivity = new Intent(getApplicationContext(),
                MultiTouch.class);

        //parent.getItemAtPosition(position);
        //session.setImageUrl(parent.getItemAtPosition(position).toString());
        session.setImageResId(position);
        intentGalleryViewActivity.putExtra("SELECTEDCAT", resImage);

        intentGalleryViewActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentGalleryViewActivity);
        overridePendingTransition(R.anim.in, R.anim.out);

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent intent = new
                Intent(getApplicationContext(), ListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.rightin, R.anim.rightout);
        finish();
        //super.onBackPressed();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
