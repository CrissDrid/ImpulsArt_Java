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
    private long id;

    @Column(name = "estado",length = 100)
    private String estado;
    @Column(name = "comprobante",length = 100)
    private String comprobante;
    @Column(name = "FechaEntrega",length = 100)
    private LocalDate FechaE;
    @Column(name = "Fecha_Venta",length = 100)
    private LocalDate Fecha_Venta;

    @ManyToMany(mappedBy = "despachos")
    private List<Domiciliario> domiciliarios;

}
