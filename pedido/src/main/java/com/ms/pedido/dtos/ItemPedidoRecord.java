package com.ms.pedido.dtos;

import java.util.UUID;

public record ItemPedidoRecord(UUID produtoId, Long quantidade) {
}
