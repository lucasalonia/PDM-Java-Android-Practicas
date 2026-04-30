package com.ig.clase9listvieww;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ig.clase9listvieww.modelo.Persona;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    //En este caso lo hacemos asi para poder cargar de datos en el constructor. Yo optaria por
    //un metodo que se encargue de llenar de datos
    private MutableLiveData<List<Persona>> personasMutable = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Carlo","Carlo","c@efe.com",R.drawable.persona1));
        personas.add(new Persona("test","testa","1@efe.com",R.drawable.persona3));
        personas.add(new Persona("ramiro","gonzales","refe.com",R.drawable.persona4));
        personasMutable.setValue(personas);

    }

    public LiveData<List<Persona>> getPersonaMutable(){

        return personasMutable;
    }
}
