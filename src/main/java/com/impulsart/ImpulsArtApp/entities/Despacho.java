package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="despachos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Despacho")
    private Long pkCod_Despacho;

    @Column(name = "estado",length = 100, nullable = false)
    private String estado;
    @Column(name = "comprobante",length = 100)
    private String comprobante;
    @Column(name = "FechaEntrega",length = 100)
    private LocalDate FechaEntrega;
    @Column(name = "Fecha_Venta",length = 100)
    private LocalDate Fecha_Venta;

    //FOREING KEY (asignacion de envios)
    @ManyToMany(mappedBy = "despacho")
    private List<Usuario> usuario;

    // Relación uno a muchos con Ventas
    @OneToMany(mappedBy = "despacho")
    private List<Venta> ventas;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FkCod_Direccion",nullable = false)
    private Direccion direccion;

}
