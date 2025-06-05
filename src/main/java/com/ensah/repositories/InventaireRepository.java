package com.ensah.repositories;

import com.ensah.bo.Inventaire;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventaireRepository extends JpaRepository<Inventaire, Long> {

    @Query("SELECT i FROM Inventaire i WHERE LOWER(i.entrepot) = LOWER(:entrepot)")
    List<Inventaire> findByEntrepotIgnoreCase(@Param("entrepot") String entrepot);

    @Query("SELECT i FROM Inventaire i WHERE i.dateInventaire BETWEEN :date1 AND :date2 AND LOWER(i.entrepot) = LOWER(:entrepot)")
    List<Inventaire> rechercherParDatesEntrepot(@Param("date1") LocalDate date1,
                                                @Param("date2") LocalDate date2,
                                                @Param("entrepot") String entrepot);

    @Query("SELECT i FROM Inventaire i WHERE i.dateInventaire BETWEEN :date1 AND :date2")
    List<Inventaire> selectionEntreDeuxDates(@Param("date1") LocalDate date1,
                                             @Param("date2") LocalDate date2);

    @Modifying
    @Transactional
    //@Query("UPDATE Inventaire i SET i.valide = true, i.validePar = :user WHERE i.id = :id")//mora men9ado l users nediro had requete
    @Query("UPDATE Inventaire i SET i.valide = true WHERE i.id = :id")
    void valideInventaire(@Param("id") Long id/*, @Param("user") String user*/);

    @Query("SELECT i FROM Inventaire i WHERE i.dateInventaire=:date1 and LOWER(i.entrepot) = LOWER(:entrepot)")
    Inventaire findInv(@Param("date1") LocalDate date1,@Param("entrepot") String entrepot);

    @Query("SELECT i FROM Inventaire i WHERE LOWER(i.entrepot) = LOWER(:entrepot) and i.dateInventaire=:date1")
    Inventaire findByEntrepotAndDateInventaireContainsIgnoreCase(@Param("entrepot") String entrepot,@Param("date1") LocalDate date1);



    List<Inventaire> findByDateInventaire(LocalDate date1);
}
