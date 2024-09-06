package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "Nombre", length = 60, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", length = 100, nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Usuario> usuarios;

}
