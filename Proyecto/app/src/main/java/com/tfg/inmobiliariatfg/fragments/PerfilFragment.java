package com.tfg.inmobiliariatfg.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.MainActivity;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private String ngrok;

    private static final int COD_GALLERY = 10;

    private String selection;
    private TextView tvNomPerfil, tvCorreoPerfil, tvTelPerfil;
    private Button btnEditarImagenPerfil, btnEditarTelefonosPerfil, btnEditarNombrePerfil, btnEliminarCuentaPerfil;
    private ImageView ivPerfil;
    private Usuario usuario;
    int idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_perfil, container, false);

        Bundle datos = getArguments();

        tvNomPerfil = cl.findViewById(R.id.tvNomPerfil);
        tvCorreoPerfil = cl.findViewById(R.id.tvCorreoPerfil);
        tvTelPerfil = cl.findViewById(R.id.tvTelPerfil);
        ivPerfil = cl.findViewById(R.id.ivPerfil);
        btnEditarTelefonosPerfil = cl.findViewById(R.id.btnEditarTelefonosPerfil);
        btnEditarNombrePerfil = cl.findViewById(R.id.btnEditarNombrePerfil);
        btnEliminarCuentaPerfil = cl.findViewById(R.id.btnEliminarCuentaPerfil);
        btnEditarImagenPerfil = cl.findViewById(R.id.btnBotonEditarImagenPerfil);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        btnEditarTelefonosPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarTelefonosPerfil();
            }
        });

        btnEditarNombrePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarNombrePerfil();
            }
        });


        btnEditarImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagenDialog();
            }
        });

        btnEliminarCuentaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarCuentaPerfil();
            }
        });

        if (datos != null) {
            usuario = (Usuario) datos.getSerializable("usuario");
            tvNomPerfil.setText(usuario.getNombre());
            tvCorreoPerfil.setText(usuario.getCorreo());
            idUsuario = usuario.getId_usuario();
            if (usuario.getImagen_perfil() != null) {
                ivPerfil.setImageBitmap(Metodos.bitmap(usuario.getImagen_perfil()));
            }
            tvTelPerfil.setText(usuario.getTelefono1() + "\n");
            if (usuario.getTelefono2() != null) {
                tvTelPerfil.append(usuario.getTelefono2());
            }
        }
        ngrok = getPref() + "fotografia/usuario/";

        ImagenDeUsuario();

        return cl;
    }

    private void ImagenDeUsuario() {
        String RemoteUri = ngrok + idUsuario;
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TFG/");
        final String pathGuardar;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        pathGuardar = dir.getAbsolutePath() + "/TFG";
        Log.v("pathGuardar", "" + pathGuardar);

        new AsyncHttpClient().get(RemoteUri, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String nombre = System.currentTimeMillis() / 100 + ".jpeg";
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
                    ivPerfil.setImageURI(uriPath);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void borrarCuentaPerfil() {

        final AlertDialog.Builder alertEliminarCuentaPerfil = new AlertDialog.Builder(getContext());
        alertEliminarCuentaPerfil.setTitle("¿Esta seguro de querer eliminarla? \n Se borrará para siempre.");
        alertEliminarCuentaPerfil.setMessage("Introduzca su contraseña");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertEliminarCuentaPerfil.setView(input);

        alertEliminarCuentaPerfil.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String correo, pass;
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(getContext(),
                                    "La contraseña no es correcta", Toast.LENGTH_LONG).show();
                        } else {
                            correo = usuario.getCorreo();
                            pass = Metodos.codificarPass(input.getText().toString());
                            Call<Usuario> call = ApiAdapter.getApiService(getPref()).getUsuario(correo, pass);
                            call.enqueue(new Callback<Usuario>() {
                                @Override
                                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                    if (response.isSuccessful()) {
                                        Call<Void> callDelete = ApiAdapter.getApiService(getPref()).eliminarUsuario(idUsuario);
                                        callDelete.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if (response.code() == 200) {
                                                    Usuario usuario = null;
                                                    Toast.makeText(getContext(), "El usuario ha sido Eliminado", Toast.LENGTH_LONG);
                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                    intent.putExtra("usuario", usuario);
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<Usuario> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
        alertEliminarCuentaPerfil.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertEliminarCuentaPerfil.show();
    }

    private void editarTelefonosPerfil() {

        final String[] telefonos = {"telefono", "telefono Opcional"};

        final AlertDialog.Builder alertModificarTelefonosPerfil = new AlertDialog.Builder(getContext());
        alertModificarTelefonosPerfil.setTitle("Seleccione Telefono a modificar");
        alertModificarTelefonosPerfil.setSingleChoiceItems(telefonos, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = telefonos[which];
            }
        });

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);

        alertModificarTelefonosPerfil.setView(input);

        alertModificarTelefonosPerfil.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String telefono = input.getText().toString();
                        if (telefono.equals("")) {
                            Toast.makeText(getContext(),
                                    "Porfavor introduzca un Telefono", Toast.LENGTH_LONG).show();
                        } else {
                            if (selection.equals(telefonos[0])) {
                                usuario.setTelefono1(telefono);
                            } else {
                                usuario.setTelefono2(telefono);
                            }
                            Call<Void> call = ApiAdapter.getApiService(getPref()).putModificarUsuarioPerfil(idUsuario, usuario);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 200) {
                                        tvTelPerfil.setText(usuario.getTelefono1() + "\n" + usuario.getTelefono2());
                                        Toast.makeText(getContext(),
                                                "El telefono ha sido modificado", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getContext(),
                                            "La conexión con el servidor ha fallado", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
        alertModificarTelefonosPerfil.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertModificarTelefonosPerfil.show();
    }

    private void editarNombrePerfil() {
        final AlertDialog.Builder alertMofificarNombrePerfil = new AlertDialog.Builder(getContext());
        alertMofificarNombrePerfil.setTitle("Escriba el Nombre");

        final EditText input = new EditText(getContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertMofificarNombrePerfil.setView(input);

        alertMofificarNombrePerfil.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String nombre = input.getText().toString();
                        if (nombre.equals("")) {
                            Toast.makeText(getContext(),
                                    "Porfavor introduzca un Nombre", Toast.LENGTH_LONG).show();
                        } else {
                            usuario.setNombre(nombre);
                            Call<Void> modificarCall = ApiAdapter.getApiService(getPref()).putModificarUsuarioPerfil(idUsuario, usuario);
                            modificarCall.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 200) {
                                        tvNomPerfil.setText(usuario.getNombre());
                                        Toast.makeText(getContext(),
                                                "El nombre ha sido modificado", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getContext(),
                                            "La conexión con el servidor ha fallado", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
        alertMofificarNombrePerfil.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertMofificarNombrePerfil.show();
    }

    private void cargarImagenDialog() {

        final CharSequence[] opciones = {"Galeria", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Galeria")) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, COD_GALLERY);
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/");
                            startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), COD_GALLERY);
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, COD_GALLERY);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COD_GALLERY:

                Uri selectedImageURI = data.getData();

                String remoteUri = ngrok + idUsuario;
                putImagen(remoteUri,selectedImageURI);
                break;
        }
    }

    public void putImagen(String remoteUri, Uri selectedImageURI) {

        final String path = getPath(selectedImageURI);
        final File file = new File(path);
        Log.v("path", "" + path);
        ivPerfil.setImageURI(selectedImageURI);
        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        new AsyncHttpClient().put(remoteUri, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case COD_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, COD_GALLERY);
                } else {
                }
                break;
        }
    }

    public String getPref() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("rutaURL",Context.MODE_PRIVATE);
        String baseURL = sharedPref.getString("baseUrl","https://34af4e85d798.ngrok.io/Restful_Inmo/servicios/");
        return baseURL;
    }

}

