package com.ensah.services;

import com.ensah.bo.*;

import java.time.LocalDate;
import java.util.List;

public interface ISortieService {

    public List<Sortie> listeSortie();
    
    public List<Sortie> rechercherSortie(String id,String p);

    public Sortie trouverSortie(String id);


}
