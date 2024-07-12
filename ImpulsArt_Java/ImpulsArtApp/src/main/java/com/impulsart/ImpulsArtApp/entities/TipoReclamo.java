package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tipoReclamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoReclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_TipoReclamo")
    private long pkCod_TipoReclamo;
    @Column(name = "NombreTipo",length = 50)
    private String nombreTipo;
    @Column(name = "Descripcion",length = 50)
    private String descripcion;

}
