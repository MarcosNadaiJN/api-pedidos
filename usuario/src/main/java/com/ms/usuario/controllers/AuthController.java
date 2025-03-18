package com.ms.usuario.controllers;

import com.ms.usuario.dtos.AuthRecord;
import com.ms.usuario.dtos.UsuarioRecord;
import com.ms.usuario.entities.Usuario;
import com.ms.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRecord authRecord) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authRecord.email(), authRecord.senha());
            var auth = authenticationManager.authenticate(usernamePassword);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Erro na autenticação: " + e.getMessage());
        }
    }


    @PostMapping("/register")
    public Usuario register(@RequestBody @Valid UsuarioRecord produto) {
        return this.usuarioService.save(produto);
    }
}
