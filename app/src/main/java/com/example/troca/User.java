package com.example.troca;

public class User {
    String idClient ,NomPrenomClient,emailClient,telClient;
    int mRes,idUser;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public User(String id, String Nom, String email, String tel, int mRes)
    {
        this.idClient=id;
        this.NomPrenomClient=Nom;
        this.emailClient=email;
        this.telClient=tel;
        this.mRes=mRes;
    }

    public String getNomPrenomClient() {
        return NomPrenomClient;
    }

    public void setNomPrenomClient(String nomPrenomClient) {
        NomPrenomClient = nomPrenomClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getTelClient() {
        return telClient;
    }

    public void setTelClient(String telClient) {
        this.telClient = telClient;
    }

    public int getmRes() {
        return mRes;
    }

    public void setmRes(int mRes) {
        this.mRes = mRes;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
}