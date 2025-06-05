package com.ensah.repositories;

import com.ensah.bo.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AchatRepository extends JpaRepository<Achat, String> {
    
    @Query("SELECT a FROM Achat a WHERE LOWER(a.produit) = LOWER(:produit)")
    List<Achat> trouverByProduitIgnoreCase(@Param("produit") String produit);


    @Query("SELECT a FROM Achat a WHERE a.id= :id and LOWER(a.produit) = LOWER(:produit)")
    List<Achat> trouverByIdAndProduitIgnoreCase(@Param("id") String id,@Param("produit") String produit);

    }
