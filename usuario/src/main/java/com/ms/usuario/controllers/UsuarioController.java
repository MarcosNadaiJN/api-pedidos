package com.ms.usuario.controllers;

import com.ms.usuario.dtos.UsuarioDTO;
import com.ms.usuario.dtos.UsuarioRecord;
import com.ms.usuario.entities.Usuario;
import com.ms.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario save(@RequestBody @Valid UsuarioRecord produto) {
        return this.usuarioService.save(produto);
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable("id") UUID id) {
        return this.usuarioService.findById(id);
    }

    @GetMapping()
    public List<Usuario> findAll() {
        return this.usuarioService.findAll();
    }

    @PutMapping("/{id}")
    public Usuario update(@RequestBody @Valid UsuarioRecord produtoRecord) {
        return this.usuarioService.update(produtoRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        this.usuarioService.deleteById(id);
    }
}
