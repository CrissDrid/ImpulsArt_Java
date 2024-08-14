package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="ReportesObras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Reporte")
    private long pkCod_Reporte;
    @Column(name = "Comentario", length = 200)
    private String comentario;
    @Column(name = "FechaReporte", length = 200)
    private LocalDate fechaReporte;

    @ManyToOne
    @JoinColumn(name = "Fk_Usuario", nullable = false)
    private Usuario usuario; // Relación con Usuario

    @ManyToOne
    @JoinColumn(name = "Fk_Obra", nullable = false)
    private Obra obra; // Relación con Obra

    @ManyToOne
    @JoinColumn(name = "Fk_TipoReporte", nullable = false)
    private TipoReporte tipoReporte; // Relación con Obra

}
