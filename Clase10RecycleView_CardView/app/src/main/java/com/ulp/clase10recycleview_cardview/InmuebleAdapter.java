package com.ulp.clase10recycleview_cardview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.clase10recycleview_cardview.modelo.Inmueble;

import java.util.List;

//Diferencias con ListView. En este caso se conecta con la CardView
//Se utiliza la clase Holder para tener la representacion del modelo. Conectar los datos con la
//vista de debe representarlos.
//Una vez con la clase interna creada extendemos
public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {
    private List<Inmueble> inmuebles;
    private Context context;
    private LayoutInflater li;

    public InmuebleAdapter(List<Inmueble> inmuebles, Context context, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.context = context;
        this.li = li;
    }


    //Este metodo devuelve un objeto de tipo ViewHolderInmueble el cual es la reprecentacion del//xml
   //
    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item, parent, false);
        return new ViewHolderInmueble(itemView);
    }


    //Este metodo se encarga del elemento de la lista que traemos según su posición
    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {

        //Utilizamos la representacion de la vista y le asignamos los valores del inmueble en el arreglo
        Inmueble inmuebleActual = inmuebles.get(position);
        holder.direccion.setText("Direccion "+inmuebleActual.getDireccion());
        holder.precio.setText("Precio "+inmuebleActual.getPrecio());
        holder.superficie.setText("Superficie "+inmuebleActual.getSuperficie());
        holder.foto.setImageResource(inmuebleActual.getFoto());
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }
    public void actualizarLista(List<Inmueble> nuevaLista) {
        inmuebles.clear();
        inmuebles.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    //Creamos la clase anonima que extiende de RecyclerView. Hace el match entre java y elementos xml
   //Representa a ITEM.XML!!
    public class ViewHolderInmueble extends RecyclerView.ViewHolder{
        //hay que representar los items de la vista
        TextView direccion, superficie, precio;
        ImageView foto;
        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion= itemView.findViewById(R.id.tvDireccion);
            superficie= itemView.findViewById(R.id.tvSuperficie);
            precio= itemView.findViewById(R.id.tvPrecio);
            foto= itemView.findViewById(R.id.ivFoto);
        }
    }
}
