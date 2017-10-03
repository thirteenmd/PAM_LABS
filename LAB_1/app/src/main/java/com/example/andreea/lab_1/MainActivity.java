package com.example.andreea.lab_1;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uniqueId = 6435;
    private EditText editTextInput;
    static final int REQUEST_TAKE_PHOTO = 1;
    Intent takePictureIntent;
    private CameraManager objCameraManager;
    private String mCameraId;
    private Boolean isTorchOn = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        editTextInput = (EditText) findViewById(R.id.editText);

        findViewById(R.id.show_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notification.setSmallIcon(R.mipmap.ic_launcher);
                notification.setWhen(System.currentTimeMillis());
                notification.setContentTitle("Title");
                notification.setContentText("Notification Text");

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(uniqueId, notification.build());

            }
        });

        findViewById(R.id.search_on_google_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String query = editTextInput.getText().toString().replaceAll(" ", "+");
                    Uri uri = Uri.parse("https://www.google.com/search?q="+query);
                    Intent gSearchIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(gSearchIntent);
                }catch(Exception e){
                    Log.d("Google Search", e.toString());
                }
            }
        });

        findViewById(R.id.open_camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton frontCameraBtn = (RadioButton) findViewById(R.id.front_camera);
                if(frontCameraBtn.isChecked()){
                    takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        }catch (IOException e){
                            Log.i("Exception!", e.toString());
                        }
                        if(photoFile != null){
                            Uri photoURI = FileProvider.getUriForFile(MainActivity.this,"com.example.android.fileprovider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                        }
                    }
                }
                else {
                    takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                    "com.example.android.fileprovider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                        }
                    }
                }
            }
        });

        findViewById(R.id.flash_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                objCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                    mCameraId = objCameraManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                if (!isFlashAvailable) {
                    Log.i("Flashlight", "No Flashlight!!!");
                }else {
                    if (isTorchOn){
                        turnLight(false);
                        isTorchOn = false;
                    }else{
                        turnLight(true);
                        isTorchOn = true;
                    }
                }
            }
        });

    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent showPicture = new Intent(MainActivity.this, PictureView.class);
        showPicture.putExtra("photoPath", mCurrentPhotoPath);
        startActivity(showPicture);
    }

    public void turnLight(boolean camera) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                objCameraManager.setTorchMode(mCameraId, camera);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
