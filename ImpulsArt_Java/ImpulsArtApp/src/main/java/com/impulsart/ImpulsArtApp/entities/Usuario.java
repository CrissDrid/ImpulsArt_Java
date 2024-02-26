package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Entity
@Table(name= "Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @Column(name = "Pk_Identificacion")
    private int identificacion;
    @Column(name = "Nombre",length = 20,nullable = false)
    private String nombre;
    @Column(name = "Apellido",length = 20,nullable = false)
    private String apellido;
    @Column(name = "FechaNacimiento",nullable = false)
    private Date fechaNacimiento;
    @Column(name = "Email",nullable = false)
    private String email;
    @Column(name = "NumCelular",length = 15,nullable = false)
    private String numCelular;
    @Column(name = "Direccion",length = 50,nullable = false)
    private String direccion;
    @Column(name = "Contrasena",length = 40,nullable = false)
    private String contrasena;
    @Column(name = "TipoUsuario",length = 50,nullable = false)
    private String tipoUsuario;
}
