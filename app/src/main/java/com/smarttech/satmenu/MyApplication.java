package com.smarttech.satmenu;

import android.app.Application;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        /*if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}*/

        super.onCreate();
        //initImageLoader(getApplicationContext());
    }


}