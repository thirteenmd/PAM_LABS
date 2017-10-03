package com.example.andreea.lab_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class PictureView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        String fileString = getIntent().getStringExtra("photoPath");
        imageView.setImageBitmap(BitmapFactory.decodeFile(fileString));
    }
}
