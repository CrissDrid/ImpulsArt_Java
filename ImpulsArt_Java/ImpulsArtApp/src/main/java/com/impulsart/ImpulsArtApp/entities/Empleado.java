package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long pkCod_Empleado;

    @Column(name = "Salario",length = 100)
    private int salario;
    @Column(name = "CasosPendientes",length = 100)
    private int casosPendientes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "Fk_Identificacion",nullable = false)
    private Usuario Usuarios;

    @OneToMany(mappedBy = "Empleados", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pqrs> PQRS;

}
