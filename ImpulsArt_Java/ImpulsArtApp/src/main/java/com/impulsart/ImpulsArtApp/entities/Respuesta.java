package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="Respuestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Respuesta")
    private long pkCod_Respuesta;
    @Column(name = "Comentario", length = 200)
    private String comentario;
    @Column(name = "FechaRespuesta", length = 200)
    private LocalDate fechaRespuesta;

    @ManyToOne
    @JoinColumn(name = "fk_Asesor", nullable = false)
    private Asesor asesor; // Relación con Obra

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fk_Reclamo", nullable = false)
    private Reclamo reclamo; // Relación con reclamo

}
