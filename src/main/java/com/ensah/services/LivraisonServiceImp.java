package com.ensah.services;

import com.ensah.bo.*;
import com.ensah.repositories.LivraisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LivraisonServiceImp implements ILivraisonService {
    
    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private ISortieService sortieService;

    // Générer un ID unique pour la livraison
    private String genererIdLivraisonUnique() {
        char prefixe = (char) ('A' + (int) (Math.random() * 26)); // 'A' + [0..25]
        int maxDigits = 8;
        long compteur = 1;
        String nouvelId;
        do {
            String codeNumerique = String.format("%0" + maxDigits + "d", compteur);
            nouvelId = prefixe + codeNumerique;
            compteur++;
        } while (livraisonRepository.existsById(nouvelId));
        return nouvelId;
    }

    // Ajouter une nouvelle livraison
    @Override
    public void ajouterLivraison(Livraison livraison) {
        if (livraison.getId() == null) {
            livraison.setId(genererIdLivraisonUnique());
        }
        livraisonRepository.save(livraison);
    }

    // Sélectionner une livraison par son ID
    @Override
    public Livraison selectionnerLivraison(String id) {
        return livraisonRepository.findById(id).orElse(null);
    }

    // Modifier une livraison existante
    @Override
    public void modifierLivraison(Livraison livraison) {
        livraisonRepository.save(livraison);
    }

    // Supprimer une livraison
    @Override
    public void supprimerLivraison(Livraison l) {
        livraisonRepository.delete(l);
    }

    // Liste de toutes les livraisons
    @Override
    public List<Livraison> listeLivraison() {
        return livraisonRepository.findAll();
    }

   /**public List<Livraison> rechercherLivraisons(String id, String produit) {
       if (id != null && !id.isEmpty() && produit != null && !produit.isEmpty()) {
           Livraison l = livraisonRepository.findByIdAndProduit(id, produit);
           return l != null ? List.of(l) : List.of();
       } else if (id != null && !id.isEmpty()) {
           return livraisonRepository.findByIdIgnoreCase(id);
       } else if (produit != null && !produit.isEmpty()) {
           return livraisonRepository.findByProduitIgnoreCase(produit);
       } else {
           return List.of(); // Aucun critère : retourne liste vide
       }
   }**/
   // Recherche de livraisons avec différents critères
   @Override
   public List<Livraison> rechercherLivraison(LocalDate date1, LocalDate date2, String p, String e) {
       if (date1 != null && date2 != null && e != null && p != null) {
           return livraisonRepository.rechercherParDatesProduitEntrepot(date1, date2, p, e);
       } else if ((date1 != null && e != null && p != null) || (date2 != null && e != null && p != null)) {
           if (date1 != null) {
               return livraisonRepository.findByDateLivraisonAndProduitAndEntrepotIgnoreCase(date1, p, e);
           } else {
               return livraisonRepository.findByDateLivraisonAndProduitAndEntrepotIgnoreCase(date2, p, e);
           }
       } else if (date1 != null && date2 != null && e != null) {
           return livraisonRepository.rechercherParDatesEntrepot(date1, date2, e);
       } else if (date1 != null && date2 != null && p != null) {
           return livraisonRepository.rechercherParDatesProduit(date1, date2, p);
       } else if (date1 != null && date2 != null) {
           return livraisonRepository.selectionEntreDeuxDates(date1, date2);
       } else if (date1 != null) {
           return livraisonRepository.findByDateLivraison(date1);
       } else if (date2 != null) {
           return livraisonRepository.findByDateLivraison(date2);
       } else if (e != null && p != null) {
           return livraisonRepository.findByEntrepotAndProduitIgnoreCase(e, p);
       } else if (p != null) {
           return livraisonRepository.findByProduitIgnoreCase(p);
       } else if (e != null) {
           return livraisonRepository.findByEntrepotIgnoreCase(e);
       } else {
           return null;
       }
   }






    // Livrer une sortie
    @Override
    public Livraison livrerSortie(String id) {
        Livraison livraison = new Livraison();
        Sortie sortie = sortieService.trouverSortie(id);

        if (sortie != null) {
            livraison.setId(sortie.getId());
            livraison.setQuantite(sortie.getQuantite());
            livraison.setProduit(sortie.getProduit());
            livraison.setUnite(sortie.getUnite());
            livraison.setClient(sortie.getClient());
            livraison.setRemarque(sortie.getRemarque());
            livraison.setEntrepot(sortie.getEntrepot());
            return livraison;
        } else {
            return null;
        }
    }

    public Livraison livrerSortieDate(String id ,LocalDate date) {
        Livraison livraison = new Livraison();
        Sortie sortie = sortieService.trouverSortie(id);

        if (sortie != null) {
            livraison.setId(sortie.getId());
            livraison.setQuantite(sortie.getQuantite());
            livraison.setProduit(sortie.getProduit());
            livraison.setDateLivraison(date);

            livraison.setUnite(sortie.getUnite());
            livraison.setClient(sortie.getClient());
            livraison.setRemarque(sortie.getRemarque());
            livraison.setEntrepot(sortie.getEntrepot());
            return livraison;
        } else {
            return null;
        }
    }

}
