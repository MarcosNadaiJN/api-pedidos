package com.ms.pedido.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmailDto {
    UUID userId;
    String emailTo;
    String subject;
    String text;
}
