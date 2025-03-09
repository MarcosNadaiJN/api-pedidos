package com.ms.produto.dtos;


import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoRecord(UUID id, @NotBlank String nome, BigDecimal preco, Long estoqueDisponivel) {
}
