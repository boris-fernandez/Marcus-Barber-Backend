package com.marcus.barber.Marcus_Barber_Backend.infra.security;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.UsuarioRepository;
import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticactionService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public final UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        if (!usuarioRepository.existsByNombreOrCorreo(nombre, nombre)){
            throw new ValidacionException("Credenciales incorrectas");
        }
        return usuarioRepository.findByNombreOrCorreo(nombre, nombre);
    }
}
