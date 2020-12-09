package com.example.troca;

public class Message {

    private String nickname;
    private String message ;
    private  int chatid ;
    private int userid;

    public Message(){

    }
    public Message(String nickname, String message , Integer chatid, int userid) {
        this.nickname = nickname;
        this.message = message;
        this.chatid = chatid;
        this.userid = userid;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
