package com.marcus.barber.Marcus_Barber_Backend.domain.producto.service;

import com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto.*;
import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.Producto;
import com.marcus.barber.Marcus_Barber_Backend.domain.producto.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @CachePut(value = "PRODUCTO", key = "'PRODUCTO_' + #id")
    @CacheEvict(value = {"PRODUCTO_LISTA", "MARCAS_PRODUCTO", "CATEGORIAS_PRODUCTO", "PRODUCTO"}, allEntries = true)
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

    @Cacheable(value = "PRODUCTO_LISTA", key = "'PRODUCTOS_' + #pageable.pageNumber")
    public Page<DatosProducto> listaProducto(Pageable pageable){
        Page<DatosProducto> productoList = productoRepository.findAll(pageable).map(producto -> new DatosProducto(producto));

        return productoList;
    }

    public Page<DatosProducto> filtrarProductosAdmin(String categoria, String marca, String nombre, String stock, Pageable pageable) {
        return productoRepository.buscarConFiltrosAdmin(nombre, marca, categoria, stock, pageable)
                .map(DatosProducto::new);
    }

    public Page<DatosProducto> filtrarProductos(String categoria, String marca, String nombre, Pageable pageable) {
        Page<DatosProducto> productos = productoRepository.buscarConFiltros(nombre, marca, categoria, pageable)
                .map(producto -> new DatosProducto(producto));

        return productos;
    }

    @CachePut(value = "PRODUCTO", key = "'PRODUCTO_' + #id")
    @CacheEvict(value = {"PRODUCTO_LISTA", "MARCAS_PRODUCTO", "CATEGORIAS_PRODUCTO", "FILTRO_PRODUCTO_ADMIN", "FILTRO_PRODUCTOS_CLIENTE" , "PRODUCTO"}, allEntries = true)
    public DatosProducto actualizarProducto(Long id, ActualizarProducto actualizarProducto){
        if(!productoRepository.existsById(id)){
            throw new ValidacionException("El producto no existe");
        }

        Producto producto = productoRepository.getReferenceById(id);
        producto.actualizar(actualizarProducto);

        productoRepository.save(producto);

        return new DatosProducto(producto);
    }

    @Caching(evict = {
            @CacheEvict(value = "PRODUCTO", key = "'PRODUCTO_' + #id"),
            @CacheEvict(value = {"PRODUCTO_LISTA", "MARCAS_PRODUCTO", "CATEGORIAS_PRODUCTO"}, allEntries = true)})
    public void eliminarProducto(long id) {
        if(!productoRepository.existsById(id)){
            throw new ValidacionException("El producto no existe");
        }
        Producto producto = productoRepository.getReferenceById(id);
        producto.actualizarEstado();

        productoRepository.save(producto);
    }

    @Cacheable(value = "TOP_PRODUCTOS" , key = "'TOP_PRODUCTOS'")
    public List<DatosProducto> top3Productos() {
        return productoRepository.top3Productos().stream().map(producto -> new DatosProducto(producto)).toList();
    }

    @Cacheable(value = "PRODUCTO", key = "'PRODUCTO_' + #id")
    public DatosProducto productoPorId(long id) {
        return productoRepository.findById(id)
                .map(DatosProducto::new)
                .orElseThrow(() -> new ValidacionException("El producto no existe"));
    }


    @Cacheable(value = "MARCAS_PRODUCTO", key = "'MARCAS'")
    public List<MarcasProducto> marcasProducto(){
        return productoRepository.findAllDistinctMarca().stream().map(MarcasProducto::new).toList();
    }

    @Cacheable(value = "CATEGORIAS_PRODUCTO", key = "'CATEGORIAS'")
    public List<CategoriasProducto> categoriasProducto() {
        return productoRepository.findAllDistinctCategoria().stream().map(CategoriasProducto::new).toList();
    }
}
