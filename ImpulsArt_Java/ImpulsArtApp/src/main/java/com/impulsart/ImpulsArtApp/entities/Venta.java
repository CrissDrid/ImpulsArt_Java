package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
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
    private int PkCod_Venta;
    @Column(name = "FechaVenta",nullable = false)
    private Date fechaVenta;
    @Column(name = "TotalPago",nullable = false)
    private int totalPago;
    @Column(name = "ReciboVenta",length = 150,nullable = false)
    private String reciboVenta;
    @Column(name = "Cantidad",nullable = false)
    private int cantidad;
    @Column(name = "MetodoPago",length = 50,nullable = false)
    private String metodoPago;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FkCod_Producto",nullable = false)
    private Obra obras;

    //FOREING KEY
    @ManyToMany(mappedBy = "ventas")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "ventas")
    private List<Pqrs> PQRS;

    @OneToMany(mappedBy = "ventas")
    private List<Devolucion> devoluciones;
}
