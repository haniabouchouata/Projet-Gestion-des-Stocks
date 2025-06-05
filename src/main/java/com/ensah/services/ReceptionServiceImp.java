package com.ensah.services;

import com.ensah.bo.Achat;
import com.ensah.bo.Reception;
import com.ensah.repositories.ReceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReceptionServiceImp implements IReceptionService {

    @Autowired
    private ReceptionRepository receptionRepository;

    @Autowired
    private IAchatService achatService;

    private String genererIdReceptionUnique() {
        char prefixe = (char) ('A' + (int) (Math.random() * 26)); // 'A' à 'Z'
        int maxDigits = 8;
        long compteur = 1;

        String nouvelId;

        do {
            String codeNumerique = String.format("%0" + maxDigits + "d", compteur);
            nouvelId = prefixe + codeNumerique;
            compteur++;
        } while (receptionRepository.existsById(nouvelId));

        return nouvelId;
    }

    @Override
    public void ajouterReception(Reception reception) {
        if (reception.getId() == null || reception.getId().isEmpty()) {
            reception.setId(genererIdReceptionUnique());
        }
        receptionRepository.save(reception);
    }

    @Override
    public Reception selectionnerReception(String id) {
        Optional<Reception> reception = receptionRepository.findById(id);
        return reception.orElse(null);
    }

    @Override
    public void modifierReception(Reception reception) {
        receptionRepository.save(reception);
    }

    @Override
    public void supprimerReception(Reception reception) {
        receptionRepository.delete(reception);
    }

    @Override
    public List<Reception> listeReception() {
        return receptionRepository.findAll();
    }

    @Override
    public List<Reception> rechercherReception(LocalDate date1, LocalDate date2, String p, String e) {
        if (date1 != null && date2 != null && e != null && p != null) {
            return receptionRepository.rechercherParDatesProduitEntrepot(date1, date2, p, e);
        } else if ((date1 != null || date2 != null) && e != null && p != null) {
            LocalDate date = date1 != null ? date1 : date2;
            return receptionRepository.findByDateReceptionAndProduitAndEntrepot(date, p, e);
        } else if (date1 != null && date2 != null && e != null) {
            return receptionRepository.rechercherParDatesEntrepot(date1, date2, e);
        } else if (date1 != null && date2 != null && p != null) {
            return receptionRepository.rechercherParDatesProduit(date1, date2, p);
        } else if (date1 != null && date2 != null) {
            return receptionRepository.selectionEntreDeuxDates(date1, date2);
        } else if (date1 != null) {
            return receptionRepository.findByDateReception(date1);
        } else if (date2 != null) {
            return receptionRepository.findByDateReception(date2);
        } else if (e != null && p != null) {
            return receptionRepository.findByEntrepotAndProduit(e, p);
        } else if (p != null) {
            return receptionRepository.findByProduit(p);
        } else if (e != null) {
            return receptionRepository.findByEntrepot(e);
        } else {
            return null;
        }
    }

    @Override
    public Reception receptionnerAchat(String id) {
        Achat a = achatService.trouverAchat(id);
        if (a != null) {
            Reception reception = new Reception();
            reception.setId(a.getId());
            reception.setQuantite(a.getQuantite());
            reception.setProduit(a.getProduit());
            reception.setUnite(a.getUnite());
            reception.setEntrepot(a.getEntrepot());
            reception.setDateReception(LocalDate.now());
            reception.setSource(a.getSource());
            receptionRepository.save(reception);
            return reception;
        } else {
            return null;
        }
    }


}
