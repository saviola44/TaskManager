package com.example.saviola44.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.saviola44.taskmanager.Model.Task;

public class MainActivity extends AppCompatActivity {
    ListView taskList;
    Button addBtn;
    ArrayList<Task> tasks;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Task.findById(Task.class, 1); //wywolanie aby ruszył SugarOrm do pracy po uruchomieniu
        taskList = (ListView) findViewById(R.id.task_list_id);
        addBtn = (Button) findViewById(R.id.add_btn);
        if(savedInstanceState!=null){
            tasks = savedInstanceState.getParcelableArrayList("tasks");
        }
        else {
            tasks = new ArrayList<>();
            List<Task> list = Task.listAll(Task.class);
            for(int i=0; i<list.size(); i++){
                tasks.add(list.get(i));
            }
        }
        adapter = new TaskAdapter(this, R.layout.task_row_layout, tasks);
        taskList.setAdapter(adapter);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.TASK_MODE, EditTaskActivity.ADD_TASK);
                startActivityForResult(intent,EditTaskActivity.ADD_TASK);
            }
        });

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = tasks.get(position);
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.TASK_MODE, EditTaskActivity.EDIT_TASK);
                intent.putExtra("task", task);
                startActivityForResult(intent, EditTaskActivity.EDIT_TASK);
            }
        });

        ParseTaskJSON parse = new ParseTaskJSONImpl(getApplicationContext());
        parse.writeTasks(tasks, "elo");
        parse.readTask("elo");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode==EditTaskActivity.ADD_TASK){
                Log.d("elo", "jrdtem");
                Task task = data.getParcelableExtra("task");
                Log.d("elo", "jrdtem  " + task.getTitle());

                tasks.add(task);
                task.save();
                adapter.notifyDataSetChanged();
            }
            else if(requestCode == EditTaskActivity.EDIT_TASK){
                Task task = data.getParcelableExtra("task");
                int pos = findPosById(task);
                if(pos==-1){
                    Toast.makeText(getApplicationContext(), "Wystąpił nieoczekiwany bład", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    tasks.set(pos, task);
                    Log.d("id task=", "" + task.getId());
                    Task t = Task.findById(Task.class, task.getId());
                    t.setTitle(task.getTitle());
                    t.setDescription(task.getDescription());
                    t.setTime_end(task.getTime_end());
                    t.setUrl_to_icon(task.getUrl_to_icon());
                    t.save();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public int findPosById(Task task){
        for(int i=0; i<tasks.size();i++){
            if(task.getId()==tasks.get(i).getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("tasks", tasks);
    }
}
