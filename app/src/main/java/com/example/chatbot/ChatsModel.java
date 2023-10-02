package com.example.chatbot;

public class ChatsModel {
    private String message;
    private String sender;
/// used part""Generator created the methods .construct and getter_setter methods

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ChatsModel(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }
//this denote user ...... sender will tell us which type sender it`s is ,Aur Bot /user

  //  we will use muliple type of views
      // to send aur render the messgae data in layout "Display"
}
