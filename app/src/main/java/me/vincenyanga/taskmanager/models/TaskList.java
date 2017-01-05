package me.vincenyanga.taskmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent.
 */

@IgnoreExtraProperties
public class TaskList {

    private String name;
    private String owner;

    public TaskList(){}

    public TaskList(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name",this.name);
        map.put("owner",this.owner);
        return map;
    }
}
