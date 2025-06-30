package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    // Sink para broadcast de eventos SSE
    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(String descricao, Double valor) {
        Pedido pedido = new Pedido(descricao, valor);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Notificar todos os clientes SSE conectados
        String mensagem = "Novo pedido criado! ID: " + pedidoSalvo.getId() +
                         ", Descrição: " + pedidoSalvo.getDescricao() +
                         ", Valor: R$ " + pedidoSalvo.getValor();

        sink.tryEmitNext(mensagem);

        return pedidoSalvo;
    }

    public Flux<String> getEventStream() {
        return sink.asFlux();
    }
}
