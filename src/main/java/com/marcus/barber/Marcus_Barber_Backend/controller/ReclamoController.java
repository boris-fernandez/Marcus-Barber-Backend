package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.dto.CrearReclamo;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.dto.DatosReclamo;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.service.ReclamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("reclamo")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    @PostMapping
    public ResponseEntity<DatosReclamo> crearReclamo(@RequestBody @Valid CrearReclamo crearReclamo, UriComponentsBuilder builder){
        DatosReclamo reclamo = reclamoService.crearReclamo(crearReclamo);
        URI uri = builder.path("/reclamo/{id}").buildAndExpand(reclamo.id()).toUri();

        return ResponseEntity.created(uri).body(reclamo);
    }


}
