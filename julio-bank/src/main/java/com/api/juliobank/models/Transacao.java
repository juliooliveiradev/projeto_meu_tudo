package com.api.juliobank.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name =  "transacao")
public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne()
    private Conta conta;
    @Column(nullable = false)
    private double valor;
    @Column(nullable = false)
    private LocalDateTime dataTransacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transacao)) return false;
        Transacao transacao = (Transacao) o;
        return Double.compare(transacao.getValor(), getValor()) == 0 && getId().equals(transacao.getId()) && getConta().equals(transacao.getConta()) && getDataTransacao().equals(transacao.getDataTransacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getConta(), getValor(), getDataTransacao());
    }
}
