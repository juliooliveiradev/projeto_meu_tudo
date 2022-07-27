package com.api.juliobank.dto;

import com.api.juliobank.models.Transacao;
import com.api.juliobank.models.Usuario;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContaDto {
    @NotNull(message="Preenchimento obrigatório")
    private double saldo;
    @NotNull(message="Preenchimento obrigatório")
    private double limiteSaqueDiario;
    @NotNull
    private boolean flagAtivo;
    @NotBlank
    @Size(max = 50)
    private String tipo;
    @NotNull
    private Usuario usuario;
    @NotNull
    private Transacao transacao;

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
//@NotNull
    //private List<Transacao> transacoes;

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimiteSaqueDiario() {
        return limiteSaqueDiario;
    }

    public void setLimiteSaqueDiario(double limiteSaqueDiario) {
        this.limiteSaqueDiario = limiteSaqueDiario;
    }

    public boolean isFlagAtivo() {
        return flagAtivo;
    }

    public void setFlagAtivo(boolean flagAtivo) {
        this.flagAtivo = flagAtivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
