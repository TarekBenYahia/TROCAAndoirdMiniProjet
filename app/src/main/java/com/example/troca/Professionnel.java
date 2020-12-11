package com.example.troca;

public class Professionnel {
    String NomPrenomPro, emailPro,notePro;

    public Professionnel(String nomPrenomPro, String emailPro, String notePro) {
        this.NomPrenomPro = nomPrenomPro;
        this.emailPro = emailPro;
        this.notePro = notePro;
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
}
