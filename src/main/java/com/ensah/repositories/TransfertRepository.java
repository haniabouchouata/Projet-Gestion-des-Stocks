package com.ensah.repositories;

import com.ensah.bo.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TransfertRepository extends JpaRepository<Transfert, Long>{
	List<Transfert> findByProduit(String p);

    List<Transfert> findByDateTransfert(LocalDate p);

    List<Transfert> findByDateTransfertAndProduit(LocalDate date,String p);

    @Query("select t from Transfert t where t.dateTransfert between :y and :x ")
    List<Transfert> selectionDeuxDates(@Param("y") LocalDate date1,@Param("x") LocalDate date2);

    @Query("select t from Transfert t where t.dateTransfert between :y and :x and t.produit =:w ")
    List<Transfert> rechercheparDatesProduit(@Param("y") LocalDate date1,@Param("x") LocalDate date2,@Param(value = "w") String P);

}
