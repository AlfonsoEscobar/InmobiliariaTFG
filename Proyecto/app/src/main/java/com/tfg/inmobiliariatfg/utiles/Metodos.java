package com.tfg.inmobiliariatfg.utiles;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.tfg.inmobiliariatfg.modelos.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static void mostrarDialogo(ProgressDialog progressDialog) {
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Esperando respuesta del servidor");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static boolean comprobarCredencialesBorrar(String correo, String pass){
        final boolean[] comprobacion = {false};
        Call<Usuario> call = ApiAdapter.getApiService().getUsuario(correo, pass);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                boolean respuesta = false;
                if(response.isSuccessful()){
                    respuesta = true;
                }
                comprobacion[0] = respuesta;
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
        return comprobacion[0];
    }
}
