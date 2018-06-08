package com.tab.code.purificadora.Model;

import java.util.Date;

public class CreditoBD {

    private double totalAdeudo;
    private double ultimoPago;
    private String fechaPago;
    private Historico historico;

    public CreditoBD() {
    }

    public CreditoBD(double totalAdeudo, double ultimoPago, String fechaPago, Historico historico) {
        this.totalAdeudo = totalAdeudo;
        this.ultimoPago = ultimoPago;
        this.fechaPago = fechaPago;
        this.historico = historico;
    }

    public double getTotalAdeudo() {
        return totalAdeudo;
    }

    public void setTotalAdeudo(double totalAdeudo) {
        this.totalAdeudo = totalAdeudo;
    }

    public double getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(double ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }
}
