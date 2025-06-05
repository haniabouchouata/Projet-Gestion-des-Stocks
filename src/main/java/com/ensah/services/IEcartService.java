package com.ensah.services;

import com.ensah.bo.*;

import java.util.List;

public interface IEcartService {

    // Retourne la liste des écarts pour un inventaire donné
    List<Ecart> getEcartsByInventaire(Inventaire inventaire);

    public List<Ecart> getEcarts() ;
    
    // Modifier un écart existant (par son ID composite)
    void modifierEcart(Ecart ecart);
}
