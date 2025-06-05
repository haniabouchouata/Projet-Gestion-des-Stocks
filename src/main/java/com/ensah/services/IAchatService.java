package com.ensah.services;

import com.ensah.bo.Achat;

import java.util.*;

public interface IAchatService {
    
    public List<Achat> listeAchat();

    public List<Achat> rechercherAchat(String numAchat,String p);

    public Achat trouverAchat(String id);
    
}
