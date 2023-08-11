package com.rogerio.servicesecurity.utils;

public class Constants {
    private Constants() {
    }

    // PERMISSÕES
    public static final String PERMISSION_ADMIN = "admin";
    public static final String PERMISSION_MODERATOR = "mod";
    public static final String PERMISSION_USER = "user";
    public static final String PERMISSION_DEPENDENT = "dependent";

    // EXCEÇÕES
    public static final String NOT_FOUND_EXCEPTION_MESSAGE = "%s com %s %s não foi encontrado.";
    public static final String FORBIDDEN_EXCEPTION_MESSAGE = "Esse usuário não possui permissão suficiente para acesso.";
    public static final String CONFLICT_EXCEPTION_MESSAGE = "%s com %s já existe em nossa base.";

    // EXCEÇÕES
    public static final String CREATE_USER_MESSAGE = "Usuário criado com sucesso.";
}
