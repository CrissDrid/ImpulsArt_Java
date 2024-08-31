package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="Reembolsos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reembolso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_CodReembolso")
    private long pk_CodReembolso;
    @Column(name = "FechaDevolucion", length = 200)
    private LocalDate fechaDevolucion;
    @Column(name = "TotalReembolso", length = 200)
    private int totalReembolso;
    @Column(name = "ComprobanteReembolso", length = 200)
    private String comprobanteReembolso;
    @Column(name = "TotalDevolver", length = 200)
    private int totalDevolver;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "fkCod_Pqrs",nullable = false)
    private Pqrs pqrs;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "fkCod_Venta",nullable = false)
    private Venta ventas;

}
