package com.ms.produto.services;

import com.ms.produto.dtos.ProdutoRecord;
import com.ms.produto.entities.Produto;
import com.ms.produto.repositories.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto save(ProdutoRecord produtoRecord) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoRecord, produto);
        return this.produtoRepository.saveAndFlush(produto);
    }

    public Produto update(ProdutoRecord produtoAtualizado) {
        Produto produtoSalvo = this.produtoRepository.findById(produtoAtualizado.id()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        BeanUtils.copyProperties(produtoAtualizado, produtoSalvo);
        return this.produtoRepository.saveAndFlush(produtoSalvo);
    }

    public Produto findById(UUID id) {
        return this.produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public List<Produto> findAll() {
        return this.produtoRepository.findAll();
    }

    public void deleteById(UUID id) {
        this.produtoRepository.deleteById(id);
    }

    public Long getQuantidadeEstoqueProduto(UUID id) {
        Produto produto = this.produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return produto.getEstoqueDisponivel();
    }
}
