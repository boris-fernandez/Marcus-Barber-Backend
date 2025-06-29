package com.marcus.barber.Marcus_Barber_Backend.domain.pedido;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pedido")
@Entity(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    private Date fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total;
}
