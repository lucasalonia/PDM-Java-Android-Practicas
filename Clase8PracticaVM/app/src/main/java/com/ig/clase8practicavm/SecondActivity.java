package com.ig.clase8practicavm;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ig.clase8practicavm.databinding.ActivitySecondBinding;
import com.ig.clase8practicavm.model.Persona;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding b;
    private SecondActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SecondActivityViewModel.class);
//        vm.getNombre().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                b.tvNombre.setText(s);
//            }
//        });
//        vm.getApellido().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                b.tvApellido.setText(s);
//            }
//        });
//        vm.getEmail().observe(this, new Observer<String>() {
//            @Override
//            public void onChange  d(String s) {
//                b.tvEmail.setText(s);
//            }
//        });

        //Recibimos el objeto completo de persona y aca seleccionamos los que usamos
        vm.getPersonaMutable().observe(this, new Observer<Persona>() {
            @Override
            public void onChanged(Persona p) {
                b.tvEmail.setText(p.getEmail());
                b.tvApellido.setText(p.getApellido());
                b.tvNombre.setText(p.getNombre());
            }
        });

        vm.obtenerNombre(getIntent().getStringExtra("nombre"));


    }
}