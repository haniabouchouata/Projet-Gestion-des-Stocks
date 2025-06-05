package com.ensah.repositories;

import com.ensah.bo.Entrepot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EntrepotRepository extends JpaRepository<Entrepot, Long>{
	Entrepot findById(long id);
}
