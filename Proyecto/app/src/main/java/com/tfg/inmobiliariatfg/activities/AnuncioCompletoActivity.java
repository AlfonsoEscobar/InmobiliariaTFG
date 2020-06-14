package com.tfg.inmobiliariatfg.activities;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.adapters.ImagenesHorizontalAdapter;
import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnuncioCompletoActivity extends AppCompatActivity {

    private Bundle datosAnuncio;
    private List<Favorito> favoritosUsuario;
    private InfoAnuncio anuncio;
    private int idUsuario;

    private RecyclerView recyclerViewImagenesAnuncio;
    private ImagenesHorizontalAdapter imagenesHorizontalAdapter;

    private TextView tvProvinciaMisInmueblesAnuncioCompleto, tvLocalidadMisInmueblesAnuncioCompleto, tvDireccionMisInmueblesAnuncioCompleto, tvPisoMisInmueblesAnuncioCompleto, tvDescripcionMisInmueblesAnuncioCompleto,
            tvHabitacionesMisInmueblesAnuncioCompleto, tvBanosMisInmueblesAnuncioCompleto, tvMetrosMisInmueblesAnuncioCompleto, tvTObraMisInmueblesAnuncioCompleto, tvTEdificacionMisInmueblesAnuncioCompleto,
            tvEquipamientoMisInmueblesAnuncioCompleto, tvExterioresMisInmueblesAnuncioCompleto, tvtipoAnuncioMisInmueblesAnuncioCompleto, tvPrecioMisInmueblesAnuncioCompleto, tvFechaAnuncioMisInmueblesAnuncioCompleto,
            tvultimaActualizacionMisInmueblesAnuncioCompleto, tvCorreoAnuncianteMisInmueblesAnuncioCompleto, tvTelefonoAnuncianteMisInmueblesAnuncioCompleto;
    private ImageView ivUltPlantaMisInmueblesAnuncioCompleto, ivGarajeMisInmueblesAnuncioCompleto, ivTrasteroMisInmueblesAnuncioCompleto, ivAscensorMisInmueblesAnuncioCompleto, ivMascotasMisInmueblesAnuncioCompleto;
    private Button btnFavoritoAnuncioCompleto, btnCopiarCorreoAnuncioCompleto, btnDialogTelefonoAnuncioCompleto;
    List<String> listaRutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_completo);

        tvProvinciaMisInmueblesAnuncioCompleto = findViewById(R.id.tvProvinciaMisInmueblesAnuncioCompleto);
        tvLocalidadMisInmueblesAnuncioCompleto = findViewById(R.id.tvLocalidadMisInmueblesAnuncioCompleto);
        tvDireccionMisInmueblesAnuncioCompleto = findViewById(R.id.tvDireccionMisInmueblesAnuncioCompleto);
        tvPisoMisInmueblesAnuncioCompleto = findViewById(R.id.tvDpisoMisInmueblesAnuncioCompleto);
        tvDescripcionMisInmueblesAnuncioCompleto = findViewById(R.id.tvDescripcionMisInmueblesAnuncioCompleto);
        tvHabitacionesMisInmueblesAnuncioCompleto = findViewById(R.id.tvHabitacionesMisInmueblesAnuncioCompleto);
        tvBanosMisInmueblesAnuncioCompleto = findViewById(R.id.tvBanosMisInmueblesAnuncioCompleto);
        tvMetrosMisInmueblesAnuncioCompleto = findViewById(R.id.tvMetrosMisInmueblesAnuncioCompleto);
        tvTObraMisInmueblesAnuncioCompleto = findViewById(R.id.tvTObraMisInmueblesAnuncioCompleto);
        tvTEdificacionMisInmueblesAnuncioCompleto = findViewById(R.id.tvTEdificacionMisInmueblesAnuncioCompleto);
        tvEquipamientoMisInmueblesAnuncioCompleto = findViewById(R.id.tvEquipamientoMisInmueblesAnuncioCompleto);
        tvExterioresMisInmueblesAnuncioCompleto = findViewById(R.id.tvExterioresMisInmueblesAnuncioCompleto);
        ivUltPlantaMisInmueblesAnuncioCompleto = findViewById(R.id.ivUltPlantaMisInmueblesAnuncioCompleto);
        ivGarajeMisInmueblesAnuncioCompleto = findViewById(R.id.ivGarajeMisInmueblesAnuncioCompleto);
        ivTrasteroMisInmueblesAnuncioCompleto = findViewById(R.id.ivTrasteroMisInmueblesAnuncioCompleto);
        ivAscensorMisInmueblesAnuncioCompleto = findViewById(R.id.ivAscensorMisInmueblesAnuncioCompleto);
        ivMascotasMisInmueblesAnuncioCompleto = findViewById(R.id.ivMascotasMisInmueblesAnuncioCompleto);
        tvtipoAnuncioMisInmueblesAnuncioCompleto = findViewById(R.id.tvtipoAnuncioMisInmueblesAnuncioCompleto);
        tvPrecioMisInmueblesAnuncioCompleto = findViewById(R.id.tvPrecioMisInmueblesAnuncioCompleto);
        tvFechaAnuncioMisInmueblesAnuncioCompleto = findViewById(R.id.tvFechaAnuncioMisInmueblesAnuncioCompleto);
        tvultimaActualizacionMisInmueblesAnuncioCompleto = findViewById(R.id.tvultimaActualizacionMisInmueblesAnuncioCompleto);
        tvCorreoAnuncianteMisInmueblesAnuncioCompleto = findViewById(R.id.tvCorreoAnuncianteMisInmueblesAnuncioCompleto);
        tvTelefonoAnuncianteMisInmueblesAnuncioCompleto = findViewById(R.id.tvTelefonoAnuncianteMisInmueblesAnuncioCompleto);
        btnFavoritoAnuncioCompleto = findViewById(R.id.btnFavoritoAnuncioCompleto);
        btnCopiarCorreoAnuncioCompleto = findViewById(R.id.btnCopiarCorreoAnuncioCompleto);
        btnDialogTelefonoAnuncioCompleto = findViewById(R.id.btnDialogTelefonoAnuncioCompleto);

        recyclerViewImagenesAnuncio = findViewById(R.id.recyclerViewImagenesAnuncio);
        recyclerViewImagenesAnuncio.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        datosAnuncio = getIntent().getExtras();
        if (datosAnuncio != null) {
            anuncio = (InfoAnuncio) datosAnuncio.getSerializable("anuncio");
            idUsuario = datosAnuncio.getInt("idUsuario");
            favoritosUsuario = (List<Favorito>) datosAnuncio.getSerializable("favoritos");

            tvProvinciaMisInmueblesAnuncioCompleto.setText(anuncio.getInmueble().getProvincia());
            tvLocalidadMisInmueblesAnuncioCompleto.setText(anuncio.getInmueble().getLocalidad());
            tvDireccionMisInmueblesAnuncioCompleto.setText(anuncio.getInmueble().getCalle());
            tvPisoMisInmueblesAnuncioCompleto.append(" " + anuncio.getInmueble().getPiso() + "º");
            if (anuncio.getInmueble().getDescripcion().equals("")) {
                tvDescripcionMisInmueblesAnuncioCompleto.setText("No se ha añadido descripción");
            } else {
                tvDescripcionMisInmueblesAnuncioCompleto.setText(anuncio.getInmueble().getDescripcion());
            }
            tvHabitacionesMisInmueblesAnuncioCompleto.append(" " + anuncio.getInmueble().getNum_habitaciones());
            tvBanosMisInmueblesAnuncioCompleto.append(" " + anuncio.getInmueble().getNum_banos());
            tvMetrosMisInmueblesAnuncioCompleto.setText(anuncio.getInmueble().getMetros2() + " /m²");
            tvTObraMisInmueblesAnuncioCompleto.append(" " + anuncio.getInmueble().getTipo_obra());
            tvTEdificacionMisInmueblesAnuncioCompleto.append(" " + anuncio.getInmueble().getTipo_edificacion());
            tvEquipamientoMisInmueblesAnuncioCompleto.append(" " + anuncio.getInmueble().getEquipamiento());
            tvExterioresMisInmueblesAnuncioCompleto.append(" " + anuncio.getInmueble().getExteriores());
            tvtipoAnuncioMisInmueblesAnuncioCompleto.append(" " + anuncio.getTipo_anuncio());
            tvPrecioMisInmueblesAnuncioCompleto.append(" " + anuncio.getPrecio());
            tvFechaAnuncioMisInmueblesAnuncioCompleto.append(" " + new SimpleDateFormat("dd-MM-yyyy").format(anuncio.getFecha_anunciado()));
            tvultimaActualizacionMisInmueblesAnuncioCompleto.append(" " + new SimpleDateFormat("dd-MM-yyyy").format(anuncio.getFecha_ultima_actualizacion()));
            tvCorreoAnuncianteMisInmueblesAnuncioCompleto.append(" " + anuncio.getCorreo());

            if (anuncio.getInmueble().isUltima_planta()) {
                ivUltPlantaMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_checked);
            } else {
                ivUltPlantaMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_not_checked);
            }
            if (anuncio.getInmueble().isGaraje()) {
                ivGarajeMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_checked);
            } else {
                ivGarajeMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_not_checked);
            }
            if (anuncio.getInmueble().isTrastero()) {
                ivTrasteroMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_checked);
            } else {
                ivTrasteroMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_not_checked);
            }
            if (anuncio.getInmueble().isAscensor()) {
                ivAscensorMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_checked);
            } else {
                ivAscensorMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_not_checked);
            }
            if (anuncio.getInmueble().isMascotas()) {
                ivMascotasMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_checked);
            } else {
                ivMascotasMisInmueblesAnuncioCompleto.setImageResource(R.drawable.ic_not_checked);
            }
            comprobarFavorito();
        }

        setImagenes(anuncio);
    }

    public void botonFavorito(View view) {
        if (idUsuario != 0) {
            if (comprobarFavorito() == true) {

                Drawable drawable = getDrawable(R.drawable.ic_favorito_unchecked);
                btnFavoritoAnuncioCompleto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);

                Call<Void> eliminarFavorito = ApiAdapter.getApiService(getPref()).eliminarFavorito(idUsuario, anuncio.getId_inmueble(), anuncio.getTipo_anuncio());
                eliminarFavorito.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            favoritosUsuario = null;
                            Call<List<Favorito>> actualizarFavorito = ApiAdapter.getApiService(getPref()).getCompararFavorito(idUsuario);
                            actualizarFavorito.enqueue(new Callback<List<Favorito>>() {
                                @Override
                                public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                                    if (response.isSuccessful()) {
                                        comprobarFavorito();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Favorito>> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            } else {

                Drawable drawable = getDrawable(R.drawable.ic_favorito_checked);
                btnFavoritoAnuncioCompleto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);

                Favorito favorito = new Favorito();
                favorito.setUsuario_favorito(idUsuario);
                favorito.setInmueble_favorito(anuncio.getInmueble().getId_inmueble());
                favorito.setTipo_anuncio(anuncio.getTipo_anuncio());

                Call<Void> addFavorito = ApiAdapter.getApiService(getPref()).createFavorito(favorito);
                addFavorito.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Call<List<Favorito>> actualizarFavorito = ApiAdapter.getApiService(getPref()).getCompararFavorito(idUsuario);
                            actualizarFavorito.enqueue(new Callback<List<Favorito>>() {
                                @Override
                                public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                                    if (response.isSuccessful()) {
                                        favoritosUsuario = response.body();

                                        comprobarFavorito();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Favorito>> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Por favor, inicia sesion para guardar tus favoritos.", Toast.LENGTH_LONG).show();
        }

    }

    public boolean comprobarFavorito() {
        boolean favorito = false;
        if (idUsuario != 0) {
            if (favoritosUsuario != null) {
                for (int i = 0; i < favoritosUsuario.size(); i++) {
                    if (favoritosUsuario.get(i).getInmueble_favorito() == anuncio.getId_inmueble() &&
                            favoritosUsuario.get(i).getTipo_anuncio().equals(anuncio.getTipo_anuncio())) {
                        Drawable drawable = getDrawable(R.drawable.ic_favorito_checked);
                        btnFavoritoAnuncioCompleto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
                        favorito = true;
                        break;
                    } else {
                        Drawable drawable = getDrawable(R.drawable.ic_favorito_unchecked);
                        btnFavoritoAnuncioCompleto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
                    }
                }
            } else {
                Drawable drawable = getDrawable(R.drawable.ic_favorito_unchecked);
                btnFavoritoAnuncioCompleto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
            }

        }
        return favorito;
    }

    public void copiarCorreo(View v) {
        String text = anuncio.getCorreo();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", text);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getApplicationContext(),
                "Copiado a portapapeles", Toast.LENGTH_LONG).show();
    }

    public void llamadaTelefonosDialog(View v) {
        final CharSequence[] opciones = {"Telefono", "Telefono opcional", "Cancelar"};
        final AlertDialog.Builder alertOpcionesTelefono = new AlertDialog.Builder(AnuncioCompletoActivity.this);
        alertOpcionesTelefono.setTitle("Seleccione una opción");
        alertOpcionesTelefono.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Telefono")) {
                    String tel1 = anuncio.getTelefono1();
                    lanzarLlamada(tel1);
                } else {
                    if (opciones[which].equals("Telefono opcional")) {
                        String tel2 = anuncio.getTelefono2();
                        if (tel2 != null) {
                            lanzarLlamada(tel2);
                        } else {
                            Toast.makeText(AnuncioCompletoActivity.this,
                                    "No tiene telefono opcional este usuario.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpcionesTelefono.show();
    }

    public void lanzarLlamada(String telefono) {
        if (telefono != null) {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:" + telefono));
            startActivity(i);
        }
    }

    public String getPref() {
        SharedPreferences sharedPref = getSharedPreferences("rutaURL",Context.MODE_PRIVATE);
        String baseURL = sharedPref.getString("baseUrl","https://34af4e85d798.ngrok.io/Restful_Inmo/servicios/");
        return baseURL;
    }

    private void setImagenes(InfoAnuncio anuncio) {
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TFG/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final String pathGuardar = dir.getAbsolutePath() + "/";
        Log.v("pathGuardar", "" + pathGuardar);

        Inmueble inmueble = anuncio.getInmueble();
            final ArrayList<Uri> listUriImages = new ArrayList<>();
            int idInmueble = inmueble.getId_inmueble();
            RequestHandle handle = null;

            Call<List<String>> listCall = ApiAdapter.getApiService(getPref()).getStringsUriFotos(idInmueble);
            try {
                Response<List<String>> response = listCall.execute();
                nombreImagenes(response.body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (listaRutas != null) {
                for (int j = 0; j < listaRutas.size(); j++) {
                    String ruta = getPref() + "fotografia/inmueble/" + idInmueble + "/" + listaRutas.get(j);
                    final int finalJ = j;
                    handle = new AsyncHttpClient().get(ruta, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String nombre = System.currentTimeMillis() / 100 + finalJ + ".jpeg";
                            Uri uriPath = null;
                            FileOutputStream fOS;
                            try {
                                fOS = new FileOutputStream(pathGuardar + nombre);
                                fOS.write(responseBody, 0, responseBody.length);
                                fOS.close();
                                uriPath = Uri.parse(pathGuardar + nombre);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (uriPath != null) {
                                listUriImages.add(uriPath);
                                Log.v("list", "" + pathGuardar + nombre);
                                Log.v("nombre", "" + nombre);
                            }
                        }


                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    while (!handle.isFinished()){
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                String archivo = "android.resource://" + getPackageName() + "/" + R.drawable.ic_sin_imagen;
                Uri ruta = Uri.parse(archivo);
                listUriImages.add(ruta);
                listUriImages.add(ruta);
                listUriImages.add(ruta);
                listUriImages.add(ruta);
            }
        imagenesHorizontalAdapter = new ImagenesHorizontalAdapter(getApplicationContext(), listUriImages);
        recyclerViewImagenesAnuncio.setAdapter(imagenesHorizontalAdapter);
    }

    private void nombreImagenes(List<String> response) {
        listaRutas = response;
    }
}
