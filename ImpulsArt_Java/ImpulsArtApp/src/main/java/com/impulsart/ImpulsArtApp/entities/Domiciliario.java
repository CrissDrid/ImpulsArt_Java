package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long pkCod_Domiciliario;
    @Column(name = "salario", length = 20)
    private int salario;
    @Column(name = "EntregasPendientes", length = 20)
    private int EntregasPendientes;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Domiciliarios_Despachos",
            joinColumns = @JoinColumn(name = "fkCod_domiciliario", referencedColumnName = "pkCod_Domiciliario"),
            inverseJoinColumns = @JoinColumn(name = "fkCod_Despacho", referencedColumnName = "pkCod_Despacho")
    )

    private List<Despacho> despachos;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "Fk_Identificacion",nullable = false)
    private Usuario Usuarios;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "Fk_placa",nullable = false)
    private Vehiculo vehiculos;

}
