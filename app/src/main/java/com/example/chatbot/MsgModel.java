package com.example.chatbot;

public class MsgModel {
    // The cnt "String" this is use for Bot as id
    private String cnt;  //Than creating a constrator using getter_and_setter methods

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public MsgModel(String cnt) {
        this.cnt = cnt;
    }
}
// this model msg part which hold the data get fatched by retroapi