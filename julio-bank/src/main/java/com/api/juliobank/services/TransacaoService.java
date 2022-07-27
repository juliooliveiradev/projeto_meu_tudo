package com.api.juliobank.services;

import com.api.juliobank.models.Conta;
import com.api.juliobank.models.Transacao;
import com.api.juliobank.repositories.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {
    final TransacaoRepository transacaoRepository ;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }


    @Transactional
    public Transacao save(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public Page<Transacao> findAll(Pageable pageable) {
        return transacaoRepository.findAll(pageable);
    }

    public Optional<Transacao> findById(Long id) {
        return transacaoRepository.findById(id);
    }

    @Transactional
    public void delete(Transacao transacao) {
        transacaoRepository.delete(transacao);
    }

    public List<Transacao> buscarContas(Collection<Optional<Conta>> conta) {
        return (List<Transacao>) transacaoRepository.findByContaIn(conta);
    }
}
