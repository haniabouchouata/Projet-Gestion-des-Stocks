package com.ensah.services;

import com.ensah.bo.Sortie;
import com.ensah.repositories.SortieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SortieServiceImp implements ISortieService {

    @Autowired
    private SortieRepository sortieRepository;
    
    @Override
    public List<Sortie> listeSortie() {
        return sortieRepository.findAll();
    }

    @Override
    public Sortie trouverSortie(String id) {
        return sortieRepository.findById(id).orElse(null);
    }

    @Override
    public List<Sortie> rechercherSortie(String num, String p) {
        List<Sortie> resultList = new ArrayList<>();

        // Recherche par ID et produit
        if (num != null && p != null) {
            resultList = sortieRepository.trouverByIdAndProduitIgnoreCase(num, p);
        } 
        // Recherche par ID uniquement
        else if (num != null) {
            Optional<Sortie> result = sortieRepository.findById(num);
            result.ifPresent(resultList::add);
        } 
        // Recherche par produit uniquement
        else if (p != null) {
            resultList = sortieRepository.trouverByProduitIgnoreCase(p);
        }

        return resultList;
    }
}
