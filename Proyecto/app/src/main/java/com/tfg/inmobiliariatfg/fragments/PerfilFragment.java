package com.tfg.inmobiliariatfg.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.MainActivity;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.ApiService;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {

    String selection;
    TextView tvNomPerfil, tvCorreoPerfil, tvTelPerfil;
    Button btnEditarImagenPerfil, btnEditarTelefonosPerfil, btnEditarNombrePerfil, btnEliminarCuentaPerfil;
    ImageView ivPerfil;
    Usuario usuario;
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
        btnEditarTelefonosPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                    Call<Void> call = ApiAdapter.getApiService().putModificarUsuarioPerfil(idUsuario, usuario);
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
        });

        btnEditarNombrePerfil = cl.findViewById(R.id.btnEditarNombrePerfil);
        btnEditarNombrePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                    Call<Void> modificarCall = ApiAdapter.getApiService().putModificarUsuarioPerfil(idUsuario, usuario);
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
        });

        btnEditarImagenPerfil = cl.findViewById(R.id.btnBotonEditarImagenPerfil);
        btnEditarImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        btnEliminarCuentaPerfil = cl.findViewById(R.id.btnEliminarCuentaPerfil);
        btnEliminarCuentaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertEliminarCuentaPerfil = new AlertDialog.Builder(getContext());
                alertEliminarCuentaPerfil.setTitle("¿Esta seguro de querer eliminarla? \n Se borrará todo lo asociado a la misma.");
                alertEliminarCuentaPerfil.setMessage("Introduzca su contraseña");

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

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
                                            "Porfavor introduzca un Nombre", Toast.LENGTH_LONG).show();
                                } else {
                                    correo = usuario.getCorreo();
                                    pass = Metodos.codificarPass(input.getText().toString());
                                    Call<Usuario> call = ApiAdapter.getApiService().getUsuario(correo, pass);
                                    call.enqueue(new Callback<Usuario>() {
                                        @Override
                                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                            if (response.isSuccessful()) {
                                                Call<Void> callDelete = ApiAdapter.getApiService().eliminarUsuario(idUsuario);
                                                call.enqueue(new Callback<Usuario>() {
                                                    @Override
                                                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                                        if (response.code() == 200) {
                                                            Usuario usuario = null;
                                                            Toast.makeText(getContext(), "El usuario ha sido Eliminado", Toast.LENGTH_LONG);
                                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                                            intent.putExtra("usuario", usuario);
                                                            startActivity(intent);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Usuario> call, Throwable t) {

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
        });

        if (datos != null) {
            usuario = (Usuario) datos.getSerializable("usuario");
            tvNomPerfil.setText(usuario.getNombre());
            tvCorreoPerfil.setText(usuario.getCorreo());
            idUsuario = usuario.getId_usuario();
            if (usuario.getImagen_perfil() != null) {
                ivPerfil.setImageBitmap(bitmap(usuario.getImagen_perfil()));
            }
            tvTelPerfil.setText(usuario.getTelefono1() + "\n " + usuario.getTelefono2());
        }
        return cl;
    }

    private void cargarImagen() {

        final CharSequence[] opciones = {"Tomar foto", "Cargar imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Tomar foto")) {
                    Intent camaraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    startActivityForResult(camaraIntent, 9);
                } else {
                    if (opciones[which].equals("Cargar imagen")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    public Bitmap bitmap(byte[] bImagen) {
        return BitmapFactory.decodeByteArray(bImagen, 0, bImagen.length);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }
}
