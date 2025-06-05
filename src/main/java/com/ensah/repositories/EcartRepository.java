package com.ensah.repositories;

import com.ensah.bo.Ecart;
import com.ensah.bo.Inventaire;
import com.ensah.bo.EcartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcartRepository extends JpaRepository<Ecart, EcartId> {

    // Recherche de tous les écarts pour un inventaire donné
    List<Ecart> findByInventaire(Inventaire inventaire);

    // Alternative si tu préfères chercher par ID d'inventaire directement :
    @Query("SELECT e FROM Ecart e WHERE e.id.inventaireId = :inventaireId")
    List<Ecart> findByInventaireId(@Param("inventaireId") Long inventaireId);
    
}
