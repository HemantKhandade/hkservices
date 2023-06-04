package com.hk.dispatch.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatchController {

		@GetMapping("/dispatch")
		public String getDispatch() {
			return "Hello World";
		}
}
