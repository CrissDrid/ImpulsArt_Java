package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Empleado")
    private long pkCod_Empleado;

    @Column(name = "Salario",length = 100)
    private int salario;
    @Column(name = "CasosPendientes",length = 100)
    private int casosPendientes;

}
