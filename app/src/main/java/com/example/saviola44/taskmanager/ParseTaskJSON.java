package com.example.saviola44.taskmanager;

import com.example.saviola44.taskmanager.Model.Task;

import java.util.ArrayList;

/**
 * Created by saviola44 on 2016-06-05.
 */
public interface ParseTaskJSON {
    public void writeTasks(ArrayList<Task> tasks, String filePath);
    public void readTask(String filePath);
}
