package com.ensah.controllers;

import com.ensah.bo.Transfert;
import com.ensah.services.ITransfertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transfert")
public class TransfertController {

	@Autowired
	private ITransfertService service;

	@GetMapping
	public String listeTransferts(Model model,
								  @RequestParam(name = "dateDebut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
								  @RequestParam(name = "dateFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
								  @RequestParam(name = "produit", required = false) String produit) {

		List<Transfert> transferts;

		if (dateDebut != null || dateFin != null || (produit != null && !produit.isEmpty())) {
			transferts = service.rechercherTransfert(dateDebut, dateFin, produit);
		} else {
			transferts = service.listeTransfert();
		}

		model.addAttribute("transferts", transferts);
		model.addAttribute("dateDebut", dateDebut);
		model.addAttribute("dateFin", dateFin);
		model.addAttribute("produit", produit);
		return "/transfert/listTransfert";
	}

	@GetMapping("/new")
	public String formulaireAjout(Model model) {
		model.addAttribute("transfert", new Transfert());
		return "/transfert/formTransfert";
	}

	@PostMapping("/save")
	public String enregistrer(@ModelAttribute Transfert transfert) {
		service.ajouterTransfert(transfert);
		return "redirect:/transfert";
	}

	@GetMapping("/edit/{id}")
	public String modifier(@PathVariable("id") Long id, Model model) {
		Transfert t = service.selectionnerTransfert(id);
		model.addAttribute("transfert", t);
		return "/transfert/formTransfert";
	}

	@GetMapping("/delete/{id}")
	public String supprimer(@PathVariable("id") Long id) {
		Transfert t = service.selectionnerTransfert(id);
		service.suprimerTransfert(t);
		return "redirect:/transferts";
	}
}
