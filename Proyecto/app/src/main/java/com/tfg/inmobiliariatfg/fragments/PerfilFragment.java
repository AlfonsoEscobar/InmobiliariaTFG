package com.tfg.inmobiliariatfg.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
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
import androidx.fragment.app.Fragment;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.MainActivity;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final String DIRECTORIO_IMAGEN = "pictures";
    private String path;
    File fileImagen;
    Bitmap bitmap;

    private static final int COD_GALLERY = 10;
    private static final int COD_CAMERA = 20;

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
        btnEditarNombrePerfil = cl.findViewById(R.id.btnEditarNombrePerfil);
        btnEliminarCuentaPerfil = cl.findViewById(R.id.btnEliminarCuentaPerfil);
        btnEditarImagenPerfil = cl.findViewById(R.id.btnBotonEditarImagenPerfil);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

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
                ivPerfil.setImageBitmap(bitmap(usuario.getImagen_perfil()));
            }
            tvTelPerfil.setText(usuario.getTelefono1() + "\n");
            if (usuario.getTelefono2() != null) {
                tvTelPerfil.append(usuario.getTelefono2());
            }
        }
        return cl;
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
                            Call<Usuario> call = ApiAdapter.getApiService().getUsuario(correo, pass);
                            call.enqueue(new Callback<Usuario>() {
                                @Override
                                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                    if (response.isSuccessful()) {
                                        Call<Void> callDelete = ApiAdapter.getApiService().eliminarUsuario(idUsuario);
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

    private void cargarImagenDialog() {

        final CharSequence[] opciones = {"Tomar foto", "Cargar imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Tomar foto")) {
                    if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
                        abrirCamara();
                    } else {
                        EasyPermissions.requestPermissions(this, "Access for storage",
                                101, galleryPermissions);
                    }
                } else {
                    if (opciones[which].equals("Cargar imagen")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), COD_GALLERY);
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void abrirCamara() {
        File file = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreada = file.exists();

        if (isCreada == false) {
            isCreada = file.mkdirs();
        }

        if (isCreada == true) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
            String nombre = timeStamp + ".jpeg";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + nombre;

            fileImagen = new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            startActivityForResult(intent, COD_CAMERA);

        }
    }

    public Bitmap bitmap(byte[] bImagen) {

        return BitmapFactory.decodeByteArray(bImagen, 0, bImagen.length);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        MultipartBody.Part filePart = null;
        File file = null;

        switch (requestCode) {
            case COD_GALLERY:
                Uri miPath = data.getData();
                String ipath = getPath(miPath);
                Log.v("path", "" + ipath);
                file = new File(ipath);
                filePart = MultipartBody.Part.createFormData("file", file.getName(),
                        RequestBody.create(file, MediaType.parse("image/jpeg")));
                ivPerfil.setImageURI(miPath);
                break;
            case COD_CAMERA:
                //Metodos.verifyStoragePermissions(getActivity());
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.v("path", "" + path);
                            }
                        });
                bitmap = BitmapFactory.decodeFile(path);
                ivPerfil.setImageBitmap(bitmap);

                file = new File(path);

                break;
        }
        /*if (file != null) {
            Call <Void> call = ApiAdapter.getApiServiceSinGson().putImagenPerfil(file);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Toast.makeText(getContext(),
                                "Imagen modificada con exito", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            sendFile("https://a24536f8.ngrok.io/Restful_Inmo/servicios/fotografia/inmueble", path);
        }*/
    }
    /*public static int sendFile(String remoteUri, String localFileName) {
        int resultado = -1;

        try {
            FileInputStream fIS = new FileInputStream(localFileName);
            int numeroDeBytesAEnviar = fIS.available();
            byte[] bytesAEnviar = new byte[numeroDeBytesAEnviar];
            fIS.read(bytesAEnviar, 0, bytesAEnviar.length);
            fIS.close();

            URL urlPut = new URL(remoteUri);

            HttpURLConnection urlPutConnection = (HttpURLConnection) urlPut.openConnection();
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

            resultado = urlPutConnection.getResponseCode();
            urlPutConnection.disconnect();
        } catch (IOException IOe) {
        }
        Log.v("enviado", "" + resultado);
        return resultado;
    }*/

    public String getPath(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /*File createImageFile() throws IOException {

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");


        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }*/

}
