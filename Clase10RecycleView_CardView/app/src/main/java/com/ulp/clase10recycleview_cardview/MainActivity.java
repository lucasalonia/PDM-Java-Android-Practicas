package com.ulp.clase10recycleview_cardview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ulp.clase10recycleview_cardview.databinding.ActivityMainBinding;
import com.ulp.clase10recycleview_cardview.modelo.Inmueble;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
private MainActivityViewModel vm;
private InmuebleAdapter ia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm =  ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        vm.getInmuebleMutable2().observe(this, new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                    ia.actualizarLista(new ArrayList<>(inmuebles));
            }
        });
        vm.getInmueblesMutable().observe(this, new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                 ia = new InmuebleAdapter(inmuebles, MainActivity.this, getLayoutInflater());
                //Al usar un card view Necesitamos un manager. Configura el orden de la carta
                GridLayoutManager glm = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.VERTICAL,false);

                binding.lista.setLayoutManager(glm);
                binding.lista.setAdapter(ia);
            }
        });
        vm.cargarInmuebles();
        vm.dormir();


    }
}