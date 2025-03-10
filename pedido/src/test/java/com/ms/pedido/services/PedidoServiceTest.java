package com.ms.pedido.services;

import com.ms.pedido.clients.ProdutoCliente;
import com.ms.pedido.clients.UsuarioClient;
import com.ms.pedido.dtos.PedidoRecord;
import com.ms.pedido.dtos.UsuarioDTO;
import com.ms.pedido.entities.Pedido;
import com.ms.pedido.producer.PedidoProducer;
import com.ms.pedido.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProdutoCliente produtoCliente;

    @Mock
    private PedidoProducer pedidoProducer;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoRecord pedidoRecord;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(UUID.randomUUID());

        pedidoRecord = new PedidoRecord(UUID.randomUUID(), UUID.randomUUID(), List.of());

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID.randomUUID());
        usuarioDTO.setEmail("teste@teste.com");
    }

    @Test
    void testCriaPedido() {
        when(usuarioClient.validarUsuario(any())).thenReturn(usuarioDTO);
        when(pedidoRepository.saveAndFlush(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.criaPedido(pedidoRecord);

        assertNotNull(resultado);
        verify(pedidoRepository, times(1)).saveAndFlush(any(Pedido.class));
        verify(pedidoProducer, times(1)).publishEmail(any());
    }

    @Test
    void testGetAllPedidos() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.getAllPedidos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testGetPedidoById() {
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.getPedidoById(pedido.getId());

        assertNotNull(resultado);
    }

    @Test
    void testGetPedidoById_NotFound() {
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pedidoService.getPedidoById(UUID.randomUUID()));
    }

    @Test
    void testAtualizaPedido() {
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedido));
        when(pedidoRepository.saveAndFlush(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.atualizaPedido(pedidoRecord);

        assertNotNull(resultado);
        verify(pedidoRepository, times(1)).saveAndFlush(any(Pedido.class));
    }
}
