package com.example.clase2603;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.clase2603.databinding.ActivitySegundaBinding;

public class SegundaActivity extends AppCompatActivity {
    private ActivitySegundaBinding b;
    private SegundaActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySegundaBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SegundaActivityViewModel.class);
        vm.getNombre().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                b.tvNombre.setText(s);
            }
        });
        vm.getApellido().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                b.tvApellido.setText(s);
            }
        });
        vm.getEmail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                b.tvEmail.setText(s);
            }
        });
        vm.obtenerNombre(getIntent().getStringExtra("nombre"));


    }
}