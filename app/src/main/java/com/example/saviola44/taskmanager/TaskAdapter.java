package com.example.saviola44.taskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.saviola44.taskmanager.Model.Task;

/**
 * Created by saviola44 on 03.06.16.
 */
public class TaskAdapter extends ArrayAdapter implements DeleteDialog.DeleteTask{
    ArrayList<Task> tasks;
    AppCompatActivity context;
    DeleteDialog dialog;
    public TaskAdapter(AppCompatActivity context, int resource, ArrayList<Task> tasks) {
        super(context, resource);
        this.tasks = tasks;
        this.context = context;
        dialog = new DeleteDialog();
    }
    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }


    @Override
    public int getCount() {
        return tasks.size();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DataHandler handler;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.task_row_layout, parent, false);
            handler = new DataHandler();
            handler.taskIcon = (ImageView) row.findViewById(R.id.row_icon);
            handler.deleteTask = (ImageView) row.findViewById(R.id.delete_task);
            handler.time_end = (TextView) row.findViewById(R.id.row_time_end);
            handler.title = (TextView) row.findViewById(R.id.row_title);
            row.setTag(handler);
        }
        else{
            handler = (DataHandler) row.getTag();
        }
        Task task = (Task) getItem(position);
        if (task.getUrl_to_icon() != null && !task.getUrl_to_icon().equals("")) {
            handler.taskIcon.setImageURI(Uri.parse(task.getUrl_to_icon()));
        }
        if(task.getTime_end()!=0){
            SimpleDateFormat fromat = new SimpleDateFormat("yyyy:mm:dd hh:mm");
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(task.getTime_end());
            handler.time_end.setText(fromat.format(cal.getTime()));
        }
        handler.title.setText(task.getTitle());
        handler.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                dialog.setArguments(bundle);
                dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "DeleteTask");*/
                showDialog(position);
            }
        });
        return row;
    }

    @Override
    public void delete(int position) {
        tasks.remove(position);
    }


    static class DataHandler{
        ImageView taskIcon;
        ImageView deleteTask;
        TextView time_end;
        TextView title;
    }
    void showDialog(final int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.confirm_deletion);
        dialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Task t = Task.findById(Task.class, tasks.get(position).getId());
                if(t!=null){
                    t.delete();
                }
                tasks.remove(position);
                notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

}
