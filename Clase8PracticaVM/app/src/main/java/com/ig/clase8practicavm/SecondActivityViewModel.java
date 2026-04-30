package com.ig.clase8practicavm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ig.clase8practicavm.model.Persona;

public class SecondActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> nombre;
    private MutableLiveData<String> apellido;
    private MutableLiveData<String> email;

    //Vamos a hacerlo con un objeto esto se hizo en la clase 9
    private MutableLiveData<Persona> personaMutable;
    public SecondActivityViewModel(@NonNull Application application) {
        super(application);
    }
//    public LiveData<String> getNombre(){
//        if (nombre==null){
//            nombre = new MutableLiveData<>();
//        }
//        return nombre;
//    }
//    public LiveData<String> getApellido(){
//        if (apellido==null){
//            apellido = new MutableLiveData<>();
//        }
//        return apellido;
//    }
//    public LiveData<String> getEmail(){
//        if (email==null){
//            email = new MutableLiveData<>();
//        }
//        return email;
//    }


    //Hacemos el get para el mutable de persona
    public LiveData<Persona> getPersonaMutable(){
        if (personaMutable==null){
            personaMutable = new MutableLiveData<>();
        }
        return personaMutable;
    }
    public void obtenerNombre(String n){
//        nombre.setValue(n);
//        apellido.setValue("asd");
//        email.setValue("algo@correo");
        Persona p = new Persona(n,"asd","algo@correo");
            personaMutable.setValue(p);
    }

}
