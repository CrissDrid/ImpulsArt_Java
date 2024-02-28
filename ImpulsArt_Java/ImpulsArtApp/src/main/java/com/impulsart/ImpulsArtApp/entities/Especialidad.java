package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Especialidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pk_Especialidad")
    private int pk_Especialidad;
    @Column(name = "NombreEspecialidad",length = 150)
    private String nombreEspecialidad;
    @Column(name = "Descripcion",length = 150)
    private String descripcion;

    //FOREING KEY
    @ManyToMany(mappedBy = "especialidades")
    private List<Usuario> usuarios;
}
