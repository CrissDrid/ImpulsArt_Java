package com.impulsart.ImpulsArtApp.security;

public class ConstantesSeguridad {

    //Limite de tiempo de expiracion del token
    public static final long JWT_EXPIRATION_TOKEN = 7 * 24 * 60 * 60 * 1000L;
    public static final String JWT_FIRMA = "SECRET_KEY_IMPULSART_SENA_CSF";

}