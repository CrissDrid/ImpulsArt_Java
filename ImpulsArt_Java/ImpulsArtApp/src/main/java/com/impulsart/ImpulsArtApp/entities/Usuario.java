package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "Usuarios")
    private List<Oferta> ofertas;

    @OneToMany(mappedBy = "Usuarios")
    private List<Empleado> Empleados;

    @OneToMany(mappedBy = "Usuarios")
    private List<Domiciliario> domiciliarios;

    //FOREING KEY
    @ManyToMany
    @JoinTable(
            name = "Usuarios_Especialidades",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Especialidad", referencedColumnName = "pk_Especialidad")
    )
    private List<Especialidad> especialidades;

    @ManyToMany
    @JoinTable(
            name = "Usuarios_Pedido",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Pedido", referencedColumnName = "pk_CodPedido")
    )
    private List<PedidoPersonalizado> pedidoPersonalizados;

    @ManyToMany
    @JoinTable(
            name = "Usuarios_Obra",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Producto", referencedColumnName = "PkCod_Producto")
    )
    private List<Obra> obras;

    @ManyToMany
    @JoinTable(
            name = "Usuarios_Ventas",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Venta", referencedColumnName = "PkCod_Venta")
    )
    private List<Venta> ventas;

    @ManyToMany
    @JoinTable(
            name = "Usuarios_pqrs",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "fkCod_PQRS", referencedColumnName = "pkCod_PQRS")
    )
    private List<Pqrs> PQRS;
}
