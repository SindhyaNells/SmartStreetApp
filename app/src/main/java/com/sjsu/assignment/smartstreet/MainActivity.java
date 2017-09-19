package com.sjsu.assignment.smartstreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayAdapter<String> menuAdapter;
    ArrayList<String> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateMenuList();

        menuAdapter = new ArrayAdapter<String>(this,R.layout.menu_list_item,R.id.text_menu_title,menuList);

        ListView listView=(ListView)findViewById(R.id.listView_menu);
        listView.setAdapter(menuAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        Intent about_intent=new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(about_intent);
                        break;
                    case 4:
                        Intent map_intent=new Intent(MainActivity.this,MapsActivity.class);
                        startActivity(map_intent);
                        break;
                    case 5:
                        Intent feedback_intent=new Intent(MainActivity.this,FeedbackActivity.class);
                        startActivity(feedback_intent);
                        break;

                }

            }
        });

    }

    public void populateMenuList(){

        menuList=new ArrayList<>();
        menuList.add("About");
        menuList.add("Interact");
        menuList.add("Photo/Video");
        menuList.add("Share");
        menuList.add("Nearby");
        menuList.add("Comments");

    }
}
