package com.example.syndicat;

public class House {
    private String id;  // Ajouter ce champ
    private String numero;
    private String etage;
    private String action;
    private double metrage;
    private double prix;
    private String imageUrl;

    public House() {
        // Nécessaire pour Firebase
    }

    public House(String etage, String numero, String action, double metrage, double prix, String imageUrl) {
        this.numero = numero;
        this.etage = etage;
        this.action = action;
        this.metrage = metrage;
        this.prix = prix;
        this.imageUrl = imageUrl;
    }

    // Getter et setter pour id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Autres getters et setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getMetrage() {
        return metrage;
    }

    public void setMetrage(double metrage) {
        this.metrage = metrage;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
