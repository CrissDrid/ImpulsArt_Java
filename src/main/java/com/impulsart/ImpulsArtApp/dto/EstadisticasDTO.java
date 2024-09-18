package com.impulsart.ImpulsArtApp.dto;

public class EstadisticasDTO {

    private int ventas;
    private int subastas;
    private int despachos;
    private int pqrs;
    private int usuarios;
    private int reportes;

    public EstadisticasDTO(int ventas, int subastas, int despachos, int pqrs, int usuarios, int reportes) {
        this.ventas = ventas;
        this.subastas = subastas;
        this.despachos = despachos;
        this.pqrs = pqrs;
        this.usuarios = usuarios;
        this.reportes = reportes;
    }
    // Getters y Setters

    public int getReportes() {
        return reportes;
    }

    public void setReportes(int reportes) {
        this.reportes = reportes;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getSubastas() {
        return subastas;
    }

    public void setSubastas(int subastas) {
        this.subastas = subastas;
    }

    public int getDespachos() {
        return despachos;
    }

    public void setDespachos(int despachos) {
        this.despachos = despachos;
    }

    public int getPqrs() {
        return pqrs;
    }

    public void setPqrs(int pqrs) {
        this.pqrs = pqrs;
    }

    public int getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(int usuarios) {
        this.usuarios = usuarios;
    }

}
