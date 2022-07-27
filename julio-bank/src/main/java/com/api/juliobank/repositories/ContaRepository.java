package com.api.juliobank.repositories;

import com.api.juliobank.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Modifying
    @Query("update Conta conta set conta.saldo = conta.saldo + ?1 where conta.id = ?2")
    void setFixedSaldoFor(double qtd, Long id);



    @Modifying
    @Query("update Conta conta set conta.saldo = conta.saldo - ?1 where conta.id = ?2")
    void setFixedSaldo(double qtd, Long id);
}
