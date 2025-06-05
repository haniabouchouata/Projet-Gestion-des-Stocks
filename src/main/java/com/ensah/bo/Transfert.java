package com.ensah.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Transfert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ce champ est obligatoire")
    private LocalDate dateTransfert;

    @NotBlank(message = "ce champ est obligatoire")
    private String produit;

    @NotBlank(message = "ce champ est obligatoire")
    private String unite;

    @Positive(message = "la quantité doit être supérieure à zéro")
    private int quantite;

    @NotBlank(message = "ce champ est obligatoire")
    private String entrepotSource;

    @NotBlank(message = "ce champ est obligatoire")
    private String entrepotDestination;

    // 🔹 Constructeur par défaut
    public Transfert() {
    }

    // 🔹 Constructeur avec tous les champs
    public Transfert(LocalDate dateTransfert, String produit, String unite, int quantite,
                     String entrepotSource, String entrepotDestination) {
        this.dateTransfert = dateTransfert;
        this.produit = produit;
        this.unite = unite;
        this.quantite = quantite;
        this.entrepotSource = entrepotSource;
        this.entrepotDestination = entrepotDestination;
    }

    // 🔹 Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateTransfert() {
        return dateTransfert;
    }

    public void setDateTransfert(LocalDate dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getEntrepotSource() {
        return entrepotSource;
    }

    public void setEntrepotSource(String entrepotSource) {
        this.entrepotSource = entrepotSource;
    }

    public String getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(String entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }
}
