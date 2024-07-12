package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Categorias")
    private long pkCod_Categorias;

    @Column(name = "NombreCategoria",length = 45)
    private String nombreCategoria;

    @Column(name = "descripcionCategoria",length = 45)
    private String descripcionCategoria;

    @OneToMany(mappedBy = "categorias",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Obra> obras;

}
