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
