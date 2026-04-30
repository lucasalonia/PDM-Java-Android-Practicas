package com.ulp.clase12menunavegable.ui.calculos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultadoViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> resultado;
    private MutableLiveData<String> mostrarTexto;
    public ResultadoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Integer> getResultado() {
        if (resultado == null){
            resultado = new MutableLiveData<>();
        }
        return resultado;
    }

    public LiveData<String> getMostrarTexto() {
        if (mostrarTexto == null){
            mostrarTexto = new MutableLiveData<>();
        }
        return mostrarTexto;
    }

    public void recuperarDatos(Bundle bundle){
        int n1 = bundle.getInt("num1");
        int n2 = bundle.getInt("num2");


        boolean tipoOperacion = bundle.getBoolean("operacion");
        if (tipoOperacion){
            mostrarTexto.setValue("La operacion fue una suma");
            resultado.setValue(n1+n2);
        }else {
            mostrarTexto.setValue("La operacion fue una resta");
            resultado.setValue(n1-n2);
        }



    }
}