package com.tab.code.purificadora.Model;

public class ClientesBD {

    private String idCliente;
    private String domicilio;
    private String nombre;
    private String telefono;
    private String pseudo;

    public ClientesBD() {
    }

    public ClientesBD(String domicilio, String nombre, String telefono, String pseudo) {
        this.domicilio = domicilio;
        this.nombre = nombre;
        this.telefono = telefono;
        this.pseudo = pseudo;
    }

    public ClientesBD(String domicilio, String nombre, String telefono, String pseudo, String cve) {
        this.domicilio = domicilio;
        this.nombre = nombre;
        this.telefono = telefono;
        this.pseudo = pseudo;
        this.idCliente = cve;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
