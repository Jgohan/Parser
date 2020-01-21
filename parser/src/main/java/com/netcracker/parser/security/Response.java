package com.netcracker.parser.security;

public class Response {
    private String text;

    public Response(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
