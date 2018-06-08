package com.tab.code.purificadora.Model;

public class UsuariosBD {
    private String id;
    private String domicilio;
    private boolean admin;
    private String password;
    private Long telefono;
    private String username;

    public UsuariosBD() {
    }

    public UsuariosBD(String id, String domicilio, boolean admin, String password, Long telefono, String username) {
        this.id = id;
        this.domicilio = domicilio;
        this.admin = admin;
        this.password = password;
        this.telefono = telefono;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
