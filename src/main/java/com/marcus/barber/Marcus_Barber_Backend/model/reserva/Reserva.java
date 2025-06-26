package com.marcus.barber.Marcus_Barber_Backend.model.reserva;

import com.marcus.barber.Marcus_Barber_Backend.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reserva")
@Entity(name = "reserva")
public class Reserva {

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
