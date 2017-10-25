package com.sjsu.assignment.smartstreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button register_btn,login_btn;
    View buttonLayout;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.buttonanim);
        a.reset();
        buttonLayout=findViewById(R.id.button_layout);
        buttonLayout.clearAnimation();
        buttonLayout.startAnimation(a);
        title=(TextView)findViewById(R.id.app_title);
        title.setText(R.string.app_title);
        register_btn=(Button)findViewById(R.id.home_register_btn);
        register_btn.setOnClickListener(this);
        login_btn=(Button)findViewById(R.id.home_login_btn);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();
        switch (id){
            case R.id.home_register_btn:
                Intent reg_intent=new Intent(this,RegisterActivity.class);
                startActivity(reg_intent);
                break;
            case R.id.home_login_btn:
                Intent login_intent=new Intent(this,LoginActivity.class);
                startActivity(login_intent);
                break;
        }
    }
}
