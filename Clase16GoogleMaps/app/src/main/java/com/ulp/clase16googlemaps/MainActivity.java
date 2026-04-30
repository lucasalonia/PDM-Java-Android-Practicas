package com.ulp.clase16googlemaps;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;
import com.ulp.clase16googlemaps.databinding.ActivityMainBinding;


//Hay que hacer que extienda de fragmentactivity para usar el mapa
public class MainActivity extends FragmentActivity {
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        viewModel = new MainActivityViewModel(getApplication());
        viewModel  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        viewModel.getMapaActual().observe(this, new Observer<MainActivityViewModel.MapaActual>() {
            @Override
            public void onChanged(MainActivityViewModel.MapaActual mapaActual) {
                ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapaActual);
            }
        });
        viewModel.cargarMapa();
    }
}