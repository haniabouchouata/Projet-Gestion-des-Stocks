package com.ensah.bo;

import java.time.LocalDate;
import com.ensah.bo.Entrepot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.ensah.bo.Entrepot;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reception {

    @Id
    private String id; // Format: R + 8 chiffres

    @NotNull(message = "La date de réception est obligatoire")
    private LocalDate dateReception;

    
    @NotNull(message = "Le produit est requis")
    private String produit;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit() être au moins 1")
    private Integer quantite;

    @NotNull(message = "L'entrepôt est requis")
    private String entrepot;

    @NotNull(message = "La sourcce est requis")
    private String source;

    @NotNull(message = "La sourcce est requis")
    private String unite;

    private String remarque;
}
