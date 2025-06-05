package com.ensah.repositories;

import com.ensah.bo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>{
	
	@Query("SELECT p FROM Produit p WHERE LOWER(p.nom) = LOWER(:nom)")
	Produit trouverProduit(@Param("nom") String nom);

}
