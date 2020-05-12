package com.tfg.inmobiliariatfg.utiles;


import android.content.Context;
import android.content.SharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Metodos {

    public static String codificarPass(String Pass){
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(Pass.getBytes());
        StringBuilder sb = new StringBuilder();

        for(byte b : hash){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
