package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="domiciliarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domiciliario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Domiciliario")
    private long id;
    @Column(name = "salarios", length = 20)
    private int salario;
    @Column(name = "EntregasPendientes", length = 20)
    private int entregas;
}
