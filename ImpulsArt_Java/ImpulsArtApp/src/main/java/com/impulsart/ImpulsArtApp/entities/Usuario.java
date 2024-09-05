package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name= "Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @Column(name = "Pk_Identificacion")
    private int identificacion;
    @Column(name = "Nombre", length = 20, nullable = false)
    private String nombre;
    @Column(name = "Apellido", length = 20, nullable = false)
    private String apellido;
    @Column(name = "userName", length = 20, nullable = false)
    private String userName;
    @Column(name = "FechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    @Column(name = "Email", nullable = false)
    private String email;
    @Column(name = "NumCelular", length = 15, nullable = false)
    private String numCelular;
    @Column(name = "Contrasena", columnDefinition = "TEXT", nullable = false)
    private String contrasena;
    @Column(name = "TipoUsuario", length = 50, nullable = false)
    private String tipoUsuario;

    @OneToMany(mappedBy = "Usuarios", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Oferta> ofertas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pqrs> pqrs;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Respuesta> respuesta;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Resena> resenas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Fk_Rol", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Direccion> direcciones;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Usuarios_Reportes",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Reporte", referencedColumnName = "pkCod_Reporte")
    )
    private List<ReporteObra> reporteObra;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Usuarios_Despachos",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Despacho", referencedColumnName = "pkCod_Despacho")
    )
    private List<Despacho> despacho;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Usuarios_Pedidos",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Pedido", referencedColumnName = "pk_CodPedido")
    )
    private List<PedidoPersonalizado> pedidoPersonalizados;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Usuarios_Obras",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Producto", referencedColumnName = "PkCod_Producto")
    )
    private List<Obra> obras;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Usuarios_Ventas",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "FkCod_Venta", referencedColumnName = "PkCod_Venta")
    )
    private List<Venta> ventas;

    // Relación ManyToMany con PQRs
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "Asesor_Pqrs",
            joinColumns = @JoinColumn(name = "Fk_Identificacion", referencedColumnName = "Pk_Identificacion"),
            inverseJoinColumns = @JoinColumn(name = "fkCod_Pqrs_asignado", referencedColumnName = "pkCod_Pqrs")
    )
    private List<Pqrs> pqrs_asignados;
}
