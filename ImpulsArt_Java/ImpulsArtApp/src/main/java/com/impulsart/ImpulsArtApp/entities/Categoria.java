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

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Categoria")
    private long pkCod_Categoria;

    @Column(name = "NombreCategoria",length = 45)
    private String nombreCategoria;

    @Column(name = "descripcionCategoria",length = 45)
    private String descripcionCategoria;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Obra> obras;

}
