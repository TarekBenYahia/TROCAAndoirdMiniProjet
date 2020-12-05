package com.example.troca;

public class Annonce {
    private String titreAnnonce,descriptionAnnonce ,dateAnnonce;
    private int ressourceImg;

    public Annonce (String title, String description,String dateAnnonce,int ressourceImg){
        this.titreAnnonce=title;
        this.descriptionAnnonce=description;
        this.dateAnnonce=dateAnnonce;
        this.ressourceImg=ressourceImg;
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

    public int getRessourceImg() {
        return ressourceImg;
    }

    public void setRessourceImg(int ressourceImg) {
        this.ressourceImg = ressourceImg;
    }
}
