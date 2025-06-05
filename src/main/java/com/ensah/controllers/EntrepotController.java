package com.ensah.controllers;

import com.ensah.bo.Entrepot;
import com.ensah.services.IEntrepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/entrepots")
public class EntrepotController {

	@Autowired
	private IEntrepotService service;

	// Afficher la liste + formulaire
	@GetMapping("/list")
	public String listEntrepots(Model model) {
		model.addAttribute("entrepots", service.listeEntrepot());
		model.addAttribute("entrepot", new Entrepot());
		return "list"; // <<< OK pour toi
	}

	// Ajouter un nouvel entrepôt
	@PostMapping("/add")
	public String addEntrepot(@ModelAttribute("entrepot") Entrepot e) {
		service.ajouterEntrepot(e);
		return "redirect:/entrepots/list"; // <<< redirection correcte
	}

	// Modifier un entrepôt (formulaire avec ID)
	@GetMapping("/edit/{id}")
	public String editEntrepot(@PathVariable("id") Long id, Model model) {
		model.addAttribute("entrepot", service.selectionnerEntrepot(id));
		model.addAttribute("entrepots", service.listeEntrepot());
		return "list";
	}

	// Sauvegarder la modification
	@PostMapping("/update")
	public String updateEntrepot(@ModelAttribute("entrepot") Entrepot e) {
		service.modifierEntrepot(e);
		return "redirect:/entrepots/list";
	}

	// Supprimer
	@GetMapping("/delete/{id}")
	public String deleteEntrepot(@PathVariable("id") Long id) {
		Entrepot e = service.selectionnerEntrepot(id);
		service.supprimerEntrepot(e);
		return "redirect:/entrepots/list";
	}
}
