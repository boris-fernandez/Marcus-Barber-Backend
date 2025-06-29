package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.Estado;
import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto.ActualizarPedido;
import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto.DatosPedido;
import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.service.PedidoService;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto.ActualizarProducto;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto.CrearProducto;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto.DatosProducto;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.service.ProductoService;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.dto.DatosReclamo;
import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.service.ReclamoService;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.dto.DatosReserva;
import com.marcus.barber.Marcus_Barber_Backend.domain.reserva.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReclamoService reclamoService;

    @Autowired
    private ReservaService reservaService;


    //Pedidos
    @GetMapping("/pedido")
    public ResponseEntity<Page<DatosPedido>> obtenerPedidos(@PageableDefault(size = 5) Pageable pageable,
                                                            @RequestParam(required = false) Estado estado,
                                                            @RequestParam(required = false) String cliente,
                                                            @RequestParam(required = false) Date fecha) {
        if(estado == null && cliente == null){
            Page<DatosPedido> pedidos = pedidoService.listarPedidos(pageable);
            return ResponseEntity.ok(pedidos);
        }
        return ResponseEntity.ok(pedidoService.filtrarPedido(pageable, estado, cliente, fecha));
    }

    @PatchMapping("/pedido/{id}")
    public ResponseEntity<DatosPedido> actualizarEstadoPedido(@PathVariable long id, @RequestBody @Valid ActualizarPedido actualizarPedido) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoPedido(id, actualizarPedido));
    }


    //Productos
    @GetMapping("/producto")
    public ResponseEntity<Page<DatosProducto>> listaProductos(@PageableDefault(size = 12) Pageable pageable,
                                                              @RequestParam(required = false) String categoria,
                                                              @RequestParam(required = false)  String marca,
                                                              @RequestParam(required = false) String nombre,
                                                              @RequestParam(required = false) String stock){
        if (nombre == null && marca == null && categoria == null && stock == null) {
            return ResponseEntity.ok(productoService.listaProducto(pageable));
        }

        return ResponseEntity.ok(productoService.filtrarProductosAdmin(categoria, marca, nombre, stock, pageable));
    }

    @PostMapping("/producto")
    public ResponseEntity<DatosProducto> crearProductoResponseEntity(@RequestBody @Valid CrearProducto producto, UriComponentsBuilder builder){
        DatosProducto productoCreado = productoService.crearProducto(producto);
        URI uri = builder.path("/producto/{id}").buildAndExpand(productoCreado.id()).toUri();

        return ResponseEntity.created(uri).body(productoCreado);
    }

    @PatchMapping("/producto/{id}")
    public ResponseEntity<DatosProducto> actualizarProducto(@PathVariable long id, @RequestBody @Valid ActualizarProducto actualizarProducto){
        return ResponseEntity.ok(productoService.actualizarProducto(id, actualizarProducto));
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }


    //Reclamos
    @GetMapping("/reclamo")
    public ResponseEntity<Page<DatosReclamo>> listarReclamo(@PageableDefault(size = 5) Pageable pageable,
                                                            @RequestParam(required = false) String tipo,
                                                            @RequestParam(required = false) String cliente){

        if (tipo == null && cliente == null){
            return ResponseEntity.ok(reclamoService.listarReclamo(pageable));
        }
        return ResponseEntity.ok(reclamoService.filtroReclamo(pageable, tipo, cliente));
    }

    //Reserva
    @GetMapping("/reserva")
    public ResponseEntity<Page<DatosReserva>> listarReserva(@PageableDefault(size = 5) Pageable pageable,
                                                            @RequestParam(required = false) String cliente,
                                                            @RequestParam(required = false) String estilista,
                                                            @RequestParam(required = false) String sede,
                                                            @RequestParam(required = false) Date fecha){

        if (cliente == null && estilista == null){
            return ResponseEntity.ok(reservaService.listarReserva(pageable));
        }

        return ResponseEntity.ok(reservaService.filtroReserva(pageable, cliente, estilista, sede, fecha));
    }
}
