package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.*;

public class Display extends AppCompatActivity implements View.OnClickListener  {

    private ListView feeling_list;

    private ArrayList<Emotion> feeling_array;

    private String filename;

    private int pos_clicked;


    /**
     *Initializes the necessary buttons
     *
     * New_Feeling: button to create a new feeling
     * Edit_Feeling: button to edit a selected feeling
     *
     * feeling_array: an array of instance of class Emotion storing all Instances of an emotion record
     * filename: gets the file name from the MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Button New_Feeling = findViewById(R.id.new_feeling_button);
        New_Feeling.setOnClickListener(this);
        Button Edit_Feeling = findViewById(R.id.edit_feeling_button);
        Edit_Feeling.setOnClickListener(this);

        feeling_array = new ArrayList<>();

        feeling_list = findViewById(R.id.feeling_list_view);
        feeling_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        filename = MainActivity.filename;

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

    /**
     * Figures out which button was clicked, and submits the correct intent.
     * Ensures if the 'Edit Feeling' button was clicked, that there was also a position clicked
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.new_feeling_button) {
            Intent new_feeling = new Intent(Display.this, MainActivity.class);
            startActivity(new_feeling);
        } else if (viewId == R.id.edit_feeling_button && pos_clicked != -1){
            Intent edit_emotion = new Intent(Display.this,editEmotion.class);
            edit_emotion.putExtra("position",pos_clicked);
            edit_emotion.putExtra("path","display");
            startActivity(edit_emotion);
        }
    }

    /**
     * <p></p>Updates the feeling array with the new values and displays it on the list
     * Will first load the array from memory
     * Will then sort the array based on its date
     * Finally, uploads the saved array to memory
     * </p>
     * <p>It then adds each feeling in the array to another array of string objects for printing purposes
     * which will either display just the Emotion and Time Stamp if there is no description, or will
     * also display the emotion if an emotion was entered.
     * </p>
     * <p>Finally, it then prints each item in the feelings array to a listview via the adapter
     * </p>
     */
    public void update_array(){
        ArrayList<String> feelings = new ArrayList<>();

        feeling_array = fileUpdate.loadState(this,filename,feeling_array);
        feeling_array = fileUpdate.sortArray(feeling_array);
        fileUpdate.saveState(this,filename,feeling_array);

        for (Emotion temp: feeling_array){
            if(temp.getDiscription().length() < 1){
                feelings.add("Emotion: "+temp.getEmotionalState()+"\n\nTime Stamp: "+temp.gettimeStamp());
            } else {
                feelings.add("Emotion: "+temp.getEmotionalState()+"\n\nDescription: "+temp.getDiscription()+"\n\nTime Stamp: "+temp.gettimeStamp());
            }
        }

        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1,android.R.id.text1, feelings);
        feeling_list.setAdapter(adapter);
    }

}
