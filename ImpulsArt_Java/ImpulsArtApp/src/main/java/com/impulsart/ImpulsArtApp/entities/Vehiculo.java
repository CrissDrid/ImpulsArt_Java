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
    @Column(name = "pk_placa")
    private long pk_placa;
    @Column(name = "Marca")
    private String marca;
    @Column(name = "TipoVehiculo")
    private String tipoVehiculo;
    @Column(name = "CantidadVehiculo")
    private int cantidadVehiculo;
    @Column(name = "modelo")
    private String modelo;

    @OneToMany(mappedBy = "vehiculos")
    private List<Domiciliario> domiciliarios;

}
