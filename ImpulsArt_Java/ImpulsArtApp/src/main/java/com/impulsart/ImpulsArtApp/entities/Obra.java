package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Clob;
import java.util.List;

@Entity
@Table(name = "Obras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PkCod_Producto")
    private int PkCod_Producto;
    @Column(name = "NombreProducto",length = 200)
    private String nombre;
    @Column(name = "Costo",nullable = false)
    private int costo;
    @Column(name = "Peso",length = 10,nullable = false)
    private String peso;
    @Column(name = "Tamano",length = 10,nullable = false)
    private String tamano;
    @Column(name = "Cantidad",nullable = false)
    private int cantidad;
    @Column(name = "Categoria",length = 50,nullable = false)
    private String categoria;
    @Column(name = "Descripcion",length = 155,nullable = false)
    private String descripcion;
    @Column(name = "Imagen")
    private Clob imagen;

    //FOREING KEY
    @OneToMany(mappedBy = "obras",cascade = CascadeType.ALL)
    private List<Venta> ventas;

    @OneToMany(mappedBy = "obras",cascade = CascadeType.ALL)
    private List<Subasta> subastas;

    @OneToMany(mappedBy = "obras")
    private List<Garantia> garantias;

    @ManyToMany(mappedBy = "obras")
    private List<Usuario> usuarios;

}
