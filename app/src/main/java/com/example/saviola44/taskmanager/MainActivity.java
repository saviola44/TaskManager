package com.example.saviola44.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView taskList;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList = (ListView) findViewById(R.id.task_list_id);
        addBtn = (Button) findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.TASK_MODE, EditTaskActivity.ADD_TASK);
                startActivityForResult(intent,EditTaskActivity.ADD_TASK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode==EditTaskActivity.ADD_TASK){

            }
            else if(resultCode == EditTaskActivity.EDIT_TASK){

            }
        }
    }
}
