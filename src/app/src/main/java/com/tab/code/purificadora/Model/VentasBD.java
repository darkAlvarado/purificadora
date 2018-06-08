package com.tab.code.purificadora.Model;

import java.util.Date;

public class VentasBD {

    private String user_id;
    private String fecha;
    private int cantidad;
    private double precio;
    private double total;
    private boolean tipo_pago;
    private String prefPago;
    private double pagoRealizado;
    public VentasBD() {
    }

    public VentasBD(String user_id, String fecha, int cantidad, double precio, double total, boolean tipo_pago, String prefPago) {
        this.user_id = user_id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.tipo_pago = tipo_pago;
        this.prefPago = prefPago;
    }

    public VentasBD( String fecha, int cantidad, double precio, double total, boolean tipo_pago, String prefPago, double pagoRealizado) {
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.tipo_pago = tipo_pago;
        this.prefPago = prefPago;
        this.pagoRealizado = pagoRealizado;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(boolean tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public String getPrefPago() {
        return prefPago;
    }

    public void setPrefPago(String prefPago) {
        this.prefPago = prefPago;
    }

    public double getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(double pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }
}
