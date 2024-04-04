package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="devoluciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_CodDevolucion")
    private long pk_CodDevolucion;
    @Column(name = "FechaDevolucion", length = 200)
    private LocalDate fechaDevolucion;
    @Column(name = "TotalReembolso", length = 200)
    private int totalReembolso;
    @Column(name = "ComprobanteReembolso", length = 200)
    private String comprobanteReembolso;
    @Column(name = "TotalDevolver", length = 200)
    private int totalDevolver;
}
