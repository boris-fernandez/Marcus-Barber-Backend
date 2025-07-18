package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto.CategoriasProducto;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto.DatosProducto;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto.MarcasProducto;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

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

    @GetMapping("/{id}")
    public ResponseEntity<DatosProducto> productoPorId(@PathVariable long id){
        return ResponseEntity.ok(productoService.productoPorId(id));
    }

    @GetMapping("top")
    public ResponseEntity<List<DatosProducto>> top3Productos(){
        return ResponseEntity.ok(productoService.top3Productos());
    }

    @GetMapping("/marcas")
    public ResponseEntity<List<MarcasProducto>> marcasProducto(){
        return ResponseEntity.ok(productoService.marcasProducto());
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriasProducto>> categoriasProducto(){
        return ResponseEntity.ok(productoService.categoriasProducto());
    }
}
