package com.example.saviola44.taskmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

/**
 * Created by Justyna on 2016-06-04.
 */
public class DeleteDialog extends DialogFragment {
    DeleteTask handler;
    interface DeleteTask{
        void delete(int position);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder createProjectAlert = new AlertDialog.Builder(getActivity());
        final int pos = getArguments().getInt("pos");
        createProjectAlert.setTitle("Usuń zadanie");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        createProjectAlert.setView(inflater.inflate(R.layout.delete_task_dialog_layout, null))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler = (DeleteTask) getActivity();
                        handler.delete(pos);
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return createProjectAlert.create();
    }
}
