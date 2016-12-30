package me.vincenyanga.taskmanager.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.vincenyanga.taskmanager.R;
import me.vincenyanga.taskmanager.models.Task;

/**
 * Created by Vincent.
 */

public class TasksAdapter extends ArrayAdapter<Task> {

    private List<Task> tasks;
    private Context context;

    public TasksAdapter(Context context, @NonNull List<Task> tasks) {
        super(context, -1, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
        }

        TextView taskName = (TextView) convertView.findViewById(R.id.task_name);
        Task task = tasks.get(position);
        if (task.isDone()) {
            // If the task is done (completed) we strike through it.
            taskName.setPaintFlags(taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            taskName.setPaintFlags(taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        taskName.setText(task.getName());

        return convertView;
    }
}
