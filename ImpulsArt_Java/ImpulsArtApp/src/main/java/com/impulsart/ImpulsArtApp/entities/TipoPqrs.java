package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tipoPQRS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPqrs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_TipoPQRS", nullable = false)
    private long pkCod_TipoPQRS;
    @Column(name = "NombreTipo",length = 50, nullable = false)
    private String nombreTipo;
    @Column(name = "Descripcion",length = 50, nullable = false)
    private String descripcion;

}
