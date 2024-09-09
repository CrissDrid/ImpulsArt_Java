package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ElementosCarritos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementoCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PkCod_Elemento")
    private Long PkCod_Elemento;
    @Column(name = "Cantidad",length = 200)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "fk_carrito", nullable = false)
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "fk_obra", nullable = false)
    private Obra obra;


}
