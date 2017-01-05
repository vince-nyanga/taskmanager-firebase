package me.vincenyanga.taskmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import me.vincenyanga.taskmanager.fragments.AddTaskListFragment;
import me.vincenyanga.taskmanager.models.TaskList;

public class TasksListsActivity extends AppCompatActivity implements AddTaskListFragment
        .TasksListCallbackListener {

    private ListView taskLists;
    private FloatingActionButton addButton;


    private DatabaseReference taskListsRef;
    private FirebaseListAdapter<TaskList> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_lists);

        taskLists = (ListView) findViewById(R.id.task_lists);
        addButton = (FloatingActionButton) findViewById(R.id.add_btn);

        taskListsRef = FirebaseDatabase.getInstance().getReference().child("taskLists");
        adapter = new FirebaseListAdapter<TaskList>(TasksListsActivity.this, TaskList.class, R
                .layout
                .list_item, taskListsRef) {
            @Override
            protected void populateView(View view, TaskList taskList, int i) {
                TextView name = (TextView) view.findViewById(R.id.name);
                TextView owner = (TextView) view.findViewById(R.id.owner);

                name.setText(taskList.getName());
                owner.setText("Created by " + taskList.getOwner());
            }
        };

        taskLists.setAdapter(adapter);
        taskLists.setEmptyView(findViewById(R.id.placeholder_txt));

        taskLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TaskList taskList = adapter.getItem(position);
                String key = adapter.getRef(position).getKey();
                navigateToDetails(taskList, key);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskListFragment.newInstance().show(getFragmentManager(),"new_list");
            }
        });

    }

    private void navigateToDetails(TaskList taskList, String key) {
        Intent intent = new Intent(TasksListsActivity.this, TasksActivity.class);
        intent.putExtra("name",taskList.getName());
        intent.putExtra("key",key);
        startActivity(intent);
    }

    @Override
    public void onTaskListAdded(final TaskList taskList) {
        final DatabaseReference newListRef = taskListsRef.push();
        newListRef.setValue(taskList.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                navigateToDetails(taskList,newListRef.getKey());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.cleanup();
    }
}
