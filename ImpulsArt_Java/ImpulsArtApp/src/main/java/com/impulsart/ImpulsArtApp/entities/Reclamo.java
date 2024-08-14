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
    @Column(name = "Estado", length = 200)
    private String estado;
    @Column(name = "FechaPQRS", length = 200)
    private LocalDate fechaPQRS;
    @Column(name = "FechaCierre", length = 200)
    private LocalDate fechaCierre;

    @ManyToMany(mappedBy = "reclamo")
    @JsonIgnore
    private List<Usuario> usuarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "fkCod_TipoReclamo",nullable = false)
    private TipoReclamo tipoReclamo;

    @OneToMany(mappedBy = "reclamo")
    @JsonIgnore
    private List<Reembolso> reembolso;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Respuesta> respuestas;

}
