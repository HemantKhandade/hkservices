package com.hk.dispatch.assigntech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assigntech")
public class AssignTechController {
	
	@GetMapping("/getassignedtech")
	public ResponseEntity<String> getAssignedTech(){
		return new ResponseEntity<String>("{'tech':'sd128b'}",HttpStatus.OK);
	}

}
