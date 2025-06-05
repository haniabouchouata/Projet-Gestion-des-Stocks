package com.ensah.services;

import com.ensah.bo.*;
import com.ensah.repositories.EcartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcartServiceImp implements IEcartService {

    @Autowired
    private EcartRepository ecartRepository;

    @Override
    public List<Ecart> getEcartsByInventaire(Inventaire inventaire) {
        return ecartRepository.findByInventaire(inventaire);
    }
    
    @Override
    public List<Ecart> getEcarts() {
        return ecartRepository.findAll();
    }

    @Override
    public void modifierEcart(Ecart ecart) {
        // On suppose que l'ID composite est déjà correctement défini dans l'objet Ecart
        Ecart existing = ecartRepository.findById(ecart.getId()).orElse(null);
        if (existing != null) {
            existing.setQuantiteAvant(ecart.getQuantiteAvant());
            existing.setQuantiteApres(ecart.getQuantiteApres());
            existing.setDifference(ecart.getQuantiteApres() - ecart.getQuantiteAvant());
            existing.setJustification(ecart.getJustification());
            ecartRepository.save(existing);
        }
    }
}
