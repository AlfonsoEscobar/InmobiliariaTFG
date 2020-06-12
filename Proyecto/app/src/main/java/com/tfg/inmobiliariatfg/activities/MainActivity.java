package com.tfg.inmobiliariatfg.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.fragments.BuscadorFragment;
import com.tfg.inmobiliariatfg.fragments.ErrorFragment;
import com.tfg.inmobiliariatfg.fragments.PerfilFragment;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewMisAnunciosFragment;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewMisFavoritosFragment;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewMisInmueblesFragment;
import com.tfg.inmobiliariatfg.modelos.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Bundle ExtrasLogin;
    private Usuario usuario;
    private int idUsuario = 0;
    private String nomUsuarioLateral, correoUsuarioLateral;
    private TextView tvNomMenuLateral, tvCorreoMenuLateral;
    private ImageView ivMenuLateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        tvNomMenuLateral = navigationView.getHeaderView(0).findViewById(R.id.tvNomMenuLateral);
        tvCorreoMenuLateral = navigationView.getHeaderView(0).findViewById(R.id.tvCorreoMenuLateral);
        ivMenuLateral = navigationView.getHeaderView(0).findViewById(R.id.ivMenuLateral);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BuscadorFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_buscar);
        }

        ExtrasLogin = getIntent().getExtras();
        if (ExtrasLogin != null) {
            usuario = (Usuario) ExtrasLogin.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
            nomUsuarioLateral = usuario.getNombre();
            correoUsuarioLateral = usuario.getCorreo();
            tvNomMenuLateral.setText(nomUsuarioLateral);
            tvCorreoMenuLateral.setText(correoUsuarioLateral);
            ImagenDeUsuario();
        }else {
            idUsuario = 0;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_buscar:
                BuscadorFragment buscadorFragment = new BuscadorFragment();
                buscadorFragment.setArguments(ExtrasLogin);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        buscadorFragment).commit();
                break;
            case R.id.nav_perfil:
                if (idUsuario == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ErrorFragment()).commit();
                } else {
                    PerfilFragment perfilFragment = new PerfilFragment();
                    perfilFragment.setArguments(ExtrasLogin);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            perfilFragment).commit();
                }
                break;
            case R.id.nav_favoritos:
                if (idUsuario == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ErrorFragment()).commit();
                } else {
                    RecyclerViewMisFavoritosFragment recyclerViewMisFavoritosFragment = new RecyclerViewMisFavoritosFragment();
                    recyclerViewMisFavoritosFragment.setArguments(ExtrasLogin);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            recyclerViewMisFavoritosFragment).commit();
                }
                break;
            case R.id.nav_inmuebles:
                if (idUsuario == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ErrorFragment()).commit();
                } else {
                    RecyclerViewMisInmueblesFragment recyclerViewMisInmueblesFragment = new RecyclerViewMisInmueblesFragment();
                    recyclerViewMisInmueblesFragment.setArguments(ExtrasLogin);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            recyclerViewMisInmueblesFragment).commit();
                }
                break;
            case R.id.nav_anuncios:
                if (idUsuario == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ErrorFragment()).commit();
                } else {
                    RecyclerViewMisAnunciosFragment rvMisAnuncios = new RecyclerViewMisAnunciosFragment();
                    rvMisAnuncios.setArguments(ExtrasLogin);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            rvMisAnuncios).commit();
                }
                    break;

            case R.id.nav_Ajustes:
                final AlertDialog.Builder alertMofificarNombrePerfil = new AlertDialog.Builder(MainActivity.this);
                alertMofificarNombrePerfil.setTitle("Modificar RemoteUri");

                final EditText input = new EditText(MainActivity.this);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertMofificarNombrePerfil.setView(input);

                alertMofificarNombrePerfil.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                String remoteUri = input.getText().toString();
                                sharedPreferences(remoteUri);
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
                    break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void sharedPreferences(String baseURL){
        SharedPreferences sharedPref = getSharedPreferences("rutaURL",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("baseUrl", baseURL);
        editor.commit();
    }

    public String getPref() {
        SharedPreferences sharedPref = getSharedPreferences("rutaURL",Context.MODE_PRIVATE);
        String baseURL = sharedPref.getString("baseUrl","https://34af4e85d798.ngrok.io/Restful_Inmo/servicios/");
        return baseURL;
    }

    private void ImagenDeUsuario() {
        String ngrok = getPref() + "fotografia/usuario/";
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
                    ivMenuLateral.setImageURI(uriPath);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
