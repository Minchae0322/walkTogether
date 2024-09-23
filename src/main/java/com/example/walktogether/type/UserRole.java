package com.example.walktogether.type;

public enum UserRole {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    USER("USER");


    UserRole(String role) {
        this.role = role;
    }

    public String getValue() {
        return this.role;
    }

    private final String role;
}
