package com.ulp.clase18_sharedpreference;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainActivityViewModel extends AndroidViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public void guardarDatos(String nombre, String edad){

        //Creamos un objeto de tipo SharedPreference.
        //Por parametro nombramos el archivo xml donde se guardaran los datos. El archivo se crea en este momento
        //si no esta creado previamente. Si ya esta creado lo busca
        //El segundo parametro es modo privado para que solo pueda ser accedido por la app
        SharedPreferences sp = getApplication().getSharedPreferences("datos.xml", MODE_PRIVATE);

        //Creamos un editor para editar el archivo xml. Este se crea utilizando el metodo edit() de
        //SharedPreferences
        SharedPreferences.Editor editor = sp.edit();

        if(nombre.isEmpty() || edad.isEmpty()){
            Toast.makeText(getApplication().getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
            return;
        }

            int iEdad = Integer.parseInt(edad);

            //Con el objeto editor guardamos los datos.
            editor.putInt("edad", iEdad);
            editor.putString("nombre", nombre);
            //Los datos no se guardan todavia. Tenemos que aplicar el metodo commit
            //El metodo commit devuelve un boolean.
            boolean guardado = editor.commit();
            if (guardado){
                Toast.makeText(getApplication().getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplication().getApplicationContext(), "Datos no guardados", Toast.LENGTH_SHORT).show();
            }


    }
}
