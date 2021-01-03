package com.example.troca;

import com.google.gson.annotations.SerializedName;

public class Annonce {

    private String titreAnnonce, descriptionAnnonce, dateAnnonce;
    private String ressourceImg;
    private int idAnnonce;
    public Annonce(String title, String description, String dateAnnonce, String ressourceImg) {
        this.titreAnnonce = title;
        this.descriptionAnnonce = description;
        this.dateAnnonce = dateAnnonce;
        this.ressourceImg = ressourceImg;
    }
    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }



    public String getDateAnnonce() {
        return dateAnnonce;
    }

    public void setDateAnnonce(String dateAnnonce) {
        this.dateAnnonce = dateAnnonce;
    }

    public String getTitreAnnonce() {
        return titreAnnonce;
    }

    public void setTitreAnnonce(String title) {
        this.titreAnnonce = title;
    }

    public String getDescriptionAnnonce() {
        return descriptionAnnonce;
    }

    public void setDescriptionAnnonce(String description) {
        this.descriptionAnnonce = description;
    }

    public String getRessourceImg() {
        return ressourceImg;
    }

    public void setRessourceImg(String ressourceImg) {
        this.ressourceImg = ressourceImg;
    }
}