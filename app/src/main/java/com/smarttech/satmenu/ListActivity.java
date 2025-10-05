package com.smarttech.satmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.smarttech.satmenu.module.base.ActivityBase;
import com.smarttech.satmenu.module.constants.AppConstants;
import com.smarttech.satmenu.multitouch.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends ActivityBase implements OnItemClickListener {

    ListAdapter listAdapter;
    List<String> listItem;
    ListView listView;
    TextView textCategories;

    private int position;
    /**
     * Native Ad Callback
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        initialization();

    }

    private void initialization() {
        // TODO Auto-generated method stub

		/*textCategories = (TextView) findViewById(R.id.main_title);
         Typeface face=Typeface.createFromAsset(getAssets(), "Satisfy-Regular.ttf");
		 textCategories.setTypeface(face);*/
		/*adView = new AdView(this, AdSize.IAB_BANNER,getString(R.string.Banner_ID));
	    AdRequest adRequest = new AdRequest();
	    adView.loadAd(adRequest);

	    this.interstitialAds = new InterstitialAd(this, getString(R.string.interstitial_ID));
		this.interstitialAds.setAdListener(this);
		interstitialAds.loadAd(adRequest);*/


        listView = (ListView) findViewById(R.id.listView_Categories);
        listItem = new ArrayList<String>();
        for (String i : resources.getStringArray(R.array.categories)) {
            listItem.add(i);
        }
        listAdapter = new ListAdapter(context, listItem);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        this.position = position;
            session.setCategoriesName(parent.getItemAtPosition(position).toString());
            Intent intent = new Intent(context, GridViewActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.in, R.anim.out);
        }



    //super.onBackPressed();

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();

        Intent intent = new
                Intent(getApplicationContext(), TakeImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.rightin, R.anim.rightout);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
