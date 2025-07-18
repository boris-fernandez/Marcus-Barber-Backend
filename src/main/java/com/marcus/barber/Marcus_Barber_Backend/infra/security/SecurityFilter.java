package com.marcus.barber.Marcus_Barber_Backend.infra.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.UsuarioRepository;
import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.replace("Bearer ", "");
            String nombreUsuario = null;

            try {
                nombreUsuario = tokenService.getSubject(token);
            } catch (TokenExpiredException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token expirado\", \"message\": \"" + ex.getMessage() + "\"}");
                return; // Detener la cadena de filtros aquí
            } catch (ValidacionException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json"); // Asegurar que la respuesta es JSON
                response.getWriter().write("{\"error\": \"Token inválido\", \"message\": \"" + ex.getMessage() + "\"}");
                return; // Detener la cadena de filtros aquí
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json"); // Asegurar que la respuesta es JSON
                response.getWriter().write("{\"error\": \"Error interno del servidor\", \"message\": \"" + ex.getMessage() + "\"}");
                return; // Detener la cadena de filtros aquí
            }

            if (nombreUsuario != null) {
                var usuarioOptional = usuarioRepository.findByNombre(nombreUsuario);
                if (usuarioOptional.isPresent()) {
                    var usuario = usuarioOptional.get();
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
