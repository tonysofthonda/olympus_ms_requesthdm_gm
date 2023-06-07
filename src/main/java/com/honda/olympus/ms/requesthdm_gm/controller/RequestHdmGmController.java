package com.honda.olympus.ms.requesthdm_gm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.honda.olympus.ms.requesthdm_gm.service.RequestHdmGmService;


@RestController
public class RequestHdmGmController 
{
	
	@Autowired
	private RequestHdmGmService requestHdmGmService;
	
	@GetMapping(path="/launch-process", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> launchProcess() 
	{
		requestHdmGmService.launchProcess();
		return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
	}
	
}
