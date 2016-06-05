package com.example.saviola44.taskmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.saviola44.taskmanager.Model.Task;

import java.util.ArrayList;

/**
 * Created by saviola44 on 2016-06-05.
 */
public class ParseTaskJSONImpl implements ParseTaskJSON{
    Context context;
    public ParseTaskJSONImpl(Context context) {
        this.context=context;
    }

    public void writeTasks(ArrayList<Task> tasks, String filePath){
        WriteTask task = new WriteTask(context, tasks, filePath);
        task.execute();
    }
    public void readTask(String filePath){
        ReadTask task = new ReadTask(context, filePath);
        task.execute();
    }



}
