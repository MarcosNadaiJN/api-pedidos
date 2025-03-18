package com.ms.usuario.services;

import com.ms.usuario.dtos.UsuarioDTO;
import com.ms.usuario.dtos.UsuarioRecord;
import com.ms.usuario.entities.Usuario;
import com.ms.usuario.repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario save(UsuarioRecord usuarioRecord) {
        if (this.usuarioRepository.existsById(usuarioRecord.id())) throw new RuntimeException("Usuario já existe");

        String passwordHash = new BCryptPasswordEncoder().encode(usuarioRecord.senha());
        Usuario usuario = new Usuario(usuarioRecord.email(), passwordHash, usuarioRecord.role());

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    public Usuario update(UsuarioRecord usuarioAtualizado) {
        Usuario usuarioSalvo = this.usuarioRepository.findById(usuarioAtualizado.id()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        BeanUtils.copyProperties(usuarioAtualizado, usuarioSalvo);
        return this.usuarioRepository.saveAndFlush(usuarioSalvo);
    }

    public UsuarioDTO findById(UUID id) {
        return this.usuarioRepository.findById(id).map(UsuarioDTO::toDTO).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    public void deleteById(UUID id) {
        this.usuarioRepository.deleteById(id);
    }
}
