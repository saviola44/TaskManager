package com.example.saviola44.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.saviola44.taskmanager.Model.Task;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView taskList;
    Button addBtn;
    Spinner sortMethSpin;
    ArrayList<Task> tasks;
    List<String> compStr = new ArrayList<>(); //spinner
    TaskAdapter adapter;
    public static int compTag = 1;
    Comparator<Task> comparator;
    public static final String PREFS_NAME="taskmanagerprefs";
    SharedPreferences prefs;

    static {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Task.findById(Task.class, 1); //wywolanie aby ruszył SugarOrm do pracy po uruchomieniu
        sortMethSpin = (Spinner) findViewById(R.id.sorted_comp_spinner);
        taskList = (ListView) findViewById(R.id.task_list_id);
        addBtn = (Button) findViewById(R.id.add_btn);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        compTag = prefs.getInt("sort", 0);
        setComoparator();

        if(savedInstanceState!=null){
            tasks = savedInstanceState.getParcelableArrayList("tasks");

        }
        else {
            tasks = new ArrayList<>();
            List<Task> list = Task.listAll(Task.class);
            for(int i=0; i<list.size(); i++){
                tasks.add(list.get(i));
            }
            Collections.sort(tasks, comparator);
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
        sortMethSpin.setOnItemSelectedListener(this);

        compStr.add(getString(R.string.task_ending));
        compStr.add(getString(R.string.task_creating));
        compStr.add(getString(R.string.title_label));
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, compStr);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortMethSpin.setAdapter(spinAdapter);
        sortMethSpin.setSelection(compTag);

        //ParseTaskJSON parse = new ParseTaskJSONImpl(getApplicationContext());
        //parse.writeTasks(tasks, "elo");
        //parse.readTask("elo");//zapisuje w bazie danych
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode==EditTaskActivity.ADD_TASK){
                Task task = data.getParcelableExtra("task");

                tasks.add(task);
                Collections.sort(tasks, comparator);
                task.save();
                adapter.notifyDataSetChanged();
            }
            else if(requestCode == EditTaskActivity.EDIT_TASK){
                Task task = data.getParcelableExtra("task");
                int pos = findPosById(task);
                if(pos==-1){
                    Toast.makeText(getApplicationContext(), R.string.unexpected_error_label, Toast.LENGTH_LONG).show();
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
                    Collections.sort(tasks, comparator);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        compTag = position;
        setComoparator();
        Collections.sort(tasks, comparator);
        prefs.edit().putInt("sort", position).commit();

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void setComoparator(){
        switch(compTag){
            case 0:{
                comparator = new TimeEndComparator();
                break;
            }
            case 1: {
                comparator = new NewestTask();
                break;
            }
            case 2:{
                comparator = new AlphabeticalOrder();
                break;
            }
        }
    }
}
