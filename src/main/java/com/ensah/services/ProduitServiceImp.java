package com.ensah.services;

import com.ensah.bo.*;
import com.ensah.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional

public class ProduitServiceImp implements IProduitService{
	
	@Autowired
	private ProduitRepository produitRepository;

	@Override
	public long trouverQuantite(String produit) {
		Produit p = produitRepository.trouverProduit(produit);
		return p.getQuantite();		
		
	}
	public Produit trouverProduit(String produit) {
		return  produitRepository.trouverProduit(produit);
		
		
	}

}
