package com.sjsu.assignment.smartstreet;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sjsu.assignment.smartstreet.utility.ConfigConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    EditText reg_email, reg_pwd,reg_confirm_pwd, reg_phone,reg_addr, reg_city, reg_zipcode;
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
        reg_phone=(EditText)findViewById(R.id.reg_phone_edit_text);
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
                        RegisterTask registerTask=new RegisterTask();
                        registerTask.execute();
                        break;
                }
            }
        });
    }

    public String getDataFromEditText(EditText editText){
        return editText.getText().toString();
    }

    public class RegisterTask extends AsyncTask<Void,Void,Boolean> {

        private final String LOG_TAG=RegisterActivity.class.getSimpleName();

        @Override
        protected Boolean doInBackground(Void... param) {

            HttpURLConnection urlConnection=null;

            StringBuilder sb=new StringBuilder();
            try {
                String base_url = ConfigConstant.BASE_URL;
                final String PATH_PARAM = ConfigConstant.REGISTER_ENDPOINT;


                Uri registerUri = Uri.parse(base_url).buildUpon().appendPath(PATH_PARAM).build();

                URL url = new URL(registerUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                try {

                    JSONObject userObj = new JSONObject();
                    userObj.put("email", getDataFromEditText(reg_email));
                    userObj.put("password",getDataFromEditText(reg_pwd));
                    userObj.put("phone",getDataFromEditText(reg_phone));
                    userObj.put("address",getDataFromEditText(reg_addr));
                    userObj.put("city",getDataFromEditText(reg_city));
                    userObj.put("zipcode",getDataFromEditText(reg_zipcode));


                    OutputStreamWriter os = new OutputStreamWriter(urlConnection.getOutputStream());
                    os.write(userObj.toString());
                    os.close();

                    int HttpResult =urlConnection.getResponseCode();
                    if(HttpResult ==HttpURLConnection.HTTP_CREATED){
                        return true;
                    }

                }catch (JSONException e){
                    Log.e(LOG_TAG,e.getMessage());
                }

            }catch (IOException e){
                Log.e(LOG_TAG,e.getMessage());
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean res) {
            if(res) {
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Register")
                        .setMessage(getResources().getString(R.string.register_success_msg))
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                closeActivity();
                            }
                        })
                        .show();


            }else{
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Register")
                        .setMessage(getResources().getString(R.string.register_failure_msg))
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        }

        public void closeActivity(){
            reg_email.setText("");
            reg_pwd.setText("");
            reg_confirm_pwd.setText("");
            reg_phone.setText("");
            reg_addr.setText("");
            reg_city.setText("");
            reg_zipcode.setText("");
        }
    }
}
