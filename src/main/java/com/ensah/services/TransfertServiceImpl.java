package com.ensah.services;

import com.ensah.bo.Transfert;
import com.ensah.repositories.TransfertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransfertServiceImpl implements ITransfertService {

    @Autowired
    private TransfertRepository transfertRepository;

    @Override
    public void ajouterTransfert(Transfert t) {
        transfertRepository.save(t);
    }

    @Override
    public void modifierTransfert(Transfert t) {
        transfertRepository.save(t);
    }

    @Override
    public void suprimerTransfert(Transfert t) {
        transfertRepository.delete(t);
    }

    @Override
    public List<Transfert> listeTransfert() {
        return transfertRepository.findAll();
    }

    @Override
    public Transfert selectionnerTransfert(long id) {
        Optional<Transfert> opt = transfertRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public List<Transfert> rechercherTransfert(LocalDate date1, LocalDate date2, String produit) {
        return transfertRepository.findAll()
                .stream()
                .filter(t -> (date1 == null || !t.getDateTransfert().isBefore(date1)) &&
                        (date2 == null || !t.getDateTransfert().isAfter(date2)) &&
                        (produit == null || t.getProduit().toLowerCase().contains(produit.toLowerCase())))
                .collect(Collectors.toList());
    }
}
