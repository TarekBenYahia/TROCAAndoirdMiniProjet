package com.example.troca;

public class Professionnel {
    String idPro ,NomPrenomPro, emailPro,notePro,adressePro,idCategorie,CinPro;

    public Professionnel(String  idPro,String nomPrenomPro, String emailPro, String notePro, String adresse, String idCategorie, String CinPro) {
        this.idPro = idPro;
        this.NomPrenomPro = nomPrenomPro;
        this.emailPro = emailPro;
        this.notePro = notePro;
        this.adressePro = adresse;
        this.idCategorie = idCategorie;
        this.CinPro = CinPro;
    }

    public String getNomPrenomPro() {
        return NomPrenomPro;
    }

    public void setNomPrenomPro(String nomPrenomPro) {
        NomPrenomPro = nomPrenomPro;
    }

    public String getEmailPro() {
        return emailPro;
    }

    public void setEmailPro(String emailPro) {
        this.emailPro = emailPro;
    }

    public String getNotePro() {
        return notePro;
    }

    public void setNotePro(String notePro) {
        this.notePro = notePro;
    }

    public String getAdressePro() {
        return adressePro;
    }

    public void setAdressePro(String adresse) {
        this.adressePro = adresse;
    }

    public String getIdPro() {
        return idPro;
    }

    public void setIdPro(String idPro) {
        this.idPro = idPro;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getCinPro() {
        return CinPro;
    }

    public void setCinPro(String cinPro) {
        CinPro = cinPro;
    }
}
