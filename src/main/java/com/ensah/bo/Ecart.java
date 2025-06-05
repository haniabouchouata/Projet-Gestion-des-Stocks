package com.ensah.bo;

import jakarta.persistence.*;

@Entity
public class Ecart {

    @EmbeddedId
    private EcartId id;

    @ManyToOne
    @MapsId("inventaireId")
    @JoinColumn(name = "inventaire_id")
    private Inventaire inventaire;

    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    private long quantiteAvant;
    private long quantiteApres;
    private long difference;

    private String justification;

    // Constructors
    public Ecart() {}

    public Ecart(Inventaire inventaire, Produit produit, long quantiteAvant, long quantiteApres, String justification) {
        this.inventaire = inventaire;
        this.produit = produit;
        this.quantiteAvant = quantiteAvant;
        this.quantiteApres = quantiteApres;
        this.difference = quantiteApres - quantiteAvant;
        this.justification = justification;
        this.id = new EcartId(inventaire.getId(), produit.getId());
    }

    // Getters and Setters

    public EcartId getId() {
        return id;
    }

    public void setId(EcartId id) {
        this.id = id;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public long getQuantiteAvant() {
        return quantiteAvant;
    }

    public void setQuantiteAvant(long l) {
        this.quantiteAvant = l;
    }

    public long getQuantiteApres() {
        return quantiteApres;
    }

    public void setQuantiteApres(long l) {
        this.quantiteApres = l;
    }

    public long getDifference() {
        return difference;
    }

    public void setDifference(long l) {
        this.difference = l;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
