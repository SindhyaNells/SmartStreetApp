package com.sjsu.assignment.smartstreet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.sjsu.assignment.smartstreet.adapter.PhotoListAdapter;
import com.sjsu.assignment.smartstreet.data.ImageFileItem;

import java.io.File;
import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity {

    PhotoListAdapter photoListAdapter;
    ArrayList<ImageFileItem> imgList;
    GridView photosGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(getResources().getString(R.string.photo_title));
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        photosGridView = (GridView) findViewById(R.id.grid_view_photos);
        photoListAdapter = new PhotoListAdapter(this,R.layout.photos_grid_item,getImagesList());
        photosGridView.setAdapter(photoListAdapter);

        photosGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ImageFileItem imageFileItem=(ImageFileItem) parent.getItemAtPosition(position);

                Bitmap img = imageFileItem.getImgFile();

                Bitmap compressed_img=compressBitmap(img,100,ShareActivity.this);
                Intent intent = new Intent(ShareActivity.this, PhotoDetailActivity.class);
                intent.putExtra("photo", compressed_img);
                intent.putExtra("photo_location",imageFileItem.getFileLocation());

                startActivity(intent);
            }
        });
    }

    public static Bitmap compressBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    private ArrayList<ImageFileItem> getImagesList() {

        imgList=new ArrayList<>();

        String targetPath = (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SmartStreetAppPics")).getPath();

        //Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File directory = new File(targetPath);

        File[] img_files = directory.listFiles();

        for (File file : img_files){

            ImageFileItem imageFileItem=new ImageFileItem();
            Bitmap img_bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
            imageFileItem.setImgFile(img_bitmap);
            imageFileItem.setFileLocation(file.getAbsolutePath());
            imgList.add(imageFileItem);
        }

        return imgList;

    }
}
