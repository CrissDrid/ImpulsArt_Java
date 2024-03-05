package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="domiciliarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domiciliario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Domiciliario")
    private long id;
    @Column(name = "salarios", length = 20)
    private int salario;
    @Column(name = "EntregasPendientes", length = 20)
    private int entregas;

    @ManyToMany
    @JoinTable(
            name = "Domiciliarios_Despachos",
            joinColumns = @JoinColumn(name = "fkCod_domiciliario", referencedColumnName = "pkCod_Domiciliario"),
            inverseJoinColumns = @JoinColumn(name = "fkCod_Despacho", referencedColumnName = "pkCod_Despacho")
    )

    private List<Despacho> despachos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Fk_Identificacion",nullable = false)
    private Usuario Usuarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Fk_placa",nullable = false)
    private Vehiculo vehiculos;

}
