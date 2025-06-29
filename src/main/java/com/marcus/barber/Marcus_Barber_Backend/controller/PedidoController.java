package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto.CrearPedido;
import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto.DatosPedido;
import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
;

@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<DatosPedido> CrearPedido(@RequestBody @Valid CrearPedido crearPedido, UriComponentsBuilder builder){
        DatosPedido datosPedido = pedidoService.CrearPedido(crearPedido);
        URI uri = builder.path("/pedido/{id}").buildAndExpand(datosPedido.id()).toUri();
        return ResponseEntity.created(uri).body(datosPedido);
    }

}
