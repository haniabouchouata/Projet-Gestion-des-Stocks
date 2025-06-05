package com.ensah.controllers;

import com.ensah.bo.*;


import com.ensah.services.IAchatService;
import com.ensah.services.IEntrepotService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.ensah.services.IReceptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reception")

public class ReceptionController {

    @Autowired
	private IReceptionService service;
	@Autowired
	private IEntrepotService entrepotService;
	@Autowired
	private IAchatService serviceAchat;

	@GetMapping("/{id}")
	public ResponseEntity<Reception> getReceptionById(@PathVariable("id") String id) {
		return ResponseEntity.ok().body(service.selectionnerReception(id));
	}
	
	@GetMapping()
	public String getAllReception(Model model) {

		List<Reception> receptions = service.listeReception();
		model.addAttribute("receptions", receptions);
		return "/reception/listReception";
	}

	@GetMapping("/recherche")
	public String listeReceptions(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2,
			@RequestParam(required = false) String produit,
			@RequestParam(required = false) String entrepot,
			Model model) {

		Reception reception = new Reception();
		reception.setEntrepot(entrepot); // 👈 setter utilisé ici si Reception.entrepot est un String
		model.addAttribute("reception", reception);

		List<Entrepot> entrepots = entrepotService.listeEntrepot();
		model.addAttribute("entrepots", entrepots);

		List<Reception> receptions = service.rechercherReception(date1, date2, produit, entrepot);
		model.addAttribute("receptions", receptions);

		model.addAttribute("date1", date1);
		model.addAttribute("date2", date2);
		model.addAttribute("produit", produit);

		return "/reception/listReception";
	}


	@GetMapping("/add")
	public String formulaireAjout(Model model) {
		Reception reception = new Reception(); // reception.entrepot est une String maintenant
		model.addAttribute("reception", reception);

		List<Entrepot> entrepots = entrepotService.listeEntrepot();
		model.addAttribute("entrepots", entrepots);

		return "/reception/formReception"; // correspond à reception/formReception.html
	}



	@PostMapping("/enregistrer")
	public String enregistrer(@ModelAttribute Reception reception) {
		service.ajouterReception(reception);
		return "redirect:/reception";
	}

	@PutMapping
	public Reception updateContact(@RequestBody Reception r) {

		service.modifierReception(r);
		return r;
	}

	@DeleteMapping
	public void deleteContact(@RequestBody Reception r) {
		service.supprimerReception(r);
	}


	
	@GetMapping("/receptionner/{id}")
	public String receptionnerAchat(@PathVariable("id") String id, Model model) {
		service.receptionnerAchat(id);
		List<Reception> receptions = service.listeReception();
		model.addAttribute("receptions", receptions);

		return "/reception/listReception";
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
	@GetMapping("/achat")
	public String getAllAchat(Model model) {

		List<Achat> a = serviceAchat.listeAchat();
		model.addAttribute("achats", a);
		return "/achat/listAchat";
	}

}

/*@PostMapping("/api/reception")
	public LocalDate enregistrerReception(
	    @RequestBody Reception reception,
	    @RequestParam("dateLivraison") LocalDate dateLivraison
	){
		service.ajouterReception(reception);
		return dateLivraison;
	}*/
/*@PostMapping("/enregistrerReception")
public String enregistrerReception(
        @ModelAttribute("reception") Reception reception,
        @RequestParam("dateLivraison") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLivraison,
        Model model
) {
    // Traitement
    System.out.println("Produit : " + reception.getProduit());
    System.out.println("Date livraison : " + dateLivraison);
    return "confirmation"; // Page de confirmation
}

🔎 Remarques :

    @RequestParam("dateLivraison") permet de récupérer un champ simple.

    @DateTimeFormat est important pour que Spring comprenne le format yyyy-MM-dd venant d’un champ <input type="date">. */