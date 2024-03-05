package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_placa")
    private long id;
    @Column(name = "Marca")
    private String marca;
    @Column(name = "TipoVehiculo")
    private String TipoVehiculo;
    @Column(name = "CantidadVehiculo")
    private int CantidadVehiculo;
    @Column(name = "modelo")
    private String modelo;

    @OneToMany(mappedBy = "vehiculos")
    private List<Domiciliario> domiciliarios;

}
