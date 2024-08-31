package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "pkCod_TipoReporte", nullable = false)
    private long pkCod_TipoReporte;
    @Column(name = "Nombre", length = 60, nullable = false)
    private String Nombre;
    @Column(name = "Descripcion", length = 100, nullable = false)
    private String Descripcion;

    @OneToMany(mappedBy = "tipoReporte")
    @JsonIgnore
    private List<ReporteObra> reporteObras;

}

