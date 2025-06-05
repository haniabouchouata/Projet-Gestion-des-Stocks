package com.ensah.controllers;

import com.ensah.bo.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.ensah.services.ISortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sortie")
public class SortieController {
	
	@Autowired
	private ISortieService service;

	@GetMapping
	public String getAllSortie(Model model) {

		List<Sortie> sorties = service.listeSortie();
		model.addAttribute("sorties",sorties);
		return "/sortie/listSortie";
	}
	/*@GetMapping("/{id}")
	public ResponseEntity<Sortie> getSortieById(@PathVariable("id") String id) {
		return ResponseEntity.ok().body(service.trouverSortie(id));
	}*/
	
	
	@GetMapping("/recherche")
	public List<Sortie> listeAchats(
	        @RequestParam(required = false) String numAchat,
	        @RequestParam(required = false) String produit) {
	    return service.rechercherSortie(numAchat, produit);
	}
	
	
	
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
