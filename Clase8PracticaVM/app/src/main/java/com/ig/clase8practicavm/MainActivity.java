package com.ig.clase8practicavm;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ig.clase8practicavm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainActivityViewModel mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        mv= new ViewModelProvider(this).get(MainActivityViewModel.class);
        mv.getAlumnoMutable().observe(this, new Observer<String>() {
            @Override
            //El Integer es el mutable que esta observando
            public void onChanged(String c) {
                //HAY QUE SI O SI TRANFORMAR EL PARAMETRO EN STRING
                binding.tvAlumnoEncontrado.setText(c);
            }
        });
        binding.btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoIngresado = binding.etAlumno.getText().toString();
                mv.buscarAlumno(textoIngresado);
            }
        });

    }
}