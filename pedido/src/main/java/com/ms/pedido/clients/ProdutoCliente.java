package com.ms.pedido.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "produto-service", url = "http://localhost:8083")
public interface ProdutoCliente {

    @GetMapping("/produtos/estoque/{id}")
    Long quantidadeEstoque(@PathVariable("id") UUID id);
}
