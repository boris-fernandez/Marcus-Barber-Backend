package com.marcus.barber.Marcus_Barber_Backend.model.reserva.service;

import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.model.reserva.Reserva;
import com.marcus.barber.Marcus_Barber_Backend.model.reserva.ReservaRepository;
import com.marcus.barber.Marcus_Barber_Backend.model.reserva.dto.CrearReserva;
import com.marcus.barber.Marcus_Barber_Backend.model.reserva.dto.DatosReserva;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosReserva crearReserva(CrearReserva crearReserva) {
        Usuario usuario = usuarioRepository.findById(crearReserva.idCliente())
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado con ID: " + crearReserva.idCliente()));

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


    public Page<DatosReserva> filtroReserva(Pageable pageable, String cliente, String estilista) {
        return reservaRepository.findByUsuario_NombreOrEstilista(cliente, estilista, pageable).map(reserva -> new DatosReserva(reserva));
    }
}
