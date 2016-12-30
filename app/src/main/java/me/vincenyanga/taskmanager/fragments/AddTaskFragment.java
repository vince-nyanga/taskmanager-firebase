package me.vincenyanga.taskmanager.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import me.vincenyanga.taskmanager.R;
import me.vincenyanga.taskmanager.models.Task;

/**
 * Created by Vincent.
 */

public class AddTaskFragment extends DialogFragment {

    private EditText taskName;

    public  AddTaskFragment(){}

    public static AddTaskFragment newInstance(){
        AddTaskFragment fragment =new AddTaskFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.new_task_layout,null);
        taskName = (EditText)dialogView.findViewById(R.id.task_name_et);
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Add task");
        builder.setView(dialogView);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addTask();
            }
        });
        return builder.create();
    }

    private void addTask() {
        String name = taskName.getText().toString();
        if(!TextUtils.isEmpty(name) && getActivity() instanceof  TaskCallbackListener){
            Task task =new Task(name);
            ((TaskCallbackListener)getActivity()).onTaskAdded(task);
        }
    }

    public interface TaskCallbackListener{
        void onTaskAdded(Task task);
    }
}
