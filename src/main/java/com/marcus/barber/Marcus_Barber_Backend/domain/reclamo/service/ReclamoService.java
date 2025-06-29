package com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.service;

import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.Reclamo;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.ReclamoRepository;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.dto.CrearReclamo;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.dto.DatosReclamo;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class ReclamoService {

    @Autowired
    private ReclamoRepository reclamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public DatosReclamo crearReclamo(CrearReclamo crearReclamo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Reclamo reclamo = Reclamo.builder()
                .canalAtencion(crearReclamo.canalAtencion())
                .categoriaServicio(crearReclamo.categoriaServicio())
                .descripcion(crearReclamo.descripcion())
                .fechaServicio(crearReclamo.fechaServicio())
                .tipo(crearReclamo.tipo())
                .solicitud(crearReclamo.solicitud())
                .usuario(usuario)
                .build();

        reclamoRepository.save(reclamo);
        return new DatosReclamo(reclamo);
    }

    public Page<DatosReclamo> listarReclamo(Pageable pageable) {
        return  reclamoRepository.findAll(pageable).map(DatosReclamo::new);
    }

    public Page<DatosReclamo> filtroReclamo(Pageable pageable, String tipo, String cliente) {
        return reclamoRepository.findByTipoOrUsuario_Nombre(pageable, tipo, cliente).map(DatosReclamo::new);
    }
}
