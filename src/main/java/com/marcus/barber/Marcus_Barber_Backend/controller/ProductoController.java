package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.model.producto.dto.ActualizarProducto;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.dto.CrearProducto;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.dto.DatosProducto;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.net.URI;

@RestController
@RequestMapping("producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<DatosProducto> crearProductoResponseEntity(@RequestBody @Valid CrearProducto producto, UriComponentsBuilder builder){
        DatosProducto productoCreado = productoService.crearProducto(producto);
        URI uri = builder.path("/producto/{id}").buildAndExpand(productoCreado.id()).toUri();

        return ResponseEntity.created(uri).body(productoCreado);
    }

    @GetMapping
    public ResponseEntity<Page<DatosProducto>> listaProductos(@PageableDefault(size = 12) Pageable pageable,
                                                              @RequestParam(required = false) String categoria,
                                                              @RequestParam(required = false)  String marca,
                                                              @RequestParam(required = false) String nombre){
        if (nombre == null && marca == null && categoria == null) {
            return ResponseEntity.ok(productoService.listaProducto(pageable));
        }

        return ResponseEntity.ok(productoService.filtrarProductos(categoria, marca, nombre, pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DatosProducto> actualizarProducto(@PathVariable long id, @RequestBody ActualizarProducto actualizarProducto){
        return ResponseEntity.ok(productoService.actualizarProducto(id, actualizarProducto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
