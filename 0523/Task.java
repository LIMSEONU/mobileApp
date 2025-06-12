package com.example.todolistapp;

public class Task {
    private int id;
    private String title;
    private long time;

    public Task(int id, String title, long time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getTime() {
        return time;
    }
}
