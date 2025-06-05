package com.ensah.repositories;

import com.ensah.bo.Reception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceptionRepository extends JpaRepository<Reception, String> {

    @Query("SELECT r FROM Reception r WHERE LOWER(r.produit) = LOWER(:produit)")
    List<Reception> findByProduit(@Param("produit") String produit);

    @Query("SELECT r FROM Reception r WHERE LOWER(r.entrepot) = LOWER(:entrepot)")
    List<Reception> findByEntrepot(@Param("entrepot") String entrepot);

    @Query("SELECT r FROM Reception r WHERE LOWER(r.entrepot) = LOWER(:entrepot) AND LOWER(r.produit) = LOWER(:produit)")
    List<Reception> findByEntrepotAndProduit(@Param("entrepot") String entrepot,
                                             @Param("produit") String produit);

    List<Reception> findByDateReception(LocalDate date);

    @Query("SELECT r FROM Reception r WHERE r.dateReception = :date AND LOWER(r.produit) = LOWER(:produit) AND LOWER(r.entrepot) = LOWER(:entrepot)")
    List<Reception> findByDateReceptionAndProduitAndEntrepot(@Param("date") LocalDate date,
                                                             @Param("produit") String produit,
                                                             @Param("entrepot") String entrepot);

    @Query("SELECT r FROM Reception r WHERE r.dateReception BETWEEN :date1 AND :date2 AND LOWER(r.produit) = LOWER(:produit) AND LOWER(r.entrepot) = LOWER(:entrepot)")
    List<Reception> rechercherParDatesProduitEntrepot(@Param("date1") LocalDate date1,
                                                      @Param("date2") LocalDate date2,
                                                      @Param("produit") String produit,
                                                      @Param("entrepot") String entrepot);

    @Query("SELECT r FROM Reception r WHERE r.dateReception BETWEEN :date1 AND :date2")
    List<Reception> selectionEntreDeuxDates(@Param("date1") LocalDate date1,
                                            @Param("date2") LocalDate date2);

    @Query("SELECT r FROM Reception r WHERE r.dateReception BETWEEN :date1 AND :date2 AND LOWER(r.entrepot) = LOWER(:entrepot)")
    List<Reception> rechercherParDatesEntrepot(@Param("date1") LocalDate date1,
                                               @Param("date2") LocalDate date2,
                                               @Param("entrepot") String entrepot);

    @Query("SELECT r FROM Reception r WHERE r.dateReception BETWEEN :date1 AND :date2 AND LOWER(r.produit) = LOWER(:produit)")
    List<Reception> rechercherParDatesProduit(@Param("date1") LocalDate date1,
                                              @Param("date2") LocalDate date2,
                                              @Param("produit") String produit);
}
