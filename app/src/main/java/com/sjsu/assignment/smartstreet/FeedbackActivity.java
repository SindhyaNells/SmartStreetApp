package com.sjsu.assignment.smartstreet;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.sjsu.assignment.smartstreet.utility.ConfigConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnSubmitFeedback;
    EditText editTextcomment;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(getResources().getString(R.string.feedback_title));
        }

        btnSubmitFeedback=(Button)findViewById(R.id.btn_submit_feedback);
        btnSubmitFeedback.setOnClickListener(this);
        editTextcomment=(EditText)findViewById(R.id.edit_text_comment);
        rating=(RatingBar)findViewById(R.id.user_rating);
    }


    public String getDataFromEditText(EditText editText){
        return editText.getText().toString();
    }

    public Float getRating(RatingBar rating){
        return rating.getRating();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==R.id.btn_submit_feedback){
            AddFeedbackTask addFeedbackTask=new AddFeedbackTask();
            addFeedbackTask.execute();
        }
    }


    public class AddFeedbackTask extends AsyncTask<Void,Void,Boolean> {

        private final String LOG_TAG=FeedbackActivity.class.getSimpleName();

        @Override
        protected Boolean doInBackground(Void... param) {

            HttpURLConnection urlConnection=null;

            StringBuilder sb=new StringBuilder();
            try {
                String base_url = ConfigConstant.BASE_URL;
                final String PATH_PARAM = ConfigConstant.FEEDBACK_ENDPOINT;


                Uri feedbackUri = Uri.parse(base_url).buildUpon().appendPath(PATH_PARAM).build();

                URL url = new URL(feedbackUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                try {

                    JSONObject donationObj = new JSONObject();
                    donationObj.put("comments", getDataFromEditText(editTextcomment));
                    donationObj.put("rating",getRating(rating));


                    OutputStreamWriter os = new OutputStreamWriter(urlConnection.getOutputStream());
                    os.write(donationObj.toString());
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
                new AlertDialog.Builder(FeedbackActivity.this).setTitle("Feedback")
                        .setMessage(getResources().getString(R.string.feedback_success_msg))
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                closeActivity();
                            }
                        })
                        .show();


            }else{
                new AlertDialog.Builder(FeedbackActivity.this).setTitle("Feedback")
                        .setMessage(getResources().getString(R.string.feedback_failure_msg))
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
            editTextcomment.setText("");
            rating.setRating(0);
        }
    }

}
