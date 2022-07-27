package com.api.juliobank.controller;

import com.api.juliobank.dto.ContaDto;
import com.api.juliobank.dto.UsuarioDTO;
import com.api.juliobank.models.Conta;
import com.api.juliobank.models.Usuario;
import com.api.juliobank.services.ContaService;
import com.api.juliobank.services.UsuarioService;
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
@RequestMapping("/Usuario")
public class UsuarioController {
    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){

        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> getAllUsuario(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUsuario(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if(!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encntrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao Encontrada");
        }
        usuarioService.delete(usuarioOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletada com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value = "id") Long id,
                                              @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encoontrada");
        }
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.setId(usuarioOptional.get().getId());
        usuario.setDataNascimento(usuarioOptional.get().getDataNascimento());
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.save(usuario));
    }
}
