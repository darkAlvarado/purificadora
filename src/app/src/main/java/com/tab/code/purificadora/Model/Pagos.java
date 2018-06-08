package com.tab.code.purificadora.Model;

public class Pagos {
    private String fecha;
    private double pagoRealizado;
    private double adeudo;

    public Pagos() {
    }

    public Pagos(String fecha, double pagoRealizado, double adeudo) {
        this.fecha = fecha;
        this.pagoRealizado = pagoRealizado;
        this.adeudo = adeudo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(double pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }

    public double getAdeudo() {
        return adeudo;
    }

    public void setAdeudo(double adeudo) {
        this.adeudo = adeudo;
    }
}
