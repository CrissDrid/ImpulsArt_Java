package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Pqrs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pqrs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Pqrs")
    private long pkCod_Pqrs;
    @Column(name = "Descripcion", length = 200)
    private String descripcion;
    @Column(name = "Estado", length = 200)
    private String estado;
    @Column(name = "FechaPQRS", length = 200)
    private LocalDate fechaPQRS;
    @Column(name = "FechaCierre", length = 200)
    private LocalDate fechaCierre;

    @ManyToMany(mappedBy = "pqrs")
    private List<Usuario> usuarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkCod_TipoPqrs",nullable = false)
    private TipoPqrs tipoPQRS;

    @OneToMany(mappedBy = "pqrs")
    @JsonIgnore
    private List<Reembolso> reembolso;

    @OneToMany(mappedBy = "pqrs", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Respuesta> respuestas;

}
