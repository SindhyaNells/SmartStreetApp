package com.sjsu.assignment.smartstreet;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PhotoDetailActivity extends AppCompatActivity {

    ImageView img_view_pic;
    Bitmap bitmap;
    String file_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(getResources().getString(R.string.share_title));
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        bitmap = getIntent().getParcelableExtra("photo");
        file_loc=getIntent().getExtras().getString("photo_location");

        img_view_pic = (ImageView) findViewById(R.id.img_view_photo_detail);
        img_view_pic.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_photo_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.action_share){
            //shareCustomDialog();

            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("image/jpeg");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {""});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file_loc));
            startActivity(Intent.createChooser(emailIntent, "Sharing Options"));
        }

        return super.onOptionsItemSelected(item);
    }

    public void shareCustomDialog(){

        final AlertDialog.Builder alert_dialog=new AlertDialog.Builder(PhotoDetailActivity.this).setTitle("Share");
        LayoutInflater inflater=this.getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.custom_dialog_share,null);
        alert_dialog.setView(dialogView);
        final AlertDialog dialog=alert_dialog.create();
        dialog.show();

        ImageView mail_img=(ImageView)dialogView.findViewById(R.id.img_view_mail);
        ImageView fb_img=(ImageView)dialogView.findViewById(R.id.img_view_fb);

        mail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        fb_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}
