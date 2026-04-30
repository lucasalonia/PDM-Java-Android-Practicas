package com.ig.clase9listvieww;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ig.clase9listvieww.databinding.ActivityDetalleBinding;
import com.ig.clase9listvieww.databinding.ActivityMainBinding;
import com.ig.clase9listvieww.modelo.Persona;


//Vista para adentrasre en el item
public class DetalleActivity extends AppCompatActivity {
    private ActivityDetalleBinding binding;
    private DetalleActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(DetalleActivityViewModel.class);

        vm.getPersonaRecuperdaMutable().observe(this, new Observer<Persona>() {
            @Override
            public void onChanged(Persona persona) {
                binding.apellidoDetalle.setText(persona.getApellido());
                binding.fotoDetalle.setImageResource(persona.getFoto());
                binding.nombreDetalle.setText(persona.getNombre());
                binding.correoDetalle.setText(persona.getCorreo());
            }
        });
        vm.recuperarPersona(getIntent());
    }
}