package me.vincenyanga.taskmanager;

import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.vincenyanga.taskmanager.adapters.TasksAdapter;
import me.vincenyanga.taskmanager.fragments.AddTaskFragment;
import me.vincenyanga.taskmanager.models.Task;

public class MainActivity extends AppCompatActivity implements AddTaskFragment.TaskCallbackListener {

    private ListView taskList;
    private FloatingActionButton addButton;


    private DatabaseReference tasksRef;
    private FirebaseListAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        taskList = (ListView) findViewById(R.id.task_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_btn);

        tasksRef = FirebaseDatabase.getInstance().getReference().child("tasks");
        adapter = new FirebaseListAdapter<Task>(MainActivity.this, Task.class, R.layout.task_list_item, tasksRef) {
            @Override
            protected void populateView(View view, Task task, int i) {
                TextView taskName = (TextView) view.findViewById(R.id.task_name);
                if (task.isDone()) {
                    // If the task is done (completed) we strike through it.
                    taskName.setPaintFlags(taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    taskName.setPaintFlags(taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
                taskName.setText(task.getName());
            }
        };
        taskList.setAdapter(adapter);
        taskList.setEmptyView(findViewById(R.id.placeholder_txt));

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Task task = adapter.getItem(position);
                task.setDone(!task.isDone());
                tasksRef.child(adapter.getRef(position).getKey()).updateChildren(task.toMap());
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskFragment.newInstance().show(getFragmentManager(),"new_task");
            }
        });
    }

    @Override
    public void onTaskAdded(Task task) {
        tasksRef.push().setValue(task.toMap());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }
}
