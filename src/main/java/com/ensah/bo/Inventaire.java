package com.ensah.bo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Inventaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /*@OneToMany(mappedBy = "inventaire", cascade = CascadeType.ALL)
    private List<Ecart> ecarts;*/

    private LocalDate dateInventaire;

    private String entrepot;

    private String effectuePar;

    private String validePar;

    private boolean valide = false;

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    // Constructeur sans arguments
    public Inventaire() {
    }

    // Constructeur avec tous les champs
    public Inventaire(LocalDate dateInventaire, String entrepot, String effectuePar, String validePar) {
        this.dateInventaire = dateInventaire;
        this.entrepot = entrepot;
        this.effectuePar = effectuePar;
        this.validePar = validePar;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateInventaire() {
        return dateInventaire;
    }

    public void setDateInventaire(LocalDate dateInventaire) {
        this.dateInventaire = dateInventaire;
    }

    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }

    public String getEffectuePar() {
        return effectuePar;
    }

    public void setEffectuePar(String effectuePar) {
        this.effectuePar = effectuePar;
    }

    public String getValidePar() {
        return validePar;
    }

    public void setValidePar(String validePar) {
        this.validePar = validePar;
    }
    
    /*public List<Ecart> getEcarts() { return ecarts; }

    public void setEcarts(List<Ecart> ecarts) { this.ecarts = ecarts; }*/

}
