package com.ensah.bo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class EcartId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long inventaireId;
    private Long produitId;

    public EcartId() {}

    public EcartId(Long inventaireId, Long produitId) {
        this.inventaireId = inventaireId;
        this.produitId = produitId;
    }

    // Getters and Setters

    public Long getInventaireId() {
        return inventaireId;
    }

    public void setInventaireId(Long inventaireId) {
        this.inventaireId = inventaireId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    // equals and hashCode (important for composite keys)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EcartId)) return false;

        EcartId that = (EcartId) o;

        if (!inventaireId.equals(that.inventaireId)) return false;
        return produitId.equals(that.produitId);
    }

    @Override
    public int hashCode() {
        int result = inventaireId.hashCode();
        result = 31 * result + produitId.hashCode();
        return result;
    }
}
