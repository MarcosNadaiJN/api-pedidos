package com.ms.usuario.dtos;

import com.ms.usuario.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UsuarioDTO {

    private UUID id;
    private String email;
    private String role;

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setRole(usuario.getRole().toString());
        return usuarioDTO;
    }
}
