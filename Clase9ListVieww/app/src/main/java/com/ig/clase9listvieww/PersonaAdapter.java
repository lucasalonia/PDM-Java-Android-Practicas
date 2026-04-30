package com.ig.clase9listvieww;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ig.clase9listvieww.modelo.Persona;

import java.util.List;

public class PersonaAdapter extends ArrayAdapter<Persona> {
    //Creamos atributos con los datos que se quieren mostrar en la vista
    //Conce cuales son as personas
    private List<Persona> personas;

    //El contexto
    private Context context;

    //Inflar cada una de las personas en el layout item
    private LayoutInflater li;

    //para obtener el recurso
    private int itemMostrar;


    //Constructor especifico con contexto y con el id del recurso (layout item)
    // y la lsita de elementos (estrctura de items con la que va a trabajar
    // agregamos manualmente un layoutinflater
    public PersonaAdapter(@NonNull Context context, int resource, @NonNull List<Persona> objects, LayoutInflater li) {
        super(context, resource, objects);
        this.personas = objects;
        this.context = context;
        this.li = li;
        this.itemMostrar = resource;
    }

    //metodo de arrayadapter el cual accede aca item de la lista y configura cada persona
    //Va a retornar una vista el cual es cada item. Son los que se incrustan en el listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //El item esta en convertView
        View item = convertView;
        if(item==null){
            //El que me pasan ppor el constructor
            //Parent es el contenedor padre. En este caso es el listview
            //False para que la lsita gestione sus propios elementos
            item = li.inflate(itemMostrar, parent, false);
        }
        //Recuperamos los componentes del item view a traves de su identificador el cual esta
        //dentro de los recursos de res

        ImageView foto = item.findViewById(R.id.foto) ;
        TextView apellido = item.findViewById(R.id.apellido);
        TextView nombre = item.findViewById(R.id.nombre);
        TextView correo = item.findViewById(R.id.correo);

        //Buscamos la persona que estamos recorriendo en este momento. La obtenemos a traves de
        //la posicion del recorrido y el get en la lista
        Persona p = personas.get(position);
        //Seteamos la imagen en base a un recurso
        foto.setImageResource(p.getFoto());
        apellido.setText(p.getApellido());
        nombre.setText(p.getNombre());
        correo.setText(p.getCorreo());


        //Item es un VIEW por lo que se puede usar metodo on clik
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando el usuario hace un click queremos que envie la informacion que contiene
                //esta vista. Creamos un intent. ADAPTER NO FUNCIONA COMO CONTEXTO usamos el contexto
                //del constructor
                Intent i= new Intent(context, DetalleActivity.class);
                i.putExtra("persona",p);

                //Aññadimos una bandera al intent para indicarle al jvm que el lugar donde se inicia
                //el intent no es una activity
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return item;
    }

    //Metodo para saber la cantidad de personas que va a recorrer, cantidad de elementos de la lista

    @Override
    public int getCount() {
        return personas.size();
    }
}
