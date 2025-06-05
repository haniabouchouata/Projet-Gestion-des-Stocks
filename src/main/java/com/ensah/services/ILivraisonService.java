package com.ensah.services;

import com.ensah.bo.Livraison;

import java.time.LocalDate;
import java.util.List;

public interface ILivraisonService {

    // Ajouter une livraison
    void ajouterLivraison(Livraison livraison);

    // Modifier une livraison
    void modifierLivraison(Livraison livraison);

    // Supprimer une livraison
    void supprimerLivraison(Livraison livraison);

    // Sélectionner une livraison par ID
    Livraison selectionnerLivraison(String id);

    // Récupérer la liste de toutes les livraisons
    List<Livraison> listeLivraison();

    //List<Livraison> rechercherLivraisons(String id, String p);

    // Recherche de livraisons avec différents critères
    List<Livraison> rechercherLivraison(LocalDate date1, LocalDate date2, String p, String e);

    // Préparer une livraison à partir d'une sortie
    Livraison livrerSortie(String idSortie);
    public Livraison livrerSortieDate(String id , LocalDate date);

}
