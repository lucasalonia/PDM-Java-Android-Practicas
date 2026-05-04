package com.ulp.clase18_sharedpreference;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.clase18_sharedpreference.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        vm  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        setContentView(binding.getRoot());


        binding.btGuardar.setOnClickListener(view -> {
            vm.guardarDatos(binding.etNombre.getText().toString(), binding.etEdad.getText().toString());
        });

        binding.btMostrar.setOnClickListener(v -> {
            //No pasamos nada por el intent ya que la informacioln a mostrar la encontrará en datos.xml
            Intent intent = new Intent(MainActivity.this, MostrarActivity.class);
            startActivity(intent);
        });
    }
}