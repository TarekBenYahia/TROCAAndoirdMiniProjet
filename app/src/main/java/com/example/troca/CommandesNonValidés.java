package com.example.troca;

public class CommandesNonValidés {
    String idCommande,idClient,date,lieu,idPro,note;
    
    public CommandesNonValidés (String idCommande, String idClient, String date, String lieu)
    {
        this.idCommande = idCommande;
        this.idClient = idClient;
        this.date = date;
        this.lieu = lieu;
    }
    public CommandesNonValidés (String idCommande, String idClient,String idPro, String date, String lieu,String note)
    {
        this.idCommande = idCommande;
        this.idClient = idClient;
        this.idPro = idPro;
        this.date = date;
        this.lieu = lieu;
        this.note = note;
    }


    public String getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getIdPro() {
        return idPro;
    }

    public void setIdPro(String idPro) {
        this.idPro = idPro;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
