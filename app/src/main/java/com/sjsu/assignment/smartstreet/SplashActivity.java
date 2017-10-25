package com.sjsu.assignment.smartstreet;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView=(ImageView)findViewById(R.id.bg_image);
        imageView.setImageResource(R.mipmap.smart_street);
        imageView.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

        openHomeActivity();

    }

    public void openHomeActivity(){
        Thread background=new Thread() {

            public void run(){
                try{
                    sleep(5*1000);
                    Intent home_intent = new Intent(SplashActivity.this, HomeActivity.class);
                    home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(home_intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        };
        background.start();
    }
}
