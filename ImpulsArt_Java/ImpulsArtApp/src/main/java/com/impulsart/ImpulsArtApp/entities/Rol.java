package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Rol")
    private long pkCod_Rol;

    @Column(name = "Nombre", length = 60)
    private String Nombre;

    @Column(name = "Descripcion", length = 100)
    private String Descripcion;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Fk_Identificacion", nullable = false)
    private Usuario usuario;

}
