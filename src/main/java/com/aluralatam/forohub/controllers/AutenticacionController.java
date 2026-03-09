package com.aluralatam.forohub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluralatam.forohub.dtos.DatosAutenticacionUsuario;
import com.aluralatam.forohub.entities.Usuario;
import com.aluralatam.forohub.infra.DatosJWTToken;
import com.aluralatam.forohub.infra.TokenService;

@RestController
@RequestMapping
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<DatosJWTToken> login(@RequestBody DatosAutenticacionUsuario datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String jwt = tokenService.generarToken(usuario);
        return ResponseEntity.ok(new DatosJWTToken(jwt));
    }
}
