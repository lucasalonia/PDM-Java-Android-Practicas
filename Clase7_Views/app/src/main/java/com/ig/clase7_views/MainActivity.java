package com.ig.clase7_views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ig.clase7_views.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //SI tenemos View Binding activado, buscamos la clase generada para la vista.
    //En gradle.kts los activamos dentro del objeto android. Este, una vez sincronizado
    //Permitirá crear el atributo con la clase de la vista relacionada
    private ActivityMainBinding binding;

    //Creamos una vatiable auxiliar para contar los numeros
    //Lo creamos como astributo para que todos los metodos tengan acceso al mismo
//    int c = 0;
    //Citamos esto ya que la activity no contara mas, de eso se encargará el viewmodel

    // Creamos un atributo con la clase ViewModel que vamos a utilizar
    private MainActivityViewModel mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializamos la clase de la vista previo al contetn view
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //La variable binding representara la vista por lo que hay que modificar el setContentView

        //Utilizamos la variable con la clase y buscamos la raiz
        setContentView(binding.getRoot());

        //Despues del contentView inicializamos el ViewModel
        //La instancia es asi de compleja
        mv= new ViewModelProvider(this).get(MainActivityViewModel.class);

        //Cada uno de los componentes tiene su identificacion en java
        //Si al boton lo uso en mas de un metodo debo crearlo como ATRIBUTO de la clase
        //La busqueda por id esta vinculado a todos los id de la aplicacion.
        //El error seria usar un componente que no esta en nuestra vista
        //Lo buscamos por su id
//        Button btContar = findViewById(R.id.contar);
//        TextView tvMostrar = findViewById(R.id.mostrar);

        //Citamos la forma anterior de localizar el id de los componentes de la vista para tabajar
        //ahora con binding.

        //Accedemos como un atributo de la clase
//        binding.mostrar.setText(c + "");
        //Esto ya no es necesario tampoco



        //Observador par alos cambios
        //new Observer crea un callback un proceso un bojeto en segundo plano que queda
        //pendiente del mutable que reacciona a cualquier cambio
        mv.getContadorMutable().observe(this, new Observer<Integer>() {
            @Override
            //El Integer es el mutable que esta observando
            public void onChanged(Integer c) {
                //HAY QUE SI O SI TRANFORMAR EL PARAMETRO EN STRING
                binding.mostrar.setText(c+"");
            }
        });

        //Listener del evento cuando se ahce click
        //Accedemos como un atributo de la clase
        binding.contar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //Usamos el atributo para contar
//                c++;
//                //Espera string por lo que concatenamos comillas vacias
//                binding.mostrar.setText(c + "");
                //Citamos lo anterior ya que son responsabilidades del viewmodel

                //Utilizamos el metodo de contar proveido por el mv
                mv.contar();


            }
        });

        binding.btTraducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoIngresado = binding.traducirTexto.getText().toString();

                if(textoIngresado.equals("pinga")){
                    binding.mostrarTraduccion.setText("dick");
                }
            }
        });
    }
}