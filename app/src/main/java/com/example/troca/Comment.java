package com.example.troca;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable
{
    private int idCommentaire;
    private String Contenu;
    private Annonce complaint;
    private int nbrLike;
    private int  idClient;
private User user;

    public User getUser(String idClient) {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment(String contenu) {
        Contenu = contenu;
    }

    public Comment() {
    }

    public int getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getNbrLike() {
        return nbrLike;
    }

    public void setNbrLike(int nbrLike) {
        this.nbrLike = nbrLike;
    }

    public Annonce getComplaint() {
        return complaint;
    }

    public void setComplaint(Annonce complaint) {
        this.complaint = complaint;
    }
}
