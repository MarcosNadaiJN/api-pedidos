package com.ms.pedido.dtos;

import com.ms.pedido.entities.ItemPedido;

import java.util.List;
import java.util.UUID;

public record PedidoRecord(UUID id, UUID usuarioId, List<ItemPedidoRecord> itens) {
}
