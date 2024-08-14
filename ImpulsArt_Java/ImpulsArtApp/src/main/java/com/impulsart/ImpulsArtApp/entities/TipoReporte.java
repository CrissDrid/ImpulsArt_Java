package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="TiposReportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_TipoReporte")
    private long pkCod_TipoReporte;
    @Column(name = "Nombre", length = 60)
    private String Nombre;
    @Column(name = "Descripcion", length = 100)
    private String Descripcion;

    @OneToMany(mappedBy = "tipoReporte")
    private List<ReporteObra> reporteObras;

}
