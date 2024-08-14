package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Asesores")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Asesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Asesor")
    private long pkCod_Asesor;

    @Column(name = "Disponibilidad",length = 50)
    private String disponibilidad;

    @Column(name = "EntregasPendientes")
    private int entregasPendientes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "FkCod_Empleado",nullable = false)
    private Empleado empleado;

    @OneToMany(mappedBy = "asesor")
    private List<Respuesta> respuestas;

    @OneToMany(mappedBy = "asesor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Calificacion> calificacion;

}
