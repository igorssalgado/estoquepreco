package com.microservico.estoquepreco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservico.estoquepreco.constantes.RabbitmqConstantes;
import com.microservico.estoquepreco.dto.EstoqueDTO;
import com.microservico.estoquepreco.dto.PrecoDTO;
import com.microservico.estoquepreco.service.RabbitMQService;

@RestController
@RequestMapping(value = "preco")
public class PrecoController {
	
	@Autowired
	private RabbitMQService rabbitMQService;
	
	@PutMapping
	private ResponseEntity alteraPreco(@RequestBody PrecoDTO precoDTO) {
		
		System.out.println(precoDTO.preco);
		
		this.rabbitMQService.enviaMenssagem(RabbitmqConstantes.FILA_PRECO, precoDTO);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
