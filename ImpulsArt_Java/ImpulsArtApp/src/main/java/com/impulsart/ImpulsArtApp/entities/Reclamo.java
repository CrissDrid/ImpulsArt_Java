package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Reclamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Reclamo")
    private long pkCod_Reclamo;
    @Column(name = "Descripcion", length = 200)
    private String descripcion;
    @Column(name = "Motivo", length = 200)
    private String motivo;
    @Column(name = "Respuesta", length = 200)
    private String respuesta;
    @Column(name = "Estado", length = 200)
    private String estado;
    @Column(name = "FechaPQRS", length = 200)
    private LocalDate fechaPQRS;
    @Column(name = "FechaCierre", length = 200)
    private LocalDate fechaCierre;

    @ManyToMany(mappedBy = "reclamo")
    private List<Usuario> usuarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "FkCod_Venta",nullable = true)
    private Venta ventas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "fkCod_Asesor",nullable = true)
    private Asesor asesor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "fkCod_TipoReclamo",nullable = false)
    private TipoReclamo tipoReclamo;

    @OneToMany(mappedBy = "reclamo")
    @JsonIgnore
    private List<Reembolso> reembolso;

}
