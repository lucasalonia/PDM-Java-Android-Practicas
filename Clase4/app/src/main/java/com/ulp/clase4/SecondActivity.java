package com.ulp.clase4;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    private TextView mostraInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mostraInfo = findViewById(R.id.textShow);
        /*Recuperamos el intetn.
        Es la forma en la que la SecondActivity recibe el "paquete" que
         enviaste desde la MainActivity.*/
       Intent i =  getIntent();
       /*Guardamos el dato en una variable  usando la clave del
        mapa envia por el intent*/
       String datoExtraido =  i.getStringExtra("dato");
       mostraInfo.setText(datoExtraido);
        accesoADatos();

    }

    private void accesoADatos(){

        /*Tenemos que usar StringBuilder para concatenar cadenas de texto*/
        StringBuilder mensaje=new StringBuilder();

        //Buscamos en la direccion publica de llamadas del movil
        Uri llamadas = Uri.parse("content://call_log/calls");
        ContentResolver cr = getContentResolver();
        /*Hacemos consulta a la tabla de la base de datos del disposstitivo en sqlite*/
        /* SELECT * FROM llamadas*/
        Cursor cursor =  cr.query(llamadas,null,null,null,null);
        /*Cursor devuelve los resultados de la query por lo que tenemos que recorrelos*/


        String nro=null;
        String tiempo=null;

        /*Recorremos la consulta sql CUrsor a ContentResolver*/
        if(cursor.getCount()>0){
            /*Recorre la matriz con la informacion*/
            while(cursor.moveToNext()){

                /*Obtenemos el numero de indice con el nombre de la columna*/
                int indiceNumero=cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int indiceDuracion=cursor.getColumnIndex(CallLog.Calls.DURATION);

                /*Seteamos en una variable los calores */
                nro=cursor.getString(indiceNumero);
                tiempo=cursor.getString(indiceDuracion);
                mensaje.append(nro+" "+tiempo+"\n");


            }
            mostraInfo.setText(mensaje.toString());

        }



    }
}