package com.example.saviola44.taskmanager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.saviola44.taskmanager.Model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by saviola44 on 2016-06-05.
 */
public class ReadTask extends AsyncTask<Void, Void, String> {
    String filePath;
    Context context;

    public ReadTask(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            InputStream input = context.openFileInput(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line!=null){
                sb.append(line);
                line=reader.readLine();
            }
            readFile(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void readFile(String jsonStr){
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("id");
                Task t = Task.findById(Task.class, id);
                if(t==null){
                    t= new Task();
                }
                t.setTitle(jsonObject.getString("title"));
                t.setCreated(jsonObject.getLong("created"));
                t.setTime_end(jsonObject.getLong("time_end"));
                t.setDescription(jsonObject.getString("description"));
                t.setUrl_to_icon(jsonObject.getString("url_to_icon"));
                t.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
