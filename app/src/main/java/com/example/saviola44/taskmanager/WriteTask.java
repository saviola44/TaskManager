package com.example.saviola44.taskmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.saviola44.taskmanager.Model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by saviola44 on 2016-06-05.
 */
public class WriteTask extends AsyncTask<Void, Void, String> {
    ArrayList<Task> tasks;
    String filePath;
    Context context;

    public WriteTask(Context context, ArrayList tasks, String filePath) {
        this.context = context;
        this.tasks = tasks;
        this.filePath = filePath;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            JSONArray jsonArray = new JSONArray();
            for(int i=0; i< tasks.size(); i++){
                Task t = tasks.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", t.getId());
                jsonObject.put("title", t.getTitle());
                jsonObject.put("created", t.getCreated());
                jsonObject.put("time_end", t.getTime_end());
                jsonObject.put("description", "" + t.getDescription());
                jsonObject.put("url_to_icon", "" + t.getUrl_to_icon());
                jsonArray.put(jsonObject);
            }
            return jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s!=null && !s.equals("")){
            try {
                OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE));
                writer.write(s);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}