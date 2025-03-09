package com.ms.pedido.controllers;

import com.ms.pedido.dtos.PedidoRecord;
import com.ms.pedido.entities.Pedido;
import com.ms.pedido.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public Pedido criaPedido(@RequestBody @Valid PedidoRecord pedidoRecord) {
        return this.pedidoService.criaPedido(pedidoRecord);
    }

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return this.pedidoService.getAllPedidos();
    }

    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable("id") UUID id) {
        return this.pedidoService.getPedidoById(id);
    }

    @PutMapping
    public Pedido atualizaPedido(@RequestBody @Valid PedidoRecord pedidoRecord) {
        return this.pedidoService.atualizaPedido(pedidoRecord);
    }
}
