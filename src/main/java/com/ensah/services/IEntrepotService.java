package com.ensah.services;

import com.ensah.bo.Entrepot;
import java.util.List;

public interface IEntrepotService {
	
	public void ajouterEntrepot(Entrepot entrepot);

    public List<Entrepot> listeEntrepot();
    
    public Entrepot selectionnerEntrepot(long id);

    public void modifierEntrepot(Entrepot entrepot);

    void supprimerEntrepot(Entrepot entrepot);

}
