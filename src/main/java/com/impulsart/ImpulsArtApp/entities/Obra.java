package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private int pkCod_Producto;
    @Column(name = "NombreProducto",length = 200)
    private String nombreProducto;
    @Column(name = "Costo", nullable = false)
    private BigDecimal costo; // Cambiado a BigDecimal
    @Column(name = "Peso",length = 10,nullable = false)
    private String peso;
    @Column(name = "Tamano",length = 50,nullable = false)
    private String tamano;
    @Column(name = "Alto",length = 50,nullable = false)
    private String alto;
    @Column(name = "Ancho",length = 50,nullable = false)
    private String ancho;
    @Column(name = "Cantidad",nullable = false)
    private int cantidad;
    @Column(name = "Descripcion",length = 155,nullable = false)
    private String descripcion;
    @Column(name = "TipoImagen",length = 155,nullable = false)
    private String TipoImagen;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imagen;

    @OneToMany(mappedBy = "obras",cascade = CascadeType.ALL)
    private List<Subasta> subastas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FkCod_Categoria",nullable = false)
    private Categoria categoria;

    @ManyToMany(mappedBy = "obras")
    @JsonIgnore
    private List<Usuario> usuarios;

    //FOREING KEY
    @OneToMany(mappedBy = "obra", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ElementoCarrito> elementoCarrito;

    @OneToMany(mappedBy = "obra", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ReporteObra> reportesObras;

    // Método para eliminar la asociación con los usuarios
    @PreRemove
    private void removeUsuariosFromObra() {
        for (Usuario usuario : usuarios) {
            usuario.getObras().remove(this);
        }
    }

}
