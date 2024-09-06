package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name= "PedidosPersonalizados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPersonalizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pk_CodPedido")
    private int pk_CodPedido;
    @Column(name = "Color", length = 50)
    private String color;
    @Column(name = "Materiales",length = 150)
    private String materiales;
    @Column(name = "Contenido",length = 250,nullable = false)
    private String contenido;
    @Column(name = "Tamano",length = 255)
    private String tamano;
    @Column(name = "EstadoPedido",length = 80,nullable = false)
    private String estadoPedido;

    //FOREING KEY
    @ManyToMany(mappedBy = "pedidoPersonalizados")
    private List<Usuario> usuarios;
}
