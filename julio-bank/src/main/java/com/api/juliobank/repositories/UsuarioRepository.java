package com.api.juliobank.repositories;

import com.api.juliobank.models.Conta;
import com.api.juliobank.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


}
