package com.example.saviola44.taskmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import com.example.saviola44.taskmanager.Model.Task;

/**
 * Created by saviola44 on 02.06.16.
 */
public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {
    TextView headerLabel; //Dodaj zadanie lub edytuj zadanie
    EditText editTitle;
    EditText editDescription;
    EditText changeTimeEnd;
    Button loadIconBtn;
    Button saveBtn;
    ImageView changeTimeIV;
    ImageView icontask;
    Calendar timeEnd = Calendar.getInstance();
    Task task;
    Uri iconUri = null;

    static final int SELECT_PHOTO = 3;

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
        icontask = (ImageView) findViewById(R.id.iconTask);

        mode = getIntent().getIntExtra(TASK_MODE, 1);
        if(mode == ADD_TASK){
            task = new Task();
        }
        else if (mode == EDIT_TASK){
            task = getIntent().getExtras().getParcelable("task");
            if(task!= null){
                completeViewData(task);
            }
        }
        initializeLabels();


        loadIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        changeTimeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EditTaskActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                long created = cal.getTimeInMillis();
                String title;
                if(editTitle!=null && editDescription!=null && changeTimeEnd!=null){
                    if(editTitle.getText().toString()!= null && !editTitle.getText().toString().equals("")){
                        title = editTitle.getText().toString();
                        task.setCreated(created);
                        task.setTitle(title);
                        if(changeTimeEnd.getText().toString()!=null &&
                                !changeTimeEnd.getText().toString().equals("") && !changeTimeEnd.getText().toString().equals("0")){
                            task.setTime_end(timeEnd.getTimeInMillis());
                        }
                        if(iconUri!=null){
                            task.setUrl_to_icon(iconUri.toString());
                        }
                        Intent resulIntent = new Intent();
                        resulIntent.putExtra("task", task);
                        setResult(RESULT_OK, resulIntent);;
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), R.string.title_alert, Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Log.d("editTile", "null");
                    finish();
                }

            }
        });


    }

    void initializeLabels(){
        if(mode == 1){
            headerLabel.setText(R.string.add_task_label);
            saveBtn.setText(R.string.add_button_label);
        }
        else if(mode == 2){
            headerLabel.setText(R.string.edit_task_label);
            saveBtn.setText(R.string.save_button_label);
        }
    }
    void completeViewData(Task task){
        if(task.getTitle()!=null && !task.getTitle().equals("")){
            editTitle.setText(task.getTitle());
        }
        if(task.getDescription()!=null && !task.getDescription().equals("")){
            editDescription.setText(task.getDescription());
        }
        if(task.getTime_end()!=0 ){
            timeEnd.setTimeInMillis(task.getTime_end());
            SimpleDateFormat fromat = new SimpleDateFormat("yyyy:mm:dd hh:mm");
            String timeStr = fromat.format(timeEnd.getTime());
            changeTimeEnd.setText(timeStr);
        }
        if(task.getUrl_to_icon()!=null && !task.getUrl_to_icon().equals("")){
            iconUri = Uri.parse(task.getUrl_to_icon());
            icontask.setImageURI(iconUri);
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        timeEnd.set(Calendar.YEAR, year);
        timeEnd.set(Calendar.MONTH, monthOfYear);
        timeEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                EditTaskActivity.this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), "TimePikerDislog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        timeEnd.set(Calendar.HOUR_OF_DAY, hourOfDay);
        timeEnd.set(Calendar.MINUTE, minute);
        timeEnd.set(Calendar.SECOND, second);
        SimpleDateFormat fromat = new SimpleDateFormat("yyyy:mm:dd hh:mm");
        String timeStr = fromat.format(timeEnd.getTime());
        changeTimeEnd.setText(timeStr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PHOTO){
                iconUri = data.getData();
                icontask.setImageURI(iconUri);
            }
        }
    }
}
