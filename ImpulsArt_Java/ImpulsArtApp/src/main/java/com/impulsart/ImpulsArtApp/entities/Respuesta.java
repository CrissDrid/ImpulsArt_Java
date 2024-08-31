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
    @Column(name = "pkCod_Respuesta", nullable = false)
    private long pkCod_Respuesta;
    @Column(name = "Comentario", length = 200, nullable = false)
    private String comentario;
    @Column(name = "FechaRespuesta", length = 200, nullable = false)
    private LocalDate fechaRespuesta;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Fk_Identificacion", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fk_Pqrs", nullable = false)
    private Pqrs pqrs; // Relación con reclamo

}
