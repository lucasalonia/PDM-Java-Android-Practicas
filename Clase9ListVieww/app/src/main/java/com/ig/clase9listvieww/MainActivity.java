package com.ig.clase9listvieww;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ig.clase9listvieww.databinding.ActivityMainBinding;
import com.ig.clase9listvieww.modelo.Persona;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainActivityViewModel mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        mv.getPersonaMutable().observe(this, new Observer<List<Persona>>() {

            //El cambio se va a detectar automaticamente ya que al momento de isntanciar mv se crea
            //una lista de personas y a su vez esta configura el mutable, probocando el evento que
            //esta debajo
            @Override
            public void onChanged(List<Persona> personas) {

                //Debemos configurar el Adapter para que genere los items en la vvista que estamos
                //posicionados ahora
                //Accedemos al contexto a traves de a clase contenedora, buscamos el id de la vista
                //iteme en la carpeta de recursos, asignams la lista de ersonas quej va a recorrer
                //Por ultimo la referencia del layout inflate

                PersonaAdapter pa = new PersonaAdapter(MainActivity.this, R.layout.item, personas, getLayoutInflater());

                //Ahora con la vista construida con el adaptador tenemos que configurar al list view
               //este tipo de elemento tiene el metodo setAdapter.
                binding.listado.setAdapter(pa);
            }
        });
    }
}