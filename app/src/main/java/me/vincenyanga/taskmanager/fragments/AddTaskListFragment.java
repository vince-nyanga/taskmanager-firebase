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
import me.vincenyanga.taskmanager.models.TaskList;

/**
 * Created by Vincent.
 */

public class AddTaskListFragment extends DialogFragment {

    EditText name;

    public AddTaskListFragment(){}

    public static  AddTaskListFragment  newInstance(){
        AddTaskListFragment fragment = new AddTaskListFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.new_task_list_layout,
                null);
        name = (EditText)dialogView.findViewById(R.id.task_list_name_et);
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Add task list");
        builder.setView(dialogView);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addTaskList();
            }
        });
        return builder.create();
    }

    private void addTaskList() {
        if(!TextUtils.isEmpty(name.getText().toString()) && getActivity() instanceof TasksListCallbackListener){
            TaskList taskList = new TaskList(name.getText().toString(),"Vince");
            ((TasksListCallbackListener)getActivity()).onTaskListAdded(taskList);
        }
    }

    public interface TasksListCallbackListener {
        void onTaskListAdded(TaskList taskList);
    }
}
