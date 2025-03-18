package com.ms.usuario.enums;

public enum Role {
    ADMIN("admin"),
    CLIENTE("cliente");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static Role fromString(String string) {
        if (string.contains("admin")) return ADMIN;
        else return CLIENTE;
    }
}
