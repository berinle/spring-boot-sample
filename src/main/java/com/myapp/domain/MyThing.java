package com.myapp.domain;

public class MyThing {

    private final long id;
    private final String content;

    public MyThing(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}