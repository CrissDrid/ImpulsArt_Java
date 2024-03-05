package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Empleado")
    private long id;

    @Column(name = "Salario",length = 100)
    private int Salario;
    @Column(name = "CasosPendientes",length = 100)
    private int CasosPendientes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Fk_Identificacion",nullable = false)
    private Usuario Usuarios;

    @OneToMany(mappedBy = "Empleados")
    private List<Pqrs> PQRS;

}
