package com.ms.pedido.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UsuarioDTO {

    private UUID id;
    private String email;
    private String role;
}
