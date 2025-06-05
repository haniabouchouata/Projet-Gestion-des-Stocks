package com.ensah.bo;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livraison {

    @Id
    private String id;

    @NotNull(message = "La date de livraison est obligatoire")
    private LocalDate dateLivraison;

    @NotNull(message = "Le produit est requis")
    private String produit;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private Integer quantite;

    
    @NotNull(message = "L'entrepôt est requis")
    private String entrepot;

    @NotNull(message = "Le client est requis")
    private String client;

    @NotNull(message = "L'unite est requis")
    private String unite;

    private String remarque;
   
}
