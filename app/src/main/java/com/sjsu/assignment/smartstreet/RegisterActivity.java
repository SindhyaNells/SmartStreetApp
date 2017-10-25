package com.sjsu.assignment.smartstreet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText reg_email, reg_pwd,reg_confirm_pwd, reg_addr, reg_city, reg_zipcode;
    TextView register_title;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register_title=(TextView)findViewById(R.id.reg_title);
        reg_email = (EditText) findViewById(R.id.reg_email_edit_text);
        reg_pwd=(EditText)findViewById(R.id.reg_pwd_edit_text);
        reg_confirm_pwd=(EditText)findViewById(R.id.reg_confirm_pwd_edit_text);
        reg_addr = (EditText) findViewById(R.id.reg_addr_edit_text);
        reg_city= (EditText) findViewById(R.id.reg_city_edit_text);
        reg_zipcode=(EditText)findViewById(R.id.reg_company_zipcode_edit_text);
        register_btn = (Button) findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id=view.getId();
                switch(id)
                {
                    case R.id.register_btn:
                        //TODO: register code
                        break;
                }
            }
        });
    }
}
