package com.ig.clase9listvieww;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ig.clase9listvieww.modelo.Persona;

public class DetalleActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Persona> personaRecuperadaMutable = new MutableLiveData<>();
    public DetalleActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Persona> getPersonaRecuperdaMutable(){
       return personaRecuperadaMutable;
    }

    public void recuperarPersona(Intent intent){
        Persona p = intent.getSerializableExtra("persona", Persona.class);
        if(p!=null){
            if((p.getApellido()!=null && !p.getApellido().isEmpty()) && (p.getCorreo()!=null)){
                personaRecuperadaMutable.setValue(p);
            }
        }
    }
}
