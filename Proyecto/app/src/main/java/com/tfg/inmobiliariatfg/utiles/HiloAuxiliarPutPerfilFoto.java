package com.tfg.inmobiliariatfg.utiles;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HiloAuxiliarPutPerfilFoto extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... urls) {
        String remoteUri = urls[0];
        String path = urls[1];
        URL urlPut;
        try {
            FileInputStream fIS = new FileInputStream(path);
            int numeroDeBytesAEnviar = fIS.available();
            byte[] bytesAEnviar = new byte[numeroDeBytesAEnviar];
            fIS.read(bytesAEnviar, 0, bytesAEnviar.length);
            fIS.close();

            urlPut = new URL(remoteUri);
            HttpURLConnection urlPutConnection = (HttpURLConnection) urlPut.openConnection();
            
            urlPutConnection.setConnectTimeout(10000);
            urlPutConnection.setReadTimeout(10000);
            urlPutConnection.setDoOutput(true);
            urlPutConnection.setRequestMethod("PUT");
            urlPutConnection.setFixedLengthStreamingMode(bytesAEnviar.length);
            urlPutConnection.setRequestProperty("Content-Type", "images/jpeg");
            urlPutConnection.connect();

            OutputStream oS = urlPutConnection.getOutputStream();
            DataOutputStream dOS = new DataOutputStream(oS);
            dOS.write(bytesAEnviar, 0, bytesAEnviar.length);
            dOS.flush();
            dOS.close();

            urlPutConnection.disconnect();
        } catch (IOException IOe) {
            Log.v("IOException", "" + IOe.getMessage());
        }
        return null;
    }
}
