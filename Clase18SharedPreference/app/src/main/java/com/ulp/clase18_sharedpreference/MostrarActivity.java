package com.ulp.clase18_sharedpreference;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.clase18_sharedpreference.databinding.ActivityMostrarBinding;

public class MostrarActivity extends AppCompatActivity {
    private ActivityMostrarBinding binding;
    private MostrarActivityViewModel mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMostrarBinding.inflate(getLayoutInflater());
        mv  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MostrarActivityViewModel.class);
        setContentView(binding.getRoot());

        mv.getNombre().observe(this, s -> {
            binding.tvNombre.setText(s);
        });
        mv.getEdad().observe(this, s -> {
            binding.tvEdad.setText(s);
        });
        mv.mostrarDatos();


    }
}