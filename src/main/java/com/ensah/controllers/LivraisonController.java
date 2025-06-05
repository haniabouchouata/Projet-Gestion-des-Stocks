package com.ensah.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ensah.bo.Entrepot;
import com.ensah.bo.Livraison;
import com.ensah.bo.Reception;
import com.ensah.bo.Sortie;
import com.ensah.services.IEntrepotService;
import com.ensah.services.ILivraisonService;

import com.ensah.services.ISortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/livraison")
public class LivraisonController {

	@Autowired
	private ILivraisonService service;
	@Autowired
	private IEntrepotService entrepotService;
	private ISortieService sortieService;

	@GetMapping("/{id}")
	public ResponseEntity<Livraison> getLivraisonById(@PathVariable("id") String id) {
		return ResponseEntity.ok().body(service.selectionnerLivraison(id));
	}

	@GetMapping
	public String listerLivraisons(Model model) {
		List<Livraison> livraisons = service.listeLivraison();
		model.addAttribute("livraisons", livraisons);
		return "/livraison/listLivraison";
	}

	@GetMapping("/ajouter")
	public String afficherFormAjout(Model model) {
		model.addAttribute("livraison", new Livraison());
		List<Entrepot> entrepots = entrepotService.listeEntrepot();
		model.addAttribute("entrepots", entrepots);
		return "/livraison/formLivraison";
	}

	@PostMapping("/save")
	public String enregistrerLivraison(@Valid @ModelAttribute Livraison livraison) {
		service.ajouterLivraison(livraison);
		return "redirect:/livraison";
	}

	@GetMapping("/modifier/{id}")
	public String afficherFormModification(@PathVariable("id") String id, Model model) {
		Livraison livraison = service.selectionnerLivraison(id);
		model.addAttribute("livraison", livraison);
		model.addAttribute("livraisons", service.listeLivraison());
		return "formLivraison";
	}

	@PostMapping("/update")
	public String modifierLivraison(@ModelAttribute Livraison livraison) {
		service.modifierLivraison(livraison);
		return "redirect:/livraison";
	}

	@GetMapping("/supprimer/{id}")
	public String supprimerLivraison(@PathVariable("id") String id) {
		Livraison livraison = service.selectionnerLivraison(id);
		if (livraison != null) {
			service.supprimerLivraison(livraison);
		}
		return "redirect:/livraison";
	}

	/**@GetMapping("/recherche")
	public String rechercherLivraison(
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String produit,
			Model model) {

		List<Livraison> resultats = service.rechercherLivraisons(id, produit);

		model.addAttribute("livraisons", resultats);
		model.addAttribute("id", id);
		model.addAttribute("produit", produit);

		return "/livraison/listLivraison";
	}**/
	@GetMapping("/recherche")
	public String listeReceptions(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2,
			@RequestParam(required = false) String produit,
			@RequestParam(required = false) String entrepot,
			Model model) {

		Livraison livraison = new Livraison();
		livraison.setEntrepot(entrepot); // 👈 setter utilisé ici si Reception.entrepot est un String
		model.addAttribute("livraison", livraison);

		List<Entrepot> entrepots = entrepotService.listeEntrepot();
		model.addAttribute("entrepots", entrepots);

		List<Livraison> livraisons = service.rechercherLivraison(date1, date2, produit, entrepot);
		model.addAttribute("livraisons", livraisons);

		model.addAttribute("date1", date1);
		model.addAttribute("date2", date2);
		model.addAttribute("produit", produit);

		return "/livraison/listLivraison";
	}





	@GetMapping("/livrer")
	public Livraison receptionnerSortie(@PathVariable("id") String id) {
		return service.livrerSortie(id);
	}
	@GetMapping("/livrer/{id}")
	public String livrer(
			@ModelAttribute Sortie sortie,
			@RequestParam("dateDeReception") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLivraison
	) {
		service.livrerSortieDate(sortie.getId(),dateLivraison);

		// Optionnel : ajouter des attributs au modèle si tu rediriges vers une JSP
		//model.addAttribute("")

		return "redirect:/livraison/liste"; // ou une autre vue
	}



	// validation handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handValidEx(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();
		List<ObjectError> validationErros = ex.getBindingResult().getAllErrors();
		for (ObjectError error : validationErros) {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		}

		return ResponseEntity.badRequest().body(errors);
	}
}
