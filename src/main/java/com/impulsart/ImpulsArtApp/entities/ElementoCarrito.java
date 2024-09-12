package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    @Column(name = "costoIndividual",length = 200)
    private BigDecimal costoIndividual;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fk_carrito", nullable = false)
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "fk_obra", nullable = false)
    private Obra obra;


}
