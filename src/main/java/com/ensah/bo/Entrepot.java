package com.ensah.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Entrepot {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @NotBlank(message = "Le nom est obligatoire")
	 private String nom;

	 @NotBlank(message = "Le code est obligatoire")
	 private String code;

	 @NotBlank(message = "L'adresse est obligatoire")
	 private String adresse;
	 
	 public Entrepot() {}

	    // Constructeur avec tous les champs
	    public Entrepot(Long id, String nom, String code, String adresse) {
	        this.id = id;
	        this.nom = nom;
	        this.code = code;
	        this.adresse = adresse;
	    }

	    // Getters et Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getNom() {
	        return nom;
	    }

	    public void setNom(String nom) {
	        this.nom = nom;
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

	    public String getAdresse() {
	        return adresse;
	    }

	    public void setAdresse(String adresse) {
	        this.adresse = adresse;
	    }

}
