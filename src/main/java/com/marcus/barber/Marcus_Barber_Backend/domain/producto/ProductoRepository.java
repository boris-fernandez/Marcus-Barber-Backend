package com.marcus.barber.Marcus_Barber_Backend.domain.producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByMarcaAndCategoria(String marca, String categoria);

    List<Producto> findByMarca(String marca);

    List<Producto> findByCategoria(String categoria);


    @Query("SELECT p FROM producto p WHERE " +
            "(:nombre IS NULL OR p.nombre = :nombre) AND " +
            "(:marca IS NULL OR p.marca = :marca) AND " +
            "(:categoria IS NULL OR p.categoria = :categoria)")
    Page<Producto> buscarConFiltros(String nombre, String marca, String categoria, Pageable pageable);

    @Query("""
    SELECT p FROM producto p
    WHERE 
        (:nombre IS NULL OR p.nombre = :nombre) AND
        (:marca IS NULL OR p.marca = :marca) AND
        (:categoria IS NULL OR p.categoria = :categoria) AND
        (
            :stock IS NULL OR :stock = 'todo' OR
            (:stock = 'bajo' AND p.stock > 0 AND p.stock <= 10) OR
            (:stock = 'sin' AND p.stock = 0)
        )
    """)
    Page<Producto> buscarConFiltrosAdmin(String nombre, String marca, String categoria, String stock, Pageable pageable);


    boolean existsByNombre(String nombre);

    @Query("select p.stock from producto  p " +
            "where p.id = :id")
    int buscarCantidadStock(long id);

    @Query("select p from producto p " +
            "inner join detallePedido dp on p.id = dp.producto.id " +
            "group by p.id " +
            "order by count(dp.pedido) desc " +
            "limit 3")
    List<Producto> top3Productos();

}
