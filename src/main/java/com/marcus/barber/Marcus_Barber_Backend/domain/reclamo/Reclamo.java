package com.marcus.barber.Marcus_Barber_Backend.domain.reclamo;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reclamo")
@Entity(name = "reclamo")
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    private String tipo;

    private String categoriaServicio;

    private String canalAtencion;

    private Date fechaServicio;

    private String descripcion;

    private String solicitud;
}
