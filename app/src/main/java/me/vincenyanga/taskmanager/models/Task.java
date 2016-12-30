package me.vincenyanga.taskmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Task {

    private String name;
    private boolean done;

    public Task(){}

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * The Firebase realtime database only supports the following :
     *  String
     *  Long
     *  Double
     *  Boolean
     *  Map<String, Object>
     *  List<Object>
     *
     *  So we convert the Task object into a map before we save to database
     * */
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name",this.name);
        map.put("done", this.done);
        return map;
    }
}
