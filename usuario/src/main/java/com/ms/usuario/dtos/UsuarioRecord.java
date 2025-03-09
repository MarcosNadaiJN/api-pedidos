package com.ms.usuario.dtos;

import com.ms.usuario.enums.Role;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioRecord(UUID id, @NotNull String email, @NotNull String senha, @NotNull Role role) {
}
