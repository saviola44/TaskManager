package com.example.saviola44.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by saviola44 on 02.06.16.
 */
public class EditTaskActivity extends AppCompatActivity {
    TextView headerLabel; //Dodaj zadanie lub edytuj zadanie
    EditText editTitle;
    EditText editDescription;
    EditText changeTimeEnd;
    Button loadIconBtn;
    Button saveBtn;
    ImageView changeTimeIV;

    //tagi wysylane w intencji na podst ktorych okreslamy w jakim trybie aktywnosc ma pracowac, czy edycji czy
    //dodawania zadania
    public static final int ADD_TASK = 1;
    public static final int EDIT_TASK = 2;
    public static final String TASK_MODE = "mode";
    int mode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_layout);
        headerLabel = (TextView) findViewById(R.id.header);
        editTitle = (EditText) findViewById(R.id.edit_title_ET);
        editDescription = (EditText) findViewById(R.id.descriptionET);
        changeTimeEnd = (EditText) findViewById(R.id.time_end_ET);
        loadIconBtn = (Button) findViewById(R.id.loadIcon);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        changeTimeIV = (ImageView) findViewById(R.id.change_time_end);

        mode = getIntent().getIntExtra(TASK_MODE, 1);
        initializeLabels();
        

    }

    public void initializeLabels(){
        if(mode == 1){
            headerLabel.setText(R.string.add_task_label);
            saveBtn.setText(R.string.add_button_label);
        }
        else if(mode == 2){
            headerLabel.setText(R.string.edit_task_label);
            saveBtn.setText(R.string.save_button_label);
        }
    }

}
