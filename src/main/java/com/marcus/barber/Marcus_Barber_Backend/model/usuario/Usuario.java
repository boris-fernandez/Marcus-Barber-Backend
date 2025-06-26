package com.marcus.barber.Marcus_Barber_Backend.model.usuario;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuario")
@Entity(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String correo;

    private String celular;

    private String contrasena;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
