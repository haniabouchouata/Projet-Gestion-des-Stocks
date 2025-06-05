package com.ensah.controllers;

import com.ensah.bo.Achat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.ensah.services.IAchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/achat")

public class AchatController {

	@Autowired
	private IAchatService service;

	@GetMapping
	public String getAllAchat(Model model) {

		List<Achat> a = service.listeAchat();
		return "/achat/listAchat";
	}

	@GetMapping("/{id}")
	public ResponseEntity<Achat> getTransfertById(@PathVariable("id") String id) {
		return ResponseEntity.ok().body(service.trouverAchat(id));
	}


	@GetMapping("/recherche")
	public List<Achat> listeAchats(
	        @RequestParam(required = false) String numAchat,
	        @RequestParam(required = false) String produit) {
	    return service.rechercherAchat(numAchat, produit);
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
