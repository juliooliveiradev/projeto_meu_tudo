package com.api.juliobank.controller;

import com.api.juliobank.dto.ContaDto;
import com.api.juliobank.dto.TransacaoDTO;
import com.api.juliobank.models.Conta;
import com.api.juliobank.models.Transacao;
import com.api.juliobank.services.ContaService;
import com.api.juliobank.services.TransacaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@CrossOrigin(origins= "*", maxAge = 3600)
@RequestMapping("/Transacao")
public class TransacaoController {
    final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping(path = "saveTransacao/{parcelas}")
    public ResponseEntity<Object> saveTransacao(@PathVariable int parcelas ,@RequestBody @Valid TransacaoDTO transacaoDTO) {

        var transacao = new Transacao();
        BeanUtils.copyProperties(transacaoDTO, transacao);
        transacao.setDataTransacao(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.save(transacao));
    }

    @GetMapping
    public ResponseEntity<Page<Transacao>> getAllTransacao(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTransacao(@PathVariable(value = "id") Long id) {
        Optional<Transacao> transacaoOptional = transacaoService.findById(id);
        if (!transacaoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao nao encntrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transacaoOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransacao(@PathVariable(value = "id") Long id) {
        Optional<Transacao> transacaoOptional = transacaoService.findById(id);
        if (!transacaoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao nao Encontrada");
        }
        transacaoService.delete(transacaoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Transacao deletada com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTransacao(@PathVariable(value = "id") Long id,
                                                  @RequestBody @Valid TransacaoDTO transacaoDTO) {
        Optional<Transacao> transacaoOptional = transacaoService.findById(id);
        if (!transacaoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao nao encoontrada");
        }
        var transacao = new Transacao();
        BeanUtils.copyProperties(transacaoDTO, transacao);
        transacao.setId(transacaoOptional.get().getId());
        transacao.setDataTransacao(transacaoOptional.get().getDataTransacao());
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.save(transacao));

    }
}