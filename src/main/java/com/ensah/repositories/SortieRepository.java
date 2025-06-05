package com.ensah.repositories;

import com.ensah.bo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SortieRepository extends JpaRepository<Sortie, String>{
	 @Query("SELECT s FROM Sortie s WHERE LOWER(s.produit) = LOWER(:produit)")
	 List<Sortie> trouverByProduitIgnoreCase(@Param("produit") String produit);


	    @Query("SELECT s FROM Sortie s WHERE s.id= :id and LOWER(s.produit) = LOWER(:produit)")
	    List<Sortie> trouverByIdAndProduitIgnoreCase(@Param("id") String id,@Param("produit") String produit);

}