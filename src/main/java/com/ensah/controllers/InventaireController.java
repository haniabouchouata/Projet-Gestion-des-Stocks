package com.ensah.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.ensah.services.IEntrepotService;
import com.ensah.services.IInventaireService;
import com.ensah.bo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/inventaire")
public class InventaireController {

	@Autowired
	private IInventaireService service;
	@Autowired
	private IEntrepotService entrepotService;

	@GetMapping("/{id}")
	public String getInventaireById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inventaire", service.selectionnerInventaire(id));
		return "inventaire/detail";
	}

	@GetMapping("/telecharger/{id}")
	public void telechargerInventaire(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
		response.setContentType("application/octet");
		response.setHeader("Content-Disposition", "attachment;filename=Inventaire.xls");
		service.telechargerInventaireExcel(response, id);
	}

	@GetMapping
	public String getAllInventaire(Model model) {
		List<Inventaire> inventaires = service.listeInventaire();
		List<Entrepot> entrepots = entrepotService.listeEntrepot();
		model.addAttribute("entrepots", entrepots);
		model.addAttribute("inventaires", inventaires);
		return "inventaire/listInventaire";
	}
	@GetMapping("/nouveau")
	public String afficherFormulaireNouveauInventaire(Model model) {
		model.addAttribute("inventaire", new Inventaire());
		List<Entrepot> entrepots = entrepotService.listeEntrepot();
		model.addAttribute("entrepots", entrepots);
		return "inventaire/formInventaire"; // correspond à nouveauInventaire.html
	}


	@PostMapping("/ajouter")
	public void ajouterInventaire(@ModelAttribute Inventaire i, HttpServletResponse response) throws Exception {
		i.setEffectuePar("Kamal");
		i.setValidePar("khalid");
		response.setContentType("application/octet");
		response.setHeader("Content-Disposition","attachment;filename=courses.xls");
		service.exporterInventaireExcel(response, i);
	}

	@PostMapping("/valide")
	public String validerInventaire(@RequestParam("id") Long id) {
		service.valideInventaire(id);
		return "redirect:/inventaire";
	}

	@GetMapping("/affichee/ecarts")
	public String afficherEcarts(@RequestParam LocalDate date,
								 @RequestParam String entrepot, Model model) {
		List<Ecart> ecarts = service.afficher(entrepot,date);
		model.addAttribute("ecarts", ecarts);
		model.addAttribute("date", date);
		model.addAttribute("entrepot", entrepot);
		return "inventaire/listEcart";
	}

	@GetMapping("/pageecart")
	public String ecarts(@RequestParam LocalDate dateInventaire,
						 @RequestParam String entrepot, Model model) {
		List<Ecart> ecarts = service.afficher(entrepot,dateInventaire);
		model.addAttribute("ecarts", ecarts);
		model.addAttribute("date", dateInventaire);
		model.addAttribute("entrepot", entrepot);
		return "inventaire/ecarts";
	}

	@PostMapping("/ecarts")
	public String ecart(@ModelAttribute("inventaire") Inventaire inventaire, Model model) {
		// Vérifie que les attributs ne sont pas null (optionnel)
		if (inventaire.getDateInventaire() != null) {
			model.addAttribute("date", inventaire.getDateInventaire());
		}
		if (inventaire.getEntrepot() != null) {
			model.addAttribute("entrepot", inventaire.getEntrepot());
		}
		model.addAttribute("inv", inventaire);
		model.addAttribute("ecarts", new ArrayList<Ecart>());
		return "inventaire/ecarts";
	}




	@PostMapping("/calcule/ecarts")
	public String calculerEcarts(@ModelAttribute Inventaire inv,
								 @RequestParam MultipartFile file,
								 Model model) {
		List<Ecart> ecarts = service.calculeEcart(inv.getDateInventaire(), inv.getEntrepot(), file);
		model.addAttribute("inv", inv);
		model.addAttribute("ecarts", ecarts);
		model.addAttribute("id", 0);
		return "inventaire/ecarts";
	}


	@PostMapping("/modifier")
	public String modifierInventaire(@ModelAttribute Inventaire i) {
		service.modifierInventaire(i);
		return "redirect:/inventaire";
	}

	@PostMapping("/supprimer")
	public String supprimerInventaire(@RequestParam("id") Long id) {
		Inventaire i = new Inventaire();
		i.setId(id);
		service.supprimerInventaire(i);
		return "redirect:/inventaire";
	}

	@GetMapping("/recherche")
	public String rechercherInventaire(@RequestParam(required = false) LocalDate date1,
									   @RequestParam(required = false) LocalDate date2,
									   @RequestParam(required = false) String entrepot,
									   Model model) {
		List<Entrepot> entrepots = entrepotService.listeEntrepot();
		model.addAttribute("entrepots", entrepots);
		return "inventaire/listInventaire";
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidationErrors(MethodArgumentNotValidException ex, Model model) {
		Map<String, String> errors = new HashMap<>();
		List<ObjectError> validationErrors = ex.getBindingResult().getAllErrors();
		for (ObjectError error : validationErrors) {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		}
		model.addAttribute("errors", errors);
		return "inventaire/form";
	}
}