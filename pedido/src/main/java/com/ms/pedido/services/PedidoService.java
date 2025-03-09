package com.ms.pedido.services;

import com.ms.pedido.clients.ProdutoCliente;
import com.ms.pedido.clients.UsuarioClient;
import com.ms.pedido.dtos.EmailDto;
import com.ms.pedido.dtos.PedidoRecord;
import com.ms.pedido.dtos.UsuarioDTO;
import com.ms.pedido.entities.ItemPedido;
import com.ms.pedido.entities.Pedido;
import com.ms.pedido.producer.PedidoProducer;
import com.ms.pedido.repositories.PedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioClient usuarioClient;
    private final ProdutoCliente produtoCliente;
    private final PedidoProducer pedidoProducer;

    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioClient usuarioClient,
                         ProdutoCliente produtoCliente,
                         PedidoProducer pedidoProducer) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioClient = usuarioClient;
        this.produtoCliente = produtoCliente;
        this.pedidoProducer = pedidoProducer;
    }


    @Transactional
    public Pedido criaPedido(PedidoRecord pedidoRecord) {
        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(pedidoRecord, pedido);

        UsuarioDTO usuarioDTO = getUsuario(pedidoRecord);
        validaQuantidadeEstoque(pedido);

        Pedido pedidoSalvo = this.pedidoRepository.saveAndFlush(pedido);

        enviaEmailConfirmacaoPedido(pedidoSalvo, usuarioDTO);

        return pedidoSalvo;
    }

    public List<Pedido> getAllPedidos() {
        return this.pedidoRepository.findAll();
    }

    public Pedido getPedidoById(UUID id) {
        return this.pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Transactional
    public Pedido atualizaPedido(PedidoRecord pedidoRecord) {
        Pedido pedido = this.pedidoRepository.findById(pedidoRecord.id()).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        BeanUtils.copyProperties(pedidoRecord, pedido);

        return this.pedidoRepository.saveAndFlush(pedido);
    }

    public void enviaEmailConfirmacaoPedido(Pedido pedido, UsuarioDTO usuarioDTO) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailTo(usuarioDTO.getEmail());
        emailDto.setText("Pedido realizado com sucesso!");
        emailDto.setSubject("Novo Pedido");
        emailDto.setUserId(usuarioDTO.getId());
        this.pedidoProducer.publishEmail(emailDto);
    }

    public UsuarioDTO getUsuario(PedidoRecord pedidoRecord) {
        return this.usuarioClient.validarUsuario(pedidoRecord.usuarioId());
    }

    public void validaQuantidadeEstoque(Pedido pedido) {
        if (pedido.getItens() != null && !pedido.getItens().isEmpty()) {
            for (ItemPedido itemPedido : pedido.getItens()) {
                Long quantidadeEstoque = this.produtoCliente.quantidadeEstoque(itemPedido.getId());
                if (quantidadeEstoque < itemPedido.getQuantidade()) {
                    throw new RuntimeException("Quantidade em estoque indisponivel");
                }
            }
        }
    }
}
