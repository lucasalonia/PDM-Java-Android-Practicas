package com.ulp.clase18_sharedpreference;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MostrarActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> nombre, edad;
    private Context context;
    public MostrarActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();

    }

    public LiveData<String> getNombre(){
        if(nombre == null){
            nombre = new MutableLiveData<>();
        }
        return nombre;
    }
    public LiveData<String> getEdad(){
        if(edad == null){
            edad = new MutableLiveData<>();
        }
        return edad;
    }

    public void mostrarDatos(){
        SharedPreferences sp = context.getSharedPreferences("datos.xml", MODE_PRIVATE);

        //En este caso no necesitamos el editor
        //Hay que asignar valores por defecto en caso de no recuperar nada
        int edad = sp.getInt("edad", -1);
        String nombre = sp.getString("nombre", null);

        if(edad != -1 && nombre != null){
            this.nombre.setValue(nombre);
            this.edad.setValue(edad+"");
        }else {
            Toast.makeText(context, "No hay datos guardados", Toast.LENGTH_SHORT).show();

        }
    }

}
