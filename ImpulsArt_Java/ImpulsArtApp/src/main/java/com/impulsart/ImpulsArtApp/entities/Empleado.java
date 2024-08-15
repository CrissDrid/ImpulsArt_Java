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
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Fk_Identificacion", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<Asesor> asesor;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Domiciliario> domiciliario;

}
