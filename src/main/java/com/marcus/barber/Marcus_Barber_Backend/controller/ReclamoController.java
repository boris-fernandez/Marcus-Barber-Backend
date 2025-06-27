package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.model.reclamo.dto.CrearReclamo;
import com.marcus.barber.Marcus_Barber_Backend.model.reclamo.dto.DatosReclamo;
import com.marcus.barber.Marcus_Barber_Backend.model.reclamo.service.ReclamoService;
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

    @GetMapping
    public ResponseEntity<Page<DatosReclamo>> listarReclamo(@PageableDefault(size = 5) Pageable pageable,
                                                            @RequestParam(required = false) String tipo,
                                                            @RequestParam(required = false) String cliente){

        if (tipo == null && cliente == null){
            return ResponseEntity.ok(reclamoService.listarReclamo(pageable));
        }
        return ResponseEntity.ok(reclamoService.filtroReclamo(pageable, tipo, cliente));
    }

}
