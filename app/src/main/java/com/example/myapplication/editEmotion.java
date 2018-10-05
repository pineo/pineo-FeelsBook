package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class editEmotion extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Emotion> array;
    String filename;
    int position;

    Button Save_Button, Delete_Button, Cancel;
    EditText Edit_Emotion, Edit_Descr, Edit_Time;
    TextView errorOut;

    Intent go_home,go_display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emotion);

        Save_Button = findViewById(R.id.save);
        Save_Button.setOnClickListener(this);
        Delete_Button = findViewById(R.id.delete);
        Delete_Button.setOnClickListener(this);

        Edit_Emotion = findViewById(R.id.editEmotion);
        Edit_Descr = findViewById(R.id.editDescr);
        Edit_Time = findViewById(R.id.editTime);

        Cancel = findViewById(R.id.cancel);
        Cancel.setOnClickListener(this);

        errorOut = findViewById(R.id.error);

        go_home = new Intent(editEmotion.this,MainActivity.class);
        go_display = new Intent(editEmotion.this,Display.class);


        filename = "emotionStorage";
        array =fileUpdate.loadState(this,filename,array);
        position = getIntent().getIntExtra("position",0);

        Edit_Emotion.setText(array.get(position).getEmotionalState());
        Edit_Descr.setText(array.get(position).getDiscription());
        Edit_Time.setText(array.get(position).gettimeStamp());

    }

    @Override
    public void onClick(View v){
        int viewId = v.getId();
        if (viewId==R.id.delete){
            array.remove(position);
            fileUpdate.saveState(this,filename,array);
            exit();
        } else if (viewId == R.id.save){
            ArrayList<String> errors = checkConstraints();
            String error_msg = "";
            if (errors.size() == 0){
                array.get(position).setEmotionalState(Edit_Emotion.getText().toString());
                array.get(position).setOpDescr(Edit_Descr.getText().toString());
                array.get(position).setDate(Edit_Time.getText().toString());
                fileUpdate.saveState(this,filename,array);
                exit();
            } else {
                error_msg = errors.get(0);
                for (int i=1;i<errors.size();i++){
                    error_msg = error_msg + "\n--------------------------------\n";
                    error_msg = error_msg + errors.get(i);
                }
                errorOut.setText(error_msg);
            }
        } else if (viewId == R.id.cancel) {
            exit();
        }
    }

    public ArrayList<String> checkConstraints(){
        ArrayList<String> errors = new ArrayList<>();
        String[] validEmotions = {"Happy","Sad","Surprise","Fear","Anger","Love"};
        if (!Arrays.asList(validEmotions).contains(Edit_Emotion.getText().toString())){
            errors.add("Only the emotions 'Happy', 'Sad', 'Surprise', 'Fear', 'Anger', and 'Love' are valid");
        }
        if (Edit_Descr.getText().toString().length()>100){
            errors.add("The maximum number of characters in the optional description is 100 and there are "+Edit_Descr.getText().toString().length()+" entered.");
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        format.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = format.parse(Edit_Time.getText().toString());

        } catch (ParseException e) {
            errors.add("Invalid Date. Please enter in the form:\nMM/DD/YYYY HH:MM:SS");
        }
        return errors;
    }

    public void exit(){
        if (getIntent().getStringExtra("path").equals("main") || array.size()==0) {
            startActivity(go_home);
        } else {
            startActivity(go_display);
        }
    }
}
