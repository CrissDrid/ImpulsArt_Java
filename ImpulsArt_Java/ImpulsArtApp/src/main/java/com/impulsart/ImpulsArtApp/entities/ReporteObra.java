package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_obra", nullable = false)
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_reporte", nullable = false)
    private TipoReporte tipoReporte;

}
