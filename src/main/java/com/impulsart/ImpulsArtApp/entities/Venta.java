package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PkCod_Venta")
    private int pkCod_Venta;
    @Column(name = "FechaVenta",nullable = false)
    private LocalDate fechaVenta;
    @Column(name = "TotalPago",nullable = false)
    private int totalPago;
    @Column(name = "ReciboVenta",length = 150,nullable = false)
    private String reciboVenta;
    @Column(name = "Cantidad",nullable = false)
    private int cantidad;
    @Column(name = "MetodoPago",length = 50,nullable = false)
    private String metodoPago;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FkCod_Producto",nullable = false)
    private Obra obras;

    // Relación muchos a uno con Despacho
    @ManyToOne
    @JoinColumn(name = "fkCod_Despacho") // La clave foránea en la tabla de ventas
    private Despacho despacho;

    //FOREING KEY
    @ManyToMany(mappedBy = "ventas", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "ventas", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reembolso> reembolso;
}
