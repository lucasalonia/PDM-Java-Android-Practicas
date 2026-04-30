package com.ulp.clase10recycleview_cardview;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.clase10recycleview_cardview.modelo.Inmueble;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> inmueblesMutable = null;
    private MutableLiveData<List<Inmueble>> inmuebleMutable2 = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesMutable() {
        if (inmueblesMutable==null){
            inmueblesMutable = new MutableLiveData<>();
        }
        return inmueblesMutable;
    }

    public MutableLiveData<List<Inmueble>> getInmuebleMutable2() {
        return inmuebleMutable2;
    }

    public void cargarInmuebles(){
        ArrayList<Inmueble> lista = new ArrayList<>();

        lista.add(new Inmueble("casa1",4848,848.8,R.drawable.casa1));
        lista.add(new Inmueble("casa2",4848,8.488,R.drawable.casa2));
        lista.add(new Inmueble("casa3",4848,84.88,R.drawable.casa3));

       inmueblesMutable.setValue(lista);

    }


    //Primera forma de actualizar contenido
    public void dormir(){
        List<Inmueble> l= new ArrayList<>();
        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    l.add(new Inmueble("casadwdfzs3", 4848, 84.88, R.drawable.casa3));

                   //metodo para ejecutar un livedata fuera del hilo principal
                    //Lo hacemos con otro mutable para mostrarlo
                    inmuebleMutable2.postValue(l);
                } catch (Exception e) {
                    Log.d("TAG", e+"");
                }
            }
        };
        t.start();
    }

}
