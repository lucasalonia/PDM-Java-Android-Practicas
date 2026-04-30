package com.ulp.clase4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    /*Forma antigua de nombrar los elementos atraves de atributos de la clase*/
    private EditText dato;
    private Button siguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedirPermiso();
        /*Llamamos al metodo para pedir permiso en acceder a las llamadas*/


        /*Vamos asignar los valores aca pero un metodo no debe estar sobrecargado de resposnabilidad*/

        dato = findViewById(R.id.editTextDato);
        siguiente = findViewById(R.id.btSiguiente);

        /*Funcion propia de la clase Button la cual escucha eventos*/
        /*Hacemos una clase anonima de listtener*/
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = dato.getText().toString();

                /*Creamos el intent aca instanciando a Intent*/
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                /*ponemos this porque se va a estar
                refiriendo a la clase del metodo actual. Entonces dentro de la clase anonima listener*/
                /*.class pasándole al sistema un "plano" o la "identidad" de la clase que quieres abrir.*/

                i.putExtra("dato",mensaje);
                /*Para mapa, clave valor*/

                /*El método startActivity() vive en la clase Context.*/
                startActivity(i);
            }
        });
    }
  /*metodo en el que pediremos permiso al usuario para acceder a su registro de llamadas*/
    public void pedirPermiso(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG},1000);
        }

    }
}