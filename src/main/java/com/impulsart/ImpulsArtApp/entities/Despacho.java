package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "referencia",length = 100, nullable = false)
    private String referencia;
    @Column(name = "estado",length = 100, nullable = false)
    private String estado;
    @Column(name = "comprobante",length = 100)
    private String comprobante;
    @Column(name = "FechaEntrega",length = 100)
    private LocalDate FechaEntrega;

    //FOREING KEY (asignacion de envios)
    @ManyToMany(mappedBy = "despacho")
    private List<Usuario> usuario;

    @ManyToOne
    @JoinColumn(name = "fk_venta", nullable = false)
    private Venta venta;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FkCod_Direccion",nullable = false)
    private Direccion direccion;

}
