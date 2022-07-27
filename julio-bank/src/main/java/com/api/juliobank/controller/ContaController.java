package com.api.juliobank.controller;

import com.api.juliobank.dto.ContaDto;
import com.api.juliobank.dto.TransacaoDTO;
import com.api.juliobank.exceptions.ExceptionCustom;
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
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins= "*", maxAge = 3600)
@RequestMapping("/Conta")
public class ContaController {

    final ContaService contaService;
    final TransacaoService transacaoService;

    public ContaController(ContaService contaService, TransacaoService transacaoService) {
        this.contaService = contaService;
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Object> saveConta(@RequestBody @Valid ContaDto contaDTO){

        var conta = new Conta();
        BeanUtils.copyProperties(contaDTO, conta);
        conta.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(conta));
    }

    @GetMapping
    public ResponseEntity<Page<Conta>> getAllConta(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(contaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneConta(@PathVariable(value = "id") Long id){
        Optional<Conta> contaOptional = contaService.findById(id);
        if(!contaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta nao encntrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteConta(@PathVariable(value = "id") Long id){
        Optional<Conta> contaOptional = contaService.findById(id);
        if (!contaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta nao Encontrada");
        }
        contaService.delete(contaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Conta deletada com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateConta(@PathVariable(value = "id") Long id,
                                              @RequestBody @Valid ContaDto contaDTO){
        Optional<Conta> contaOptional = contaService.findById(id);
        if(!contaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta nao encoontrada");
        }
        var conta = new Conta();
        BeanUtils.copyProperties(contaDTO, conta);
        conta.setId(contaOptional.get().getId());
        conta.setDataCriacao(contaOptional.get().getDataCriacao());
        return ResponseEntity.status(HttpStatus.OK).body(contaService.save(conta));
    }

    @PutMapping(path = "deposito/{qtd}/{id}")
    public ResponseEntity<?> deposito(@PathVariable double qtd, @PathVariable Long id){
        contaService.depositar(qtd,id);
        return ResponseEntity.status(HttpStatus.OK).body("Deposito realizado com sucesso");
    }

    @GetMapping(path = "getSaldo/{id}")
    public ResponseEntity<?> getSaldo(@PathVariable Long id){
        Optional<Conta> contaOptional = contaService.buscarsaldo(id);
        return new ResponseEntity<>(contaOptional.get().getSaldo(), HttpStatus.OK);
    }

    @PutMapping(path = "sacar/{qtd}/{id}")
    public ResponseEntity<?> sacar(@PathVariable double qtd, @PathVariable Long id){
        if(qtd <= 0){
            throw new ExceptionCustom("valor incorreto");
        }
        this.contaService.saque(qtd,id);
        return ResponseEntity.status(HttpStatus.OK).body("Saque efetuado com sucesso");
    }

    @GetMapping(path = "extrato/{id}")
    public ResponseEntity<?> extrato(@PathVariable Long id){
        List<Transacao> transacoes = contaService.extratoConta(id);

        return ResponseEntity.status(HttpStatus.OK).body(transacoes);
    }

    @GetMapping(path = "buscarContaAtiva/{id}")
    public ResponseEntity<?> buscarContaAtiva(@PathVariable Long id){
        Optional<Conta> conta = contaService.checarConta(id);
        if(conta.get().isFlagAtivo() == false){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(conta.get().getUsuario(),HttpStatus.OK);
    }
    // id1 = id da conta do usuario
    // id2 = id da conta que o usuario deseja transferir
   @PutMapping(path = "realizarTransferencia/{qtd}/{id1}/{id2}")
    public ResponseEntity<?> realizarTransferencia(@PathVariable double qtd, @PathVariable Long id1,@PathVariable Long id2){
        contaService.depositar(qtd,id2);
        contaService.saque(qtd,id1);
        return ResponseEntity.status(HttpStatus.OK).body("Transferencia realizada com sucesso");
    }

    @PutMapping(path = "desfazerTransferencia/{qtd}/{id1}/{id2}")
    public ResponseEntity<?> desfazerTransferencia(@PathVariable double qtd, @PathVariable Long id1,@PathVariable Long id2){
        contaService.depositar(qtd,id1);
        contaService.saque(qtd,id2);
        return ResponseEntity.status(HttpStatus.OK).body("Transferencia realizada com sucesso");
    }

    @PutMapping(path = "parcelarTransferencia/{qtd}/{id1}/{id2}/{parcelas}")
    public ResponseEntity<?> parcelarTransferencia(@PathVariable double qtd, @PathVariable Long id1,@PathVariable Long id2, @PathVariable Long parcelas){
        var vlrtrans = qtd / parcelas;
        for(int i = 0; i < parcelas; i++) {
            contaService.depositar(vlrtrans, id2);
            contaService.saque(vlrtrans, id1);
            var transacao = new Transacao();
            var transacaoDTO = new TransacaoDTO();
            BeanUtils.copyProperties(transacaoDTO, transacao);
            transacao.setDataTransacao(LocalDateTime.now(ZoneId.of("UTC")).plusMonths(1));
            transacaoService.save(transacao);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Transferencia realizada com sucesso");
    }

}
