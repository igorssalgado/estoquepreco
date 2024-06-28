package com.microservico.estoquepreco.dto;

//permite que o objeto seja transformado em uma sequencia de bytes, incluindo todos os dados do objeto
import java.io.Serializable;

public class EstoqueDTO implements Serializable {
	public String codigoProduto;
	public double quantidade;	
}
