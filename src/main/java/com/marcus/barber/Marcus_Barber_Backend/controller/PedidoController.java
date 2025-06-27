package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.model.pedido.Estado;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.dto.ActualizarPedido;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.dto.CrearPedido;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.dto.DatosPedido;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<DatosPedido>> obtenerPedidos(@PageableDefault(size = 5) Pageable pageable,
                                                            @RequestParam(required = false) String codigo,
                                                            @RequestParam(required = false) String cliente) {

        if(codigo == null && cliente == null){
            Page<DatosPedido> pedidos = pedidoService.listarPedidos(pageable);
            return ResponseEntity.ok(pedidos);
        }
        return ResponseEntity.ok(pedidoService.filtrarPedido(pageable, codigo, cliente));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<DatosPedido> actualizarEstadoPedido(@PathVariable long id, @RequestBody @Valid ActualizarPedido actualizarPedido) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoPedido(id, actualizarPedido));
    }

}
