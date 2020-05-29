package com.tfg.inmobiliariatfg.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewMisAnunciosFragment;
import com.tfg.inmobiliariatfg.fragments.BuscadorFragment;
import com.tfg.inmobiliariatfg.fragments.ErrorFragment;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewMisFavoritosFragment;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewMisInmueblesFragment;
import com.tfg.inmobiliariatfg.fragments.PerfilFragment;
import com.tfg.inmobiliariatfg.modelos.Usuario;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Bundle ExtrasLogin;
    private Context context;
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

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences();

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
                    break;
                }
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

    private void sharedPreferences(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("baseUrl", getString(R.string.baseURL));
        editor.commit();
    }
}
