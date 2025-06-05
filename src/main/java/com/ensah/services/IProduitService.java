package com.ensah.services;

import com.ensah.bo.Produit;

public interface IProduitService {
	
	public long trouverQuantite(String produit);
	
	public Produit trouverProduit(String produit);

}
