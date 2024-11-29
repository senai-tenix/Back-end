package com.senai.tenix.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senai.tenix.dto.TenixDto;
import com.senai.tenix.model.TenixModel;
import com.senai.tenix.repository.TenixRepository;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/tenis")
public class TenixController {

    private static final Logger logger = LoggerFactory.getLogger(TenixController.class);

    @Autowired
    private TenixRepository tenixRepository;

    // Listar todos os tênis
    @GetMapping
    public List<TenixModel> listar() {
        return tenixRepository.findAll();
    }

    // Adicionar um novo tênis
    @PostMapping("/cadastrar")
    public ResponseEntity<TenixModel> adicionar(@RequestBody TenixDto tenixDto) {
        try {
            TenixModel tenix = dtoToModel(tenixDto);
            TenixModel savedTenix = tenixRepository.save(tenix);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTenix);
        } catch (Exception e) {
            logger.error("Erro ao tentar salvar tênis: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Atualizar um tênis existente
    @PutMapping("/{id}")
    public ResponseEntity<TenixModel> atualizar(@PathVariable Long id, @RequestBody TenixDto tenixDto) {
        Optional<TenixModel> tenisOptional = tenixRepository.findById(id);

        if (tenisOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TenixModel tenis = tenisOptional.get();
        tenis.setNome(tenixDto.nome());
        tenis.setPreco(tenixDto.preco());
        tenis.setImg(tenixDto.img());

        tenixRepository.save(tenis);
        return ResponseEntity.ok(tenis);
    }

    // Deletar um tênis
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            tenixRepository.deleteById(id);
            logger.info("Tênis com id {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao tentar excluir tênis com id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método auxiliar para converter TenixDto para TenixModel
    private TenixModel dtoToModel(TenixDto tenixDto) {
        TenixModel tenix = new TenixModel();
        tenix.setNome(tenixDto.nome());
        tenix.setPreco(tenixDto.preco());
        tenix.setImg(tenixDto.img());
        return tenix;
    }
}
