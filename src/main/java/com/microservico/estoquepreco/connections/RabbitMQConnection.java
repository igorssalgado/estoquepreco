package com.microservico.estoquepreco.connections;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import com.microservico.estoquepreco.constantes.RabbitmqConstantes;

import jakarta.annotation.PostConstruct;

//notacao pra informar o spring que é essa classe que vai ser autogerenciado pelo spring (vai instanciar essa classe ao iniciar a app) 
@Component
public class RabbitMQConnection {
	
	private static final String NOME_EXCHANGE = "amq.direct";
	private AmqpAdmin amqpAdmin;
	
	//amqpAdmin é autoconfiguravel
	public RabbitMQConnection(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	private Queue fila(String nomedafila) {
		return new Queue(nomedafila, true, false, false);
	}

	private DirectExchange trocaDireta() {
		return new DirectExchange(NOME_EXCHANGE);
	}
	
	private Binding relacionamento(Queue fila, DirectExchange troca) {
		return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
	}

	@PostConstruct
	private void adiciona() {
		Queue filaEstoque = this.fila(RabbitmqConstantes.FILA_ESTOQUE);
		Queue filaPreco = this.fila(RabbitmqConstantes.FILA_PRECO);
		
		DirectExchange troca = this.trocaDireta();
		
		Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
		Binding ligacaoPreco = this.relacionamento(filaPreco, troca);
		
		//criando as filas no rabbitMQ
		this.amqpAdmin.declareQueue(filaEstoque);
		this.amqpAdmin.declareQueue(filaPreco);
		
		this.amqpAdmin.declareExchange(troca);
		
		this.amqpAdmin.declareBinding(ligacaoEstoque);
		this.amqpAdmin.declareBinding(ligacaoPreco);
		
	}
}
