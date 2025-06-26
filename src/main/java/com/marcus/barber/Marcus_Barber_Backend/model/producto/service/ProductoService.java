package com.marcus.barber.Marcus_Barber_Backend.model.producto.service;

import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.Producto;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.dto.ActualizarProducto;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.dto.CrearProducto;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.ProductoRepository;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.dto.DatosProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public DatosProducto crearProducto(CrearProducto crearPedido){

        if (productoRepository.existsByNombre(crearPedido.nombre())){
            throw new ValidacionException("Ya existe un producto con ese nombre");
        }

        Producto producto = Producto.builder()
                .nombre(crearPedido.nombre())
                .marca(crearPedido.marca())
                .stock(crearPedido.stock())
                .precio(crearPedido.precio())
                .categoria(crearPedido.categoria())
                .imagenPrincipal(crearPedido.imagenPrincipal())
                .imagenSecundaria(crearPedido.imagenSecundaria())
                .imagenTerciaria(crearPedido.imagenTerciaria())
                .descripcion(crearPedido.descripcion())
                .build();

        productoRepository.save(producto);

        return new DatosProducto(producto);
    }

    public Page<DatosProducto> listaProducto(Pageable pageable){
        Page<DatosProducto> productoList = productoRepository.findAll(pageable).map(producto -> new DatosProducto(producto));

        return productoList;
    }

    public Page<DatosProducto> filtrarProductos(String categoria, String marca, String nombre, Pageable pageable) {
        Page<DatosProducto> productos = productoRepository.buscarConFiltros(nombre, marca, categoria, pageable)
                .map(producto -> new DatosProducto(producto));

        return productos;
    }

    public DatosProducto actualizarProducto(Long id, ActualizarProducto actualizarProducto){
        if(!productoRepository.existsById(id)){
            throw new ValidacionException("El producto no existe");
        }

        Producto producto = productoRepository.getReferenceById(id);
        producto.actualizar(actualizarProducto);

        productoRepository.save(producto);

        return new DatosProducto(producto);
    }

    public void eliminarProducto(long id) {
        if(!productoRepository.existsById(id)){
            throw new ValidacionException("El producto no existe");
        }
        Producto producto = productoRepository.getReferenceById(id);
        producto.actualizarEstado();

        productoRepository.save(producto);
    }

}
