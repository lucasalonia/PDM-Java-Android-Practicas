package com.example.clase2603;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

public class SegundaActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> nombre;
    private MutableLiveData<String> apellido;
    private MutableLiveData<String> email;
    public SegundaActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getNombre(){
        if (nombre==null){
            nombre = new MutableLiveData<>();
        }
        return nombre;
    }
    public LiveData<String> getApellido(){
        if (apellido==null){
            apellido = new MutableLiveData<>();
        }
        return apellido;
    }
    public LiveData<String> getEmail(){
        if (email==null){
            email = new MutableLiveData<>();
        }
        return email;
    }
    public void obtenerNombre(String n){
        nombre.setValue(n);
        apellido.setValue("asd");
        email.setValue("algo@correo");
    }
}
