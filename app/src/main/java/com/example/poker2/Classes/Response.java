package com.example.poker2.Classes;

public class Response {

    String name;
    Integer answer;

    public Response() {
    }

   public Response(String name, Integer answer) {
        this.name = name;
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}