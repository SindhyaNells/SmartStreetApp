package com.sjsu.assignment.smartstreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText login_email,login_pwd;
    Button login_btn;
    TextView forgot_pwd_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email=(EditText)findViewById(R.id.login_email_edit_text);
        login_pwd=(EditText)findViewById(R.id.login_pwd_edit_text);
        login_btn=(Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        forgot_pwd_btn=(TextView)findViewById(R.id.forgot_pwd_btn);
        forgot_pwd_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.login_btn:
                //TODO: login code
                break;
            case R.id.forgot_pwd_btn:

                break;

        }
    }
}
