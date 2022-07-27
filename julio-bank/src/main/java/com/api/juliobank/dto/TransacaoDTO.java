package com.api.juliobank.dto;

import com.api.juliobank.models.Conta;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class TransacaoDTO {

    @NotNull
    private Conta conta;
    @NotNull
    private double valor;

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
