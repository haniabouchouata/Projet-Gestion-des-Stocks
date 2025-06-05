package com.ensah.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
@Entity
public class Achat {

    @Id
    private String id; // Format: R + 8 chiffres

    @NotNull(message = "La date de réception est obligatoire")
    private LocalDate dateAchat;

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
    private String source;

    // 🔹 Constructeur par défaut (obligatoire pour JPA)
    public Achat() {
    }

    // 🔹 Constructeur avec tous les champs
    public Achat(String id, LocalDate dateAchat, String produit, Integer quantite, String unite, String entrepot, String source) {
        this.id = id;
        this.dateAchat = dateAchat;
        this.produit = produit;
        this.quantite = quantite;
        this.unite = unite;
        this.entrepot = entrepot;
        this.source = source;
    }

    // 🔹 Getters et Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
