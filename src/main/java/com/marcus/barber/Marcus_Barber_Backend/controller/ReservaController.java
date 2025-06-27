package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.model.reserva.dto.CrearReserva;
import com.marcus.barber.Marcus_Barber_Backend.model.reserva.dto.DatosReserva;
import com.marcus.barber.Marcus_Barber_Backend.model.reserva.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<DatosReserva>> listarReserva(@PageableDefault(size = 5) Pageable pageable,
                                                            @RequestParam(required = false) String cliente,
                                                            @RequestParam(required = false) String estilista){

        if (cliente == null && estilista == null){
            return ResponseEntity.ok(reservaService.listarReserva(pageable));
        }

        return ResponseEntity.ok(reservaService.filtroReserva(pageable, cliente, estilista));
    }
}
