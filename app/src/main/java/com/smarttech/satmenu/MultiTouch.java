package com.smarttech.satmenu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.smarttech.satemenus.SatelliteMenu;
import com.smarttech.satemenus.SatelliteMenuItem;
import com.smarttech.satmenu.module.base.ActivityBase;
import com.smarttech.satmenu.module.base.Session;
import com.smarttech.satmenu.module.constants.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MultiTouch extends ActivityBase implements OnTouchListener {

    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    int key = 0;
    ImageView view1, view2;
    Button saveButton, setWal, drower_open, drowre_close;
    FrameLayout framelayout;
    DrawerLayout drawerLayout;
    RelativeLayout relativelayout_buttone;
    Session session;
    // these matrices will be used to move and zoom image
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;
    private ProgressDialog progressDialog;
    private int[] resId;
    /**
     * Native Ad Callback
     */

    public static Bitmap doGreyscale(Bitmap src) {
        // constant factors
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        session = Session.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            resId = AppConstants.BIRTHDAY;
        } else {
            resId = extras.getIntArray("SELECTEDCAT");
        }

        initializetion();

    }

    private void initializetion() {
        // TODO Auto-generated method stub
        view1 = (ImageView) findViewById(R.id.imageView);
        view2 = (ImageView) findViewById(R.id.imageView2);
        view2.setImageResource(resId[session.getImageResId()]);

	/*
		adView = new AdView(this, AdSize.IAB_BANNER, "ca-app-pub-7751734045553296/2649239383");
	    AdRequest adRequest = new AdRequest();
	    adView.loadAd(adRequest);

	  this.interstitialAds = new InterstitialAd(this, "ca-app-pub-7751734045553296/4125972580");
		this.interstitialAds.setAdListener(this);
		interstitialAds.loadAd(adRequest);*/


        SatelliteMenu satelliteMenu = (SatelliteMenu) findViewById(R.id.menu);
        List<SatelliteMenuItem> item = new ArrayList<SatelliteMenuItem>();
        //item.add(new SatelliteMenuItem(1, R.drawable.rate));
        item.add(new SatelliteMenuItem(2, R.drawable.share));
        item.add(new SatelliteMenuItem(3, R.drawable.frame));
        item.add(new SatelliteMenuItem(4, R.drawable.wal));
        item.add(new SatelliteMenuItem(5, R.drawable.save_download));
        satelliteMenu.addItems(item);

        satelliteMenu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {

            @Override
            public void eventOccured(int id) {
                // TODO Auto-generated method stub
                switch (id) {
                    case 5:
                        save(false);
                        break;

                    case 4:
                        progressDialog = ProgressDialog.show(MultiTouch.this, "", "set wallpaper...");
                        new Thread() {

                            public void run() {

                                try {
                                    save(true);
                                    sleep(2000);

                                } catch (Exception e) {

                                    Log.e("tag", e.getMessage());

                                }

                                // dismiss the progress dialog

                                progressDialog.dismiss();

                            }

                        }.start();


                        break;

                    case 3:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity
                                Intent intent = new Intent(context, GridViewActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                overridePendingTransition(R.anim.rightin, R.anim.rightout);
                                finish();
                            }
                        }, 500);
                        break;
                    case 2:
                        share();
                        break;

                    default:
                        break;
                }

            }
        });

        //initImage();


        framelayout = (FrameLayout) findViewById(R.id.frame);
        view1.setImageBitmap(session.getBitmap());
        view2.setOnTouchListener(this);

    }

    @SuppressLint("NewApi")
    public boolean onTouch(View v, MotionEvent event) {
        // handle touch events here
        ImageView view = (ImageView) view1;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }

                    if (lastEvent != null && event.getPointerCount() == 2) {
                        newRot = rotation(event);
                        // matrix.postRotate(70);

                        float r = newRot - d;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[3];
                        float sx = values[0];
					/*
					 * float xc = (view.getWidth() / 2) * sx; float yc =
					 * (view.getHeight() / 2) * sx; matrix.postRotate(r, tx +
					 * xc, ty + yc);
					 */
                        float xc = (view.getWidth() / 2);
                        float yc = (view.getHeight() / 2);
					/* matrix.postRotate(r, tx + xc, ty + yc); */
                        matrix.postRotate(r, view.getMeasuredWidth() / 2,
                                view.getMeasuredHeight() / 2);

                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true;
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {

        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));

        double radians = Math.atan2(delta_y, delta_x);
        // Toast.makeText(getApplicationContext(),Math.toDegrees(radians)+"",
        // Toast.LENGTH_LONG).show();

        return (float) Math.toDegrees(radians);
    }

    private void save(Boolean boolean1) {
        // TODO Auto-generated method stub
        framelayout.setDrawingCacheEnabled(true);

        Bitmap bitmap = framelayout.getDrawingCache();

        //String root = Environment.getExternalStorageDirectory().toString();
        File newDir = new File(AppConstants.GT_FOLDER_PATH);
        newDir.mkdirs();
        Random gen = new Random();
        int n = 10000;
        n = gen.nextInt(n);
        String fotoname = n + ".jpg";
        File file = new File(newDir, fotoname);
        String s = file.getAbsolutePath();
        System.err.print("Path of saved image." + s);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

            if (boolean1) {
                //Context context = this.getBaseContext();
                //context.setWallpaper(bitmap);

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int height = metrics.heightPixels / 2;
                int width = metrics.widthPixels / 2;

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                try {
                    wallpaperManager.setBitmap(bitmap);
                    wallpaperManager.suggestDesiredDimensions(width, height);
                    Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (boolean1) {
                Toast.makeText(context, "Wallpaper Set...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "save image...", Toast.LENGTH_SHORT).show();
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            }

            out.flush();
            out.close();
        } catch (Exception e) {

        }

    }

    private void share() {
        // TODO Auto-generated method stub
        //String imageUrl = listGalleryImages.get(viewPager.getCurrentItem());


        framelayout.setDrawingCacheEnabled(true);

        Bitmap bitmap = framelayout.getDrawingCache();

        //String root = Environment.getExternalStorageDirectory().toString();
        File newDir = new File(AppConstants.GT_FOLDER_PATH);
        newDir.mkdirs();
        Random gen = new Random();
        int n = 10000;
        n = gen.nextInt(n);
        String fotoname = n + ".jpg";
        File file = new File(newDir, fotoname);
        String s = file.getAbsolutePath();
        System.err.print("Path of saved image." + s);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {

        }


        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("image/*");

        // For a file in shared storage. For data in private storage, use a
        // ContentProvider.
        // Uri uri = Uri.fromFile(getFileStreamPath(file1));

        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();

        Intent intent = new
                Intent(getApplicationContext(), GridViewActivity.class);
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
