package com.marcus.barber.Marcus_Barber_Backend.domain.reserva.service;

import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.Reserva;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.ReservaRepository;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.dto.CrearReserva;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.dto.DatosReserva;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosReserva crearReserva(CrearReserva crearReserva) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Reserva reserva = Reserva.builder()
                .turno(crearReserva.turno())
                .fecha(crearReserva.fecha())
                .detalles(crearReserva.detalles())
                .sede(crearReserva.sede())
                .estilista(crearReserva.estilista())
                .servicio(crearReserva.servicio())
                .usuario(usuario)
                .build();

        reservaRepository.save(reserva);

        return new DatosReserva(reserva);
    }

    public Page<DatosReserva> listarReserva(Pageable pageable) {
        return reservaRepository.findAll(pageable).map(reserva -> new DatosReserva(reserva));
    }


    public Page<DatosReserva> filtroReserva(Pageable pageable, String cliente, String estilista, String sede, Date fecha) {
        return reservaRepository.filtrarReservas(cliente, estilista, sede, fecha, pageable).map(reserva -> new DatosReserva(reserva));
    }

    public DatosReserva reservaPorId(long id) {
        if (!reservaRepository.existsById(id)){
            throw new ValidacionException("No existe la reserva");
        }
        return reservaRepository.findById(id).map(DatosReserva::new).get();
    }
}
