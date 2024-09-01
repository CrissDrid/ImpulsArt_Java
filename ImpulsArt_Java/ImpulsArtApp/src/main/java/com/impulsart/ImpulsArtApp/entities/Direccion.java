package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "Calle", length = 100, nullable = false)
    private String calle;

    @Column(name = "Ciudad", length = 50, nullable = false)
    private String ciudad;

    @Column(name = "CodigoPostal", length = 10, nullable = false)
    private String codigoPostal;


    @ManyToOne
    @JoinColumn(name = "Fk_Identificacion", nullable = false)
    @JsonIgnore
    private Usuario usuario;
}

