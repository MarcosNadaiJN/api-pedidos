package com.ms.produto.controllers;

import com.ms.produto.dtos.ProdutoRecord;
import com.ms.produto.entities.Produto;
import com.ms.produto.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public Produto save(@RequestBody @Valid ProdutoRecord produto) {
        return this.produtoService.save(produto);
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable("id") UUID id) {
        return this.produtoService.findById(id);
    }

    @GetMapping
    public List<Produto> findAll() {
        return this.produtoService.findAll();
    }

    @PutMapping("/{id}")
    public Produto update(@RequestBody @Valid ProdutoRecord produtoRecord) {
        return this.produtoService.update(produtoRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        this.produtoService.deleteById(id);
    }

    @GetMapping("/estoque/{id}")
    public Long getQuantidadeEstoqueProduto(@PathVariable("id") UUID id) {
        return this.produtoService.getQuantidadeEstoqueProduto(id);
    }
}
