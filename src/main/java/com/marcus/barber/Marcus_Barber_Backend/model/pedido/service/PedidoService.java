package com.marcus.barber.Marcus_Barber_Backend.model.pedido.service;

import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.Estado;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.Pedido;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.PedidoRepository;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.detallesPedido.DetallesPedido;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.detallesPedido.DetallesPedidosRepository;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.dto.ActualizarPedido;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.dto.CrearPedido;
import com.marcus.barber.Marcus_Barber_Backend.model.pedido.dto.DatosPedido;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.Producto;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.ProductoRepository;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.Rol;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetallesPedidosRepository detallesPedidosRepository;

    private List<Producto> productoList;

    ArrayList<DetallesPedido> listaDetallesPedido = new ArrayList<>();

    public DatosPedido CrearPedido(CrearPedido crearPedido) {
        Usuario usuario = usuarioRepository.findById(crearPedido.idUsuario())
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado con ID: " + crearPedido.idUsuario()));

        productoList = crearPedido.detallesPedidoDTOS().stream().map(detallesPedidoDTO -> {
            return productoRepository.findById(detallesPedidoDTO.idProducto())
                    .orElseThrow(() -> new ValidacionException("El producto con el ID: " + detallesPedidoDTO.idProducto() + " no existe"));
        }).collect(Collectors.toList());

        //verificar stock
        verificarStock(crearPedido);

        //devolver total
        double total = calcularTotalPedido(crearPedido);

        long totalPedidos = pedidoRepository.count();
        String codigo = String.format("ORD-%05d", totalPedidos + 1);

        Date fecha = new Date();

        Pedido pedido = guardarPedido(usuario, codigo, fecha, total);

        pedidoRepository.save(pedido);

        //actualizarPedido
        actualizarPedido(crearPedido, pedido);

        return new DatosPedido(pedido, listaDetallesPedido);

    }

    public Page<DatosPedido> listarPedidos(Pageable pageable) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<DetallesPedido> todosLosDetalles = detallesPedidosRepository.findAll();

        List<DatosPedido> datosPedidos = pedidos.stream()
                .map(pedido -> {
                    List<DetallesPedido> detallesDeEstePedido = todosLosDetalles.stream()
                            .filter(detalle -> detalle.getPedido().getId().equals(pedido.getId()))
                            .toList();
                    return new DatosPedido(pedido, detallesDeEstePedido);
                })
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), datosPedidos.size());
        List<DatosPedido> datosPedidosPaginados = datosPedidos.subList(start, end);

        return new PageImpl<>(datosPedidosPaginados, pageable, datosPedidos.size());
    }

    public Page<DatosPedido> filtrarPedido(Pageable pageable, String codigo, String cliente) {
        List<Pedido> pedidoList = pedidoRepository.filtrarPorClienteOCodigo(cliente, codigo);
        List<DetallesPedido> todosLosDetalles = pedidoList.stream()
                .flatMap(pedido -> detallesPedidosRepository.findByPedido_Id(pedido.getId()).stream())
                .collect(Collectors.toList());

        List<DatosPedido> datosPedidosList = pedidoList.stream()
                .map(pedido -> {
                    List<DetallesPedido> detallesDeEstePedido = todosLosDetalles.stream()
                            .filter(detalle -> detalle.getPedido().getId().equals(pedido.getId()))
                            .collect(Collectors.toList());
                    return new DatosPedido(pedido, detallesDeEstePedido);
                }).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), datosPedidosList.size());
        List<DatosPedido> datosPedidosPaginados = datosPedidosList.subList(start, end);

        return new PageImpl<>(datosPedidosPaginados, pageable, datosPedidosList.size());
    }


    public DatosPedido actualizarEstadoPedido(long id, ActualizarPedido actualizarPedido) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("El producto con el id: " + id + " no existe"));

        List<DetallesPedido> detallesDeEstePedido = detallesPedidosRepository.findByPedido_Id(pedido.getId());

        pedido.setEstado(actualizarPedido.estado());

        pedidoRepository.save(pedido);

        return new DatosPedido(pedido, detallesDeEstePedido);
    }

    private void verificarStock(CrearPedido crearPedido){
        for (int i = 0; i < productoList.size(); i++) {
            Producto producto = productoList.get(i);
            int cantidadSolicitada = crearPedido.detallesPedidoDTOS().get(i).cantidad();
            int stockDisponible = productoRepository.buscarCantidadStock(producto.getId());

            if (cantidadSolicitada > stockDisponible) {
                throw new ValidacionException("El producto con ID: " + producto.getId() + " no tiene suficiente stock. Stock disponible: " + stockDisponible + ", solicitado: " + cantidadSolicitada);
            }
        }
    }

    private double calcularTotalPedido(CrearPedido crearPedido){
        double total = 0;
        for (int i = 0; i < productoList.size(); i++) {
            Producto producto = productoList.get(i);
            int cantidad = crearPedido.detallesPedidoDTOS().get(i).cantidad();
            total += producto.getPrecio() * cantidad;
        }
        return total;
    }

    private void actualizarPedido(CrearPedido crearPedido, Pedido pedido){
        for (int i = 0; i < productoList.size(); i++) {
            Producto producto = productoList.get(i);
            int cantidad = crearPedido.detallesPedidoDTOS().get(i).cantidad();

            int stock = productoRepository.buscarCantidadStock(producto.getId());
            producto.setStock(stock - cantidad);
            productoRepository.save(producto);

            DetallesPedido detallesPedido = DetallesPedido.builder()
                    .producto(producto)
                    .pedido(pedido)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecio())
                    .build();

            listaDetallesPedido.add(detallesPedido);
            detallesPedidosRepository.save(detallesPedido);
        }
    }

    private Pedido guardarPedido(Usuario usuario, String codigo, Date fecha, double total){
        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .codigo(codigo)
                .fecha(fecha)
                .estado(Estado.PENDIENTE)
                .total(total)
                .build();
        return pedido;
    }

}
