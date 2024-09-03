package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Carritos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PkCod_Carrito")
    private Long pkCod_Carrito;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Obras_Carrito",
            joinColumns = @JoinColumn(name = "FkCod_Carrito", referencedColumnName = "PkCod_Carrito"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Producto", referencedColumnName = "pkCod_Producto")
    )
    private List<Obra> obra;


}
