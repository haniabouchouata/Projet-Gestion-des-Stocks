package com.ensah.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    
    private String description;

    @NotBlank(message = "L'unité est obligatoire")
    private String unite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prixUnitaire;

    @NotNull(message = "La quantité est requise")
    private long quantite;

    // Relation avec Ecart
    /*@OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Ecart> ecarts;*/

    // Constructeurs
    public Produit() {}

    public Produit(String nom, String description, String unite, Double prixUnitaire, long quantite) {
        this.nom = nom;
        this.description = description;
        this.unite = unite;
        this.prixUnitaire = prixUnitaire;
        this.quantite = quantite;
    }

    // Getters et Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getUnite() { return unite; }

    public void setUnite(String unite) { this.unite = unite; }

    public Double getPrixUnitaire() { return prixUnitaire; }

    public void setPrixUnitaire(Double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public long getQuantite() { return quantite; }

    public void setQuantite(long quantite) { this.quantite = quantite; }

    /*public List<Ecart> getEcarts() { return ecarts; }

    public void setEcarts(List<Ecart> ecarts) { this.ecarts = ecarts; }*/
}
