package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "garantias")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PkCod_Garantia")
    private int PkCod_Garantia;

    @Column(name = "Durabilidad",length = 50)
    private String durabilidad;

    @Column(name = "Condiciones",length = 150)
    private String Condiciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FkCod_Producto",nullable = false)
    private Obra obras;

}
