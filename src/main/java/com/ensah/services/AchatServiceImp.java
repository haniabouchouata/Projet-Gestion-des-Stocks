package com.ensah.services;

import com.ensah.bo.Achat;
import com.ensah.repositories.AchatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AchatServiceImp implements IAchatService {

    @Autowired
    private AchatRepository achatRepository;

    @Override
    public List<Achat> listeAchat() {
        return achatRepository.findAll();
    }

    @Override
    public Achat trouverAchat(String id) {
        Achat result = achatRepository.findById(id).orElse(null);
        return result; // ou tu peux lancer une exception si pas trouvé
    }

    @Override
    public List<Achat> rechercherAchat(String num, String p) {
        if (num != null && p != null) {
            return achatRepository.trouverByIdAndProduitIgnoreCase(num, p);
        } else if (num != null) {
            Optional<Achat> result = achatRepository.findById(num);
            List<Achat> list = new ArrayList<>();
            result.ifPresent(list::add);
            return list;
        } else if (p != null) {
            return achatRepository.trouverByProduitIgnoreCase(p);
        } else {
            return new ArrayList<>();
        }
    }
}
