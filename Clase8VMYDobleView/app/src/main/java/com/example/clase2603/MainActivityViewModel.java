package com.example.clase2603;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private List<String> alumnos;
    private MutableLiveData<String> encontrado;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        iniciarLista();
    }

    private void iniciarLista(){
        alumnos = Arrays.asList("Pepe","Juan","Carlitos","Default","Laucha");
    }

    public LiveData<String> getEncontrado(){
        if(encontrado==null){
            encontrado = new MutableLiveData<>();
        }
        return encontrado;
    }

    public void buscar(String nombre){
        for (String s : alumnos){
            if (s.equals(nombre)){
                Intent i = new Intent(getApplication(), SegundaActivity.class);
                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("nombre",nombre);
                getApplication().startActivity(i);
                return;
            }
        }
        encontrado.setValue("No encontrado");
    }

}
