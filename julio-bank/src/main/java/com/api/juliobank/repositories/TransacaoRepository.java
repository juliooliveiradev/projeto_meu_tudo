package com.api.juliobank.repositories;

import com.api.juliobank.models.Conta;
import com.api.juliobank.models.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional(readOnly = false)
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {



    Collection<Transacao> findByContaIn(Collection<Optional<Conta>> conta);
}
