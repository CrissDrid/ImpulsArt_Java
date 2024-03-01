package com.impulsart.ImpulsArtApp.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
