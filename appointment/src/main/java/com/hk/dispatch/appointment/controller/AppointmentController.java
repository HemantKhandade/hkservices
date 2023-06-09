package com.hk.dispatch.appointment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

		@GetMapping("/apptslots")
		public ResponseEntity<String> getAppointmentSlots(){
			return new ResponseEntity<String>("{'slots':['8AM-10AM,10AM-12PM,12PM-02PM,02PM-04PM']}", HttpStatus.OK);
		}
}
