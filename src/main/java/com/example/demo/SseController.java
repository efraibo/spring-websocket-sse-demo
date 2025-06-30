package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.time.Duration;

@CrossOrigin
@RestController
public class SseController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping(value = "/sse/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamEvents() {
        // Combina eventos periódicos com eventos de pedidos
        Flux<ServerSentEvent<String>> keepAlive = Flux.interval(Duration.ofSeconds(30))
                .map(seq -> ServerSentEvent.builder("Keep-alive #" + seq).build());

        Flux<ServerSentEvent<String>> pedidoEvents = pedidoService.getEventStream()
                .map(mensagem -> ServerSentEvent.builder(mensagem).build());

        return Flux.merge(keepAlive, pedidoEvents);
    }
}
