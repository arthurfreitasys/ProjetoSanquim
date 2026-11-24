package com.lab.jpa.projetosanquim.service;

import com.lab.jpa.projetosanquim.dto.LoginRequestDTO;
import com.lab.jpa.projetosanquim.dto.LoginResponseDTO;
import com.lab.jpa.projetosanquim.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );
        String perfil = authentication.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Usuário autenticado sem perfil"))
                .getAuthority();

        String token = jwtService.generateToken(authentication.getName(), perfil);
        return new LoginResponseDTO(token, jwtService.getExpirationMillis(), perfil);
    }
}