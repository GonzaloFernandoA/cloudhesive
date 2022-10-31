package com.example.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.demoService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController

public class demoControler {

	@Autowired
	demoService servicio;
	
	@RequestMapping("")
	public @ResponseBody String GetDemo()
	{
		return servicio.getSaludo();
	}
		
}

