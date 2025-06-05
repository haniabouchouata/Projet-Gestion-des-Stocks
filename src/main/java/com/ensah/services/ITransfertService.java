package com.ensah.services;

import com.ensah.bo.Transfert;

import java.time.LocalDate;
import java.util.*;

public interface ITransfertService {

    void ajouterTransfert(Transfert t);

    void modifierTransfert(Transfert t);

    void suprimerTransfert(Transfert t);
    
    public List<Transfert> listeTransfert();

    Transfert selectionnerTransfert(long id);

    List<Transfert> rechercherTransfert(LocalDate date1,LocalDate date2,String p);
    
}
