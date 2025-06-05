package com.ensah.repositories;

import com.ensah.bo.Livraison;
import com.ensah.bo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, String> {
        /**
	    // Recherche insensible à la casse pour le produit
	    @Query("SELECT r FROM Livraison r WHERE LOWER(r.produit) = LOWER(:produit)")
	    List<Livraison> findByProduitIgnoreCase(@Param("produit") String produit);

	    // Recherche insensible à la casse pour id
	    @Query("SELECT r FROM Livraison r WHERE LOWER(r.id) = LOWER(:id)")
	    List<Livraison> findByIdIgnoreCase(@Param("id") String id);

	    // Recherche insensible à la casse pour le produit et id
		@Query("SELECT r FROM Livraison r WHERE LOWER(r.id) = LOWER(:id) AND LOWER(r.produit) = LOWER(:produit)")
		Livraison findByIdAndProduit(@Param("id") String id, @Param("produit") String produit);
        **/
		// Recherche insensible à la casse pour le produit
		@Query("SELECT r FROM Livraison r WHERE LOWER(r.produit) = LOWER(:produit)")
		List<Livraison> findByProduitIgnoreCase(@Param("produit") String produit);

	// Recherche insensible à la casse pour l'entrepôt
	@Query("SELECT r FROM Livraison r WHERE LOWER(r.entrepot) = LOWER(:entrepot)")
	List<Livraison> findByEntrepotIgnoreCase(@Param("entrepot") String entrepot);

	// Recherche insensible à la casse pour le produit et l'entrepôt
	@Query("SELECT r FROM Livraison r WHERE LOWER(r.entrepot) = LOWER(:entrepot) AND LOWER(r.produit) = LOWER(:produit)")
	List<Livraison> findByEntrepotAndProduitIgnoreCase(@Param("entrepot") String entrepot,
													   @Param("produit") String produit);

	// Recherche insensible à la casse pour la date de livraison
	@Query("SELECT r FROM Livraison r WHERE r.dateLivraison = :date")
	List<Livraison> findByDateLivraison(@Param("date") LocalDate date);

	// Recherche insensible à la casse pour la date, le produit et l'entrepôt
	@Query("SELECT r FROM Livraison r WHERE r.dateLivraison = :date1 AND LOWER(r.produit) = LOWER(:produit) AND LOWER(r.entrepot) = LOWER(:entrepot)")
	List<Livraison> findByDateLivraisonAndProduitAndEntrepotIgnoreCase(@Param("date1") LocalDate date1,
																	   @Param("produit") String produit,
																	   @Param("entrepot") String entrepot);

	// Recherche entre deux dates, insensible à la casse pour le produit et l'entrepôt
	@Query("SELECT r FROM Livraison r WHERE r.dateLivraison BETWEEN :date1 AND :date2 AND LOWER(r.produit) = LOWER(:produit) AND LOWER(r.entrepot) = LOWER(:entrepot)")
	List<Livraison> rechercherParDatesProduitEntrepot(@Param("date1") LocalDate date1,
													  @Param("date2") LocalDate date2,
													  @Param("produit") String produit,
													  @Param("entrepot") String entrepot);

	// Recherche entre deux dates, insensible à la casse pour le produit
	@Query("SELECT r FROM Livraison r WHERE r.dateLivraison BETWEEN :date1 AND :date2 AND LOWER(r.produit) = LOWER(:produit)")
	List<Livraison> rechercherParDatesProduit(@Param("date1") LocalDate date1,
											  @Param("date2") LocalDate date2,
											  @Param("produit") String produit);

	// Recherche entre deux dates, insensible à la casse pour l'entrepôt
	@Query("SELECT r FROM Livraison r WHERE r.dateLivraison BETWEEN :date1 AND :date2 AND LOWER(r.entrepot) = LOWER(:entrepot)")
	List<Livraison> rechercherParDatesEntrepot(@Param("date1") LocalDate date1,
											   @Param("date2") LocalDate date2,
											   @Param("entrepot") String entrepot);

	// Recherche entre deux dates sans tenir compte du produit et de l'entrepôt
	@Query("SELECT r FROM Livraison r WHERE r.dateLivraison BETWEEN :date1 AND :date2")
	List<Livraison> selectionEntreDeuxDates(@Param("date1") LocalDate date1,
											@Param("date2") LocalDate date2);



}

