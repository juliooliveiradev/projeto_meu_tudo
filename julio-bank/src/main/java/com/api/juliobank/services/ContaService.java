package com.api.juliobank.services;

import com.api.juliobank.models.Conta;
import com.api.juliobank.models.Transacao;
import com.api.juliobank.repositories.ContaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    final ContaRepository contaRepository;
    final TransacaoService transacaoService;


    public ContaService(ContaRepository contaRepository, TransacaoService transacaoService) {
        this.contaRepository = contaRepository;
        this.transacaoService = transacaoService;
    }

    @Transactional
    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public Page<Conta> findAll(Pageable pageable) {
        return contaRepository.findAll(pageable);
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    @Transactional
    public void delete(Conta conta) {
        contaRepository.delete(conta);
    }

    public void depositar(double qtd, Long id) {
        contaRepository.setFixedSaldoFor(qtd,id);
    }

    public Optional<Conta> buscarsaldo(Long id) {
        return contaRepository.findById(id);
    }

    public void saque(double qtd, Long id) {
        contaRepository.setFixedSaldo(qtd, id);
    }

    public Optional<Conta> checarConta(Long id) {
        return contaRepository.findById(id);
    }

    public List<Transacao> extratoConta(Long id) {
        Collection<Optional<Conta>> conta = Collections.singleton(checarConta(id));
        return transacaoService.buscarContas(conta);
    }


}
