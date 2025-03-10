package com.ms.pedido.dtos;

import java.util.List;
import java.util.UUID;

public record PedidoRecord(UUID id, UUID usuarioId, List<ItemPedidoRecord> itens) {
   public PedidoRecord() {
       this(null, null, null);
   }
}
