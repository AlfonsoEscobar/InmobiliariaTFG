package com.tfg.inmobiliariatfg.utiles;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HiloAuxiliarGetPerfilFoto extends AsyncTask<String, Void, File> {
    @Override
    protected File doInBackground(String... urls) {
        String remoteUri = urls[0];
        String path = urls[1];
        try {
            URL urlGet = new URL(remoteUri);

            HttpURLConnection urlGetConnection = (HttpURLConnection) urlGet.openConnection();
            int numeroDeBytesRecibidos = urlGetConnection.getContentLength();
            byte[] bytesRecibidos = new byte[numeroDeBytesRecibidos];

            InputStream iS = urlGetConnection.getInputStream();
            DataInputStream dIS = new DataInputStream(iS);
            dIS.read(bytesRecibidos);
            dIS.close();
            urlGetConnection.disconnect();

            FileOutputStream fOS = new FileOutputStream(path);
            fOS.write(bytesRecibidos, 0, bytesRecibidos.length);
            fOS.close();
        } catch (IOException iOE) {

        }

        return null;
    }
}
