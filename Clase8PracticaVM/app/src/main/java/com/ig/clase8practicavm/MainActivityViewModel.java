package com.ig.clase8practicavm;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {
    private ArrayList<String> listaAlumnos;
    private MutableLiveData<String> alumnoMutable;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        listaAlumnos = new ArrayList<>();

        listaAlumnos.add("Saul");
        listaAlumnos.add("Jessie");
    }

    public LiveData<String> getAlumnoMutable() {
        if(alumnoMutable==null){
            alumnoMutable= new MutableLiveData<>();
            String primeraBusqueda="Primera Busqueda";
            alumnoMutable.setValue(primeraBusqueda);
        }
        return alumnoMutable;
    }
    public void buscarAlumno(String nombreABuscar) {

        for (String a : listaAlumnos) {
            if (a.equalsIgnoreCase(nombreABuscar)) {
                // 1. Crea el Intent explícito definiendo el contexto de la App y la clase destino
                Intent i = new Intent(getApplication(), SecondActivity.class);

// 2. Agrega el flag obligatorio para iniciar una Activity desde un contexto de Application
// Esto crea una nueva tarea (Task) en el stack de Android.
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

// 3. Pasa datos extras (clave, valor) a la siguiente pantalla
                i.putExtra("nombre", nombreABuscar);

// 4. Lanza la ejecución del Intent a través del sistema operativo
                getApplication().startActivity(i);
                return;
            }
        }

        alumnoMutable.setValue("No encontrado");
    }
}
