package com.smarttech.satmenu;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarttech.satmenu.module.base.ActivityBase;
import com.smarttech.satmenu.module.constants.AppConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TakeImageActivity extends ActivityBase implements OnClickListener {

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int PICK_FROM_FILE = 3;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    File file;
    Button buttonTakeImage, buttonTakeImageCam;
    ImageView imageView;
    Uri mPhotoUri;
    Uri mImageUri;

    String[] appPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int PERMISSION_REQUEST_CODE = 14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_image_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkAndRequestPermission()) {
                initApp();
            }
        }else {
            initApp();
        }

    }

    private boolean checkAndRequestPermission() {
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String perm: appPermission){
            if (ContextCompat.checkSelfPermission(this,perm) != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(perm);
            }
        }

        if (!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE){
            HashMap<String, Integer> permissionResults = new HashMap<>();
            List<String> permissionNeeded = new ArrayList<>();
            int deniedCount = 0;

            for (int i = 0; i<grantResults.length; i++){
                if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i], grantResults[i]);
                    permissionNeeded.add(permissions[i]);
                    deniedCount++;
                }
            }

            if (deniedCount == 0){
                initApp();
            }else{
                if (!permissionNeeded.isEmpty()){
                    ActivityCompat.requestPermissions(this,permissionNeeded.toArray(new String[permissionNeeded.size()]),PERMISSION_REQUEST_CODE);
                }

//                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()){
//                    String permName = entry.getKey();
//                    int perResult = entry.getValue();
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,permName)) {
//                        alertDialog("", "This app needs permissions to work without and problems.",
//                                "Yes, Grant Permissions",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        checkAndRequestPermission();
//                                    }
//                                },
//                                "No, Exit App",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        finish();
//                                    }
//                                }, false);
//                    }
////                    }else{
////                        alertDialog("","You have denied some permissions. Allow all permission at [Setting] > [Permissions]",
////                                "Go to Settings",
////                                new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        dialog.dismiss();
////                                        Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
////                                                Uri.fromParts("package", getPackageName(), null));
////                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                        startActivity(i);
////                                        finish();
////                                    }
////                                },
////                                "No, Exit App",
////                                new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        dialog.dismiss();
////                                        finish();
////                                    }
////                                }, false);
////
////                    }
//
//                }
            }
        }

    }

    private void initApp() {
        file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/FrameImages");

        initializetion();
    }

    private void initializetion() {
        // TODO Auto-generated method stub
        buttonTakeImage = (Button) findViewById(R.id.buttonTakeImageActivity);
        buttonTakeImageCam = (Button) findViewById(R.id.buttonTakeImageCam);
        // imageView = (ImageView)
        // findViewById(R.id.imageViewTakeImageActivity);



        buttonTakeImage.setOnClickListener(this);
        buttonTakeImageCam.setOnClickListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.buttonTakeImageActivity:

                if (Environment.getExternalStorageState().equals("mounted")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(
                            Intent.createChooser(intent, "Select Picture:"),
                            PICK_FROM_FILE);
                }
                break;

            case R.id.buttonTakeImageCam:

                takePhotoIntent();

            default:
                break;
        }

    }

    private void takePhotoIntent() {


        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try {
            // place where to store camera taken picture
            photo = this.createTemporaryFile("picture", ".jpg");
            photo.delete();
        } catch (Exception e) {
            Log.v("tag", "Can't create file to take picture!");
            Toast.makeText(TakeImageActivity.this, "Please check SD card! Image shot is impossible!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageUri = FileProvider.getUriForFile(TakeImageActivity.this, BuildConfig.APPLICATION_ID + ".provider",photo);

        }else {
            mImageUri = Uri.fromFile(photo);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        //start camera intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public void grabImage(ImageView imageView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_FILE:

                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                Bitmap photo = getPreview(selectedImagePath);
                // imageView.setImageBitmap(photo);
                session.setBitmap(photo);

                Intent intent = new Intent(context, ListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in, R.anim.out);
                break;

            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    // previewCapturedImage();
                    this.getContentResolver().notifyChange(mImageUri, null);
                    ContentResolver cr = this.getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri);
                        session.setBitmap(bitmap);
                        Intent intent1 = new Intent(context, ListActivity.class);
                        startActivity(intent1);
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                        Log.d("tag", "Failed to load", e);
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // failed to capture image
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }
                break;


        }

    }

    public Bitmap getPreview(String fileName) {
        File image = new File(fileName);

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image.getPath(), bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / 64;
        // opts.inSampleSize = originalSize;
        return BitmapFactory.decodeFile(image.getPath());
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void onDestroy() {
        // Destroy the AdView.
        /*adView.destroy();*/

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
