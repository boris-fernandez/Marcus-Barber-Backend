package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.dto.CrearReserva;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.dto.DatosReserva;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<DatosReserva> crearReserva(@RequestBody @Valid CrearReserva crearReserva, UriComponentsBuilder builder){
        DatosReserva datosReserva = reservaService.crearReserva(crearReserva);
        URI uri = builder.path("/reserva/{id}").buildAndExpand(datosReserva.id()).toUri();

        return ResponseEntity.created(uri).body(datosReserva);
    }
}
