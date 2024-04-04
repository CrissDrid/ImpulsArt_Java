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
public class TipoPQRS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_TipoPQRS")
    private long pkCod_TipoPQRS;
    @Column(name = "NombreTipo",length = 50)
    private String nombreTipo;
    @Column(name = "Descripcion",length = 50)
    private String descripcion;

}
