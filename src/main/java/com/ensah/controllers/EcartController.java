package com.ensah.controllers;

import com.ensah.bo.*;

import org.springframework.web.bind.annotation.*;

import com.ensah.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("api/ecart")
public class EcartController {
	
	@Autowired
	private IEcartService service;
	
	@Autowired
	private IInventaireService service2;
	
	@GetMapping("/{id}")
	public ResponseEntity<List<Ecart>> getAllReception(@PathVariable("id") Long invantaireId) {
		
		Inventaire i = service2.selectionnerInventaire(invantaireId);
		
		List<Ecart> ecarts = service.getEcartsByInventaire(i);

		if (ecarts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(ecarts, HttpStatus.OK);
	}
	
	//lecture fichier jedid
	//creation deyal lfichier inventaire

}
