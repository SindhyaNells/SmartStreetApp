package com.sjsu.assignment.smartstreet;

import android.content.DialogInterface;
import android.content.Intent;
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
                LoginTask loginTask=new LoginTask();
                loginTask.execute();
                break;
            case R.id.forgot_pwd_btn:

                break;

        }
    }

    public String getDataFromEditText(EditText editText){
        return editText.getText().toString();
    }

    public class LoginTask extends AsyncTask<Void,Void,Boolean> {

        private final String LOG_TAG=LoginActivity.class.getSimpleName();

        @Override
        protected Boolean doInBackground(Void... param) {

            HttpURLConnection urlConnection=null;

            StringBuilder sb=new StringBuilder();
            try {
                String base_url = ConfigConstant.BASE_URL;
                final String PATH_PARAM = ConfigConstant.LOGIN_ENDPOINT;


                Uri loginUri = Uri.parse(base_url).buildUpon().appendPath(PATH_PARAM).build();

                URL url = new URL(loginUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                try {

                    JSONObject userObj = new JSONObject();
                    userObj.put("email", getDataFromEditText(login_email));
                    userObj.put("password",getDataFromEditText(login_pwd));

                    OutputStreamWriter os = new OutputStreamWriter(urlConnection.getOutputStream());
                    os.write(userObj.toString());
                    os.close();

                    int HttpResult = urlConnection.getResponseCode();
                    if(HttpResult == HttpURLConnection.HTTP_OK){
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

                openDashboardActivity();

            }else{
                new AlertDialog.Builder(LoginActivity.this).setTitle("Login")
                        .setMessage(getResources().getString(R.string.login_failure_msg))
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        }

        public void openDashboardActivity(){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }
}
