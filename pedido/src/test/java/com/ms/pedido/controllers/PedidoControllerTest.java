package com.ms.pedido.controllers;

import com.ms.pedido.dtos.PedidoRecord;
import com.ms.pedido.entities.Pedido;
import com.ms.pedido.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCriaPedido() throws Exception {
        PedidoRecord pedidoRecord = new PedidoRecord();
        Pedido pedido = new Pedido();

        when(pedidoService.criaPedido(any(PedidoRecord.class))).thenReturn(pedido);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(pedidoService, times(1)).criaPedido(any(PedidoRecord.class));
    }

    @Test
    void testGetAllPedidos() throws Exception {
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        when(pedidoService.getAllPedidos()).thenReturn(pedidos);

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(pedidoService, times(1)).getAllPedidos();
    }

    @Test
    void testGetPedidoById() throws Exception {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido();
        when(pedidoService.getPedidoById(id)).thenReturn(pedido);

        mockMvc.perform(get("/pedidos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(pedidoService, times(1)).getPedidoById(id);
    }

    @Test
    void testAtualizaPedido() throws Exception {
        PedidoRecord pedidoRecord = new PedidoRecord();
        Pedido pedido = new Pedido();
        when(pedidoService.atualizaPedido(any(PedidoRecord.class))).thenReturn(pedido);

        mockMvc.perform(put("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(pedidoService, times(1)).atualizaPedido(any(PedidoRecord.class));
    }
}
