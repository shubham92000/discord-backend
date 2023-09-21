package com.example.discordBackend.utils;

public class RoleValue {
    public static Role getRole(String userType){
        return switch (userType.toLowerCase()) {
            case "user" -> Role.ROLE_USER;
            case "admin" -> Role.ROLE_ADMIN;
            default -> null;
        };
    }
}
