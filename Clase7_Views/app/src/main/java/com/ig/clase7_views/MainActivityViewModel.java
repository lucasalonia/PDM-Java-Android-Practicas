package com.ig.clase7_views;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

//Para que sea un view model debe extender de AndroidViewModel
public class MainActivityViewModel extends AndroidViewModel {

    //La accion de incrementar el numero del contador deberia ir aca y
    // no directamente en la MainActivity
    private int c = 0;
    //Para que la vista se entere que el contador cambio de estado hay que guardarlo en un objeto
    //especial

    //Tiene que ser del objeto que se quiere comunicar
    //La activity es observar este mutable cuando cambie
    private MutableLiveData<Integer> contadorMutable;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    //Supertipo de Mutable
    //Hacemos un metodo para obtener el mutable
    public LiveData<Integer> getContadorMutable(){
        if(contadorMutable==null){
            contadorMutable= new MutableLiveData<>();

            //Para que ya el texto muestre el valor inicial
            contadorMutable.setValue(c);
        }
        return contadorMutable;
    }

    //En este caso no debe devolver nada, no debe ser una funcion
    public void contar(){
        c++;
        contadorMutable.setValue(c);

    }
}
