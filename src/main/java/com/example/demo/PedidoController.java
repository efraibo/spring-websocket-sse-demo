package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoRequest request) {
        Pedido pedido = pedidoService.criarPedido(request.descricao(), request.valor());
        return ResponseEntity.ok(pedido);
    }

    public record PedidoRequest(String descricao, Double valor) {}
}
