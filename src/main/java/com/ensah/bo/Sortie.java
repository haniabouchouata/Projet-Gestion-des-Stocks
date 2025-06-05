package com.ensah.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Sortie {

    @Id
    private String id; // Format: R + 8 chiffres

    @NotNull(message = "La date de Commande est obligatoire")
    private LocalDate dateCommande;

    @NotNull(message = "Le produit est requis")
    private String produit;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private Integer quantite;

    @NotNull(message = "ce champ est requis")
    private String unite;

    @NotNull(message = "L'entrepôt est requis")
    private String entrepot;

    @NotNull(message = "La source est requise")
    private String client;

    private String remarque;

    // Constructeur par défaut
    public Sortie() {
    }

    // Constructeur avec tous les paramètres
    public Sortie(String id, LocalDate dateCommande, String produit, Integer quantite, 
                  String unite, String entrepot, String client, String remarque) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.produit = produit;
        this.quantite = quantite;
        this.unite = unite;
        this.entrepot = entrepot;
        this.client = client;
        this.remarque = remarque;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}