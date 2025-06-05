package com.ensah.services;

import com.ensah.bo.Entrepot;
import com.ensah.repositories.EntrepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EntrepotServiceImp implements IEntrepotService {
	
	@Autowired
    private EntrepotRepository entrepotRepository;

    @Override
    public void ajouterEntrepot(Entrepot entrepot) {
    	entrepot.setId(null);
        entrepotRepository.save(entrepot);
    }

    @Override
    public List<Entrepot> listeEntrepot() {
        return entrepotRepository.findAll();
    }
    
    @Override
    public Entrepot selectionnerEntrepot(long id) {
    	Entrepot e = entrepotRepository.findById(id);
    	return e;
    }

    @Override
    public void modifierEntrepot(Entrepot entrepot) {
        entrepotRepository.save(entrepot);   
    }

    @Override
    public void supprimerEntrepot(Entrepot entrepot) {
        entrepotRepository.delete(entrepot);
    }
	
	
}
