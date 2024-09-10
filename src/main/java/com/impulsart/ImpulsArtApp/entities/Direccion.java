package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Direcciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pk_DireccionId")
    private Long id;

    @Column(name = "Departamento", length = 100, nullable = false)
    private String departamento;

    @Column(name = "Ciudad", length = 50, nullable = false)
    private String ciudad;

    @Column(name = "Direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "Observaciones", length = 155)
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "Fk_Identificacion", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "direccion", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Despacho> despacho;

}

