package com.ensah.services;

import com.ensah.bo.Reception;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface IReceptionService {

    void ajouterReception(Reception reception);

    void modifierReception(Reception reception);

    void supprimerReception(Reception r);

    Reception selectionnerReception(String id);
    
    List<Reception> listeReception();

    List<Reception> rechercherReception(LocalDate date1, LocalDate date2, String p, String e);

    public Reception receptionnerAchat(String id/* ,LocalDate date,String entrepot*/);

}
