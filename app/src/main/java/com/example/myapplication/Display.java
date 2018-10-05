package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.*;

public class Display extends AppCompatActivity implements View.OnClickListener  {

    Button New_Feeling, Edit_Feeling;
    ListView feeling_list;
    ArrayList<Emotion> feeling_array;
    ArrayList<String> feelings;
    ListAdapter adapter;
    public Context context;
    private String filename;
    private int pos_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        //check storage for a pre-existing list, and if so use the list

        New_Feeling = findViewById(R.id.new_feeling_button);
        New_Feeling.setOnClickListener(this);

        Edit_Feeling = findViewById(R.id.edit_feeling_button);
        Edit_Feeling.setOnClickListener(this);

        feeling_array = new ArrayList<>();
        feelings = new ArrayList<>();

        feeling_list = findViewById(R.id.feeling_list_view);
        feeling_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        context = this;
        filename = "emotionStorage";

        pos_clicked = -1;

        update_array();

        feeling_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //https://stackoverflow.com/questions/33448363/how-to-get-text-of-selected-item-from-the-main-list-in-listview-android
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
               pos_clicked = position;
            }
        });

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Intent new_feeling = new Intent(Display.this, MainActivity.class);

        if (viewId == R.id.new_feeling_button) {
            startActivity(new_feeling);
        } else if (viewId == R.id.edit_feeling_button && pos_clicked != -1){
            Intent edit_emotion = new Intent(Display.this,editEmotion.class);
            edit_emotion.putExtra("position",pos_clicked);
            edit_emotion.putExtra("path","display");
            startActivity(edit_emotion);
            int i = 0;
        }
    }

    public void update_array(){
        feeling_array = fileUpdate.loadState(this,filename,feeling_array);
        feeling_array = fileUpdate.sortArray(feeling_array);
        fileUpdate.saveState(this,filename,feeling_array);
        for (Emotion temp: feeling_array){
            if(temp.getDiscription().length() < 1){
                feelings.add(temp.getEmotionalState()+"\n\nTime Stamp:"+temp.gettimeStamp());
            } else {
                feelings.add(temp.getEmotionalState()+"\n\nDescription: "+temp.getDiscription()+"\n\nTime Stamp:"+temp.gettimeStamp());
            }
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1,android.R.id.text1, feelings);
        feeling_list.setAdapter(adapter);
    }

}
