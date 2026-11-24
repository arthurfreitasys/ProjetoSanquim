package com.lab.jpa.projetosanquim.security;

import com.lab.jpa.projetosanquim.domain.enums.TipoUsuario;
import com.lab.jpa.projetosanquim.domain.model.Usuario;
import com.lab.jpa.projetosanquim.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuarioProcurado = Usuario.builder()
                .email(email)
                .build();
        Usuario usuario = usuarioRepository.findOne(Example.of(usuarioProcurado))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(new SimpleGrantedAuthority(mapearAuthority(usuario.getTipoUsuario())))
                .build();
    }

    private String mapearAuthority(TipoUsuario tipoUsuario) {
        return switch (tipoUsuario) {
            case GESTOR -> "ROLE_GESTAO";
            case PROFESSOR -> "ROLE_PROFESSOR";
            case ALUNO -> "ROLE_ALUNO";
        };
    }
}