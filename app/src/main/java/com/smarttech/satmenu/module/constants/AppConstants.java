package com.smarttech.satmenu.module.constants;

import android.os.Environment;

import com.smarttech.satmenu.R;


public class AppConstants {


    public static final String STORE_IMAGES_IN_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FrameImages/Cache";
    public static final String GT_FOLDER_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FrameImages/";


    public static final int PHOTO_VIEW_ID = 1000;
    public static final String DEVELOPER_ID = "197409921";
    public static final String APP_ID = "201863953";

    public static final int[] BIRTHDAY = {R.drawable.birthday1, R.drawable.birthday2, R.drawable.birthday3,
            R.drawable.birthday4, R.drawable.birthday5, R.drawable.birthday6,
            R.drawable.birthday7, R.drawable.birthday8, R.drawable.birthday9, R.drawable.birthday10,};
    public static final int[] CHRISHMASH = {R.drawable.chrishmash1, R.drawable.chrishmash2, R.drawable.chrishmash3, R.drawable.chrishmash4, R.drawable.chrishmash4, R.drawable.chrishmash5, R.drawable.chrishmash6, R.drawable.chrishmash7, R.drawable.chrishmash8, R.drawable.chrishmash9, R.drawable.chrishmash10, R.drawable.chrishmash11};
    public static final int[] FLOWER_AND_FRAME = {R.drawable.flowerandframe1, R.drawable.flowerandframe2, R.drawable.flowerandframe3, R.drawable.flowerandframe4, R.drawable.flowerandframe5, R.drawable.flowerandframe6, R.drawable.flowerandframe7, R.drawable.flowerandframe8};
    public static final int[] HEART_AND_LOVE = {R.drawable.heartandlove1, R.drawable.heartandlove2, R.drawable.heartandlove3, R.drawable.heartandlove4, R.drawable.heartandlove5, R.drawable.heartandlove6, R.drawable.heartandlove7, R.drawable.heartandlove8, R.drawable.heartandlove8, R.drawable.heartandlove9, R.drawable.heartandlove10};
    public static final int[] kID = {R.drawable.kid1, R.drawable.kid2, R.drawable.kid3, R.drawable.kid4, R.drawable.kid5, R.drawable.kid6, R.drawable.kid7, R.drawable.kid8};
    public static final int[] SIMPLE = {R.drawable.simple1, R.drawable.simple2, R.drawable.simple3, R.drawable.simple4, R.drawable.simple5, R.drawable.simple6, R.drawable.simple7, R.drawable.simple8};
    public static final int[] WEDDING = {R.drawable.wedding1, R.drawable.wedding2, R.drawable.wedding3, R.drawable.wedding4, R.drawable.wedding5, R.drawable.wedding6, R.drawable.wedding7, R.drawable.wedding8};

    public static final int[] SAMPLE={R.drawable.simple1,R.drawable.simple2,};

    public static class Config {
        public static final boolean DEVELOPER_MODE = false;
    }

    public static class Extra {
        public static final String IMAGES = "com.photoframeeditor.universalimageloader.IMAGES";
        public static final String IMAGE_POSITION = "com.photoframeeditor.universalimageloader.IMAGE_POSITION";
    }


}
