package com.ensah.services;


import com.ensah.bo.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IInventaireService {

    List<Produit> ajouterInventaire(Inventaire i);

    public List<Produit> telechargerInventaire(Inventaire i);

    void valideInventaire(Long id/*,user*/);

    List<Ecart> afficher(String entrepot,LocalDate date);

    void modifierInventaire(Inventaire i);

    void supprimerInventaire(Inventaire i);

    Inventaire selectionnerInventaire(Long id);

    List<Inventaire> listeInventaire();

    public List<Ecart> calculeEcart(LocalDate d,String entrepot,MultipartFile file);

    public ByteArrayInputStream exporterInventaireExcel(List<Produit> produits);


    List<Inventaire> rechercherInventaire(LocalDate date1, LocalDate date2, String e);

    public void exporterInventaireExcel(HttpServletResponse responce,Inventaire i) throws Exception;

    public void telechargerInventaireExcel(HttpServletResponse responce,Long id) throws Exception;

}
