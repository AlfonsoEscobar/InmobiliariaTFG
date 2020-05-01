package com.tfg.inmobiliariatfg.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.fragments.AnunciosFragment;
import com.tfg.inmobiliariatfg.fragments.BuscadorFragment;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewFavoritosFragment;
import com.tfg.inmobiliariatfg.fragments.InmueblesFragment;
import com.tfg.inmobiliariatfg.fragments.PerfilFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_buscar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BuscadorFragment()).commit();
                break;
            case R.id.nav_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PerfilFragment()).commit();
                break;
            case R.id.nav_favoritos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RecyclerViewFavoritosFragment()).commit();
                break;
            case R.id.nav_inmuebles:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InmueblesFragment()).commit();
                break;
            case R.id.nav_anuncios:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AnunciosFragment()).commit();
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
}
