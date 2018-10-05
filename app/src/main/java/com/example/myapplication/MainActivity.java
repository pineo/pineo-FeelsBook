package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import java.io.Serializable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

    public Emotion happy, sad, anger, fear, surprise, love;

    public ImageButton Happy_Button, Sad_Button, Anger_Button, Surprise_Button, Fear_Button, Love_Button;

    public TextView HappyText, SadText, AngryText, SurpriseText, FearText, LoveText;

    public Button View_All_Entries, Edit_Last;

    public EditText Optional_Description;

    public Intent submitFeeling;

    public String op_descr;

    public ArrayList<String> feelings;

    public static String filename;

    ArrayList<Emotion> feeling_array;

    Context context;

    Bundle newEmotion;
    //public TextView number_of_character;

    /**
     * Initializes all buttons and array lists required for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes the feelings and submit buttons
        Happy_Button = findViewById(R.id.happybutton);
        Sad_Button = findViewById(R.id.sadbutton);
        Anger_Button = findViewById(R.id.angerbutton);
        Surprise_Button = findViewById(R.id.surprisebutton);
        Fear_Button = findViewById(R.id.fearbutton);
        Love_Button = findViewById(R.id.lovebutton);
        View_All_Entries = findViewById(R.id.view_entries);

        HappyText = findViewById(R.id.happytext);
        SadText = findViewById(R.id.sadtext);
        AngryText = findViewById(R.id.angertext);
        SurpriseText = findViewById(R.id.surprisetext);
        FearText = findViewById(R.id.feartext);
        LoveText = findViewById(R.id.lovetext);

        Optional_Description = findViewById(R.id.op_description);

        Happy_Button.setOnClickListener(this);
        Sad_Button.setOnClickListener(this);
        Anger_Button.setOnClickListener(this);
        Fear_Button.setOnClickListener(this);
        Surprise_Button.setOnClickListener(this);
        Love_Button.setOnClickListener(this);

        Edit_Last = findViewById(R.id.editLast);
        Edit_Last.setOnClickListener(this);

        context = this;

        View_All_Entries.setOnClickListener(this);

        submitFeeling = new Intent(MainActivity.this, Display.class);

        feeling_array = new ArrayList<>();

        newEmotion = new Bundle();

        feelings = new ArrayList<>();

        filename = "emotionStorage";

        feeling_array = fileUpdate.loadState(this,filename,feeling_array);

        countEmotion();
    }
    /**
     *Updates the array from the saved file to ensure accurate data
     */
    @Override
    protected void onResume() {
        super.onResume();
        feeling_array = fileUpdate.loadState(this,filename,feeling_array);
        countEmotion();
    }

    /**
     *
     * Determines which button was clicked and adds it to the array list
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        //Each instant where one of the feeling is clicked, will ensure the error message is clear, the feeling is set and feeling entered is true.
        if (viewId == R.id.happybutton) {
            add_entry("Happy");
            setHappy();
        } else if (viewId == R.id.sadbutton) {
            add_entry("Sad");
            setSad();
        } else if (viewId == R.id.angerbutton) {
            add_entry("Anger");
            setAngry();
        } else if (viewId == R.id.fearbutton) {
            add_entry("Fear");
            setFear();
        } else if (viewId == R.id.surprisebutton) {
            add_entry("Surprise");
            setSurprise();
        } else if (viewId == R.id.lovebutton) {
            add_entry("Love");
            setLove();
        } else if (viewId == R.id.view_entries) {
            if (feeling_array.size()>0){
                viewEntries();
            } else {
                Toast.makeText(context, "There are no emotions to view", Toast.LENGTH_SHORT).show();
            }

        } else if (viewId == R.id.editLast){
            Intent edit_emotion = new Intent(MainActivity.this,editEmotion.class);
            if (feeling_array.size()>0) {
                edit_emotion.putExtra("position", 0);
                edit_emotion.putExtra("path", "main");
                startActivity(edit_emotion);
            } else {
                Toast.makeText(context, "There are no emotions to edit.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Adds the instance of a feeling to an arraylist of type emotion, saving its value and updating
     * it in the program. It will then display a toast message to notify the user the it has been added.
     * If the description is over the 100 character limit, it will reject the addition and notify the user
     * that they have exceeded the bounds.
     *
     * @param feeling An instance of emotion to be added to an arraylist of type Emotion
     */
    public void add_entry(String feeling) {
        op_descr = Optional_Description.getText().toString().trim();
        if (op_descr.length() <= 100) {
            feeling_array.add(new Emotion(feeling,op_descr));
            feeling_array = fileUpdate.sortArray(feeling_array);
            fileUpdate.saveState(this,filename,feeling_array);
            Optional_Description.setText("");
            Toast.makeText(context, feeling + " Emotion Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Over The 100 Character Limit...\nYou currently have " + op_descr.length() + " Characters\n", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Sends the user to the Display activity to view all the emotions that have been entered
     */
    public void viewEntries(){
        fileUpdate.saveState(this,filename,feeling_array);
        startActivity(submitFeeling);
    }

    /**
     * Counts the particular occurence of a given emotion, and returns the count of that emotion
     * to the program to be displayed.
     *
     * @param state A string of a particular state to be found within an arraylist of type Emotion
     * @return Returns a count of the number of the emotional state in the arraylist
     */
    public int getCount(String state) {
        int count=0;
        for (Emotion temp: feeling_array){
            if(temp.getEmotionalState().equals(state)){
                count++;
            }
        }
        return count;
    }

    /**
     * Updates the count of happy emotions recorded
     */
    public void setHappy(){
        String happytext = "Happy\nCount: "+getCount("Happy");
        HappyText.setText(happytext);
    }

    /**
     * Updates the count of sad emotions recorded
     */
    public void setSad(){
        String sadtext = "Sad\nCount: "+getCount("Sad");
        SadText.setText(sadtext);
    }

    /**
     * Updates the count of angry emotions recorded
     */
    public void setAngry(){
        String angrytext = "Angry\nCount: "+getCount("Anger");
        AngryText.setText(angrytext);
    }

    /**
     * Updates the count of fear emotions recorded
     */
    public void setFear(){
        String feartext = "Fear\nCount: "+getCount("Fear");
        FearText.setText(feartext);
    }

    /**
     * Updates the count of surprise emotions recorded
     */
    public void setSurprise(){
        String surprisetext = "Surprise\nCount: "+getCount("Surprise");
        SurpriseText.setText(surprisetext);
    }

    /**
     * Updates the count of love emotions recorded
     */
    public void setLove(){
        String lovetext = "Love\nCount: "+getCount("Love");
        LoveText.setText(lovetext);
    }

    /**
     * Updates the count of all emotions recorded
     */
    public void countEmotion(){
        setHappy();
        setSad();
        setAngry();
        setSurprise();
        setLove();
        setFear();
    }

}