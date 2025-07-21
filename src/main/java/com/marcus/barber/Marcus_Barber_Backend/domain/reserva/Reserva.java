package com.marcus.barber.Marcus_Barber_Backend.domain.reserva;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reserva")
@Entity(name = "reserva")
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    private String estilista;

    private String turno;

    private String sede;

    private Date fecha;

    private String servicio;

    private String detalles;
}
