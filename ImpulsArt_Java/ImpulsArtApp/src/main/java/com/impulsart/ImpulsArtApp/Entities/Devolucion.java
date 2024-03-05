package com.impulsart.ImpulsArtApp.Entities;

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
    private long id;
    @Column(name = "FechaDevolucion", length = 200)
    private LocalDate fechaDevolucion;
    @Column(name = "TotalReembolso", length = 200)
    private int TotalReembolso;
    @Column(name = "ComprobanteReembolso", length = 200)
    private String ComprobanteReembolso;
    @Column(name = "TotalDevolver", length = 200)
    private int TotalDevolver;
}
