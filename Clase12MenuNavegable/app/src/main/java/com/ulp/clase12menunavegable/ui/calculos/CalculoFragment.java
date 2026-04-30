package com.ulp.clase12menunavegable.ui.calculos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.clase12menunavegable.R;
import com.ulp.clase12menunavegable.databinding.FragmentCalculoBinding;

public class CalculoFragment extends Fragment {

    private CalculoViewModel mViewModel;


    //Creamos el binding necesario
    private FragmentCalculoBinding binding;


    //Esto se puede borrar
//    public static CalculoFragment newInstance() {
//        return new CalculoFragment();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //viene el inflater por parametro y donde se va a inflar mas la propiedad en falso.
        binding = FragmentCalculoBinding.inflate(inflater, container, false);

        binding.btCalcularSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n1 = Integer.parseInt(binding.etNumero1.getText().toString());
                int n2 = Integer.parseInt(binding.etNumero2.getText().toString());

                //Vamos a pasar los datos antes de realizar el calculo
                //No usaremos un putExtra sino que un BUNDLE utilizando el metodo putIn
                Bundle bundle = new Bundle();
                bundle.putInt("num1",n1);
                bundle.putInt("num2",n2);
                bundle.putBoolean("operacion",true);


                //Es aquel que maneja los puntos de ida y de vuelta. Ademas el metodo que de navegacion se especifica a
                //que fragment irá y que informacion llevara con sigo en este caso el bundle. Esto ultimo lo hacemos con la accion de navigacion entre
                //los fragments relacionados
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main)
                        .navigate(R.id.action_nav_calculo_to_resultadoFragment, bundle);
            }
        });
        binding.btResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n1 = Integer.parseInt(binding.etNumero1.getText().toString());
                int n2 = Integer.parseInt(binding.etNumero2.getText().toString());

                //Vamos a pasar los datos antes de realizar el calculo
                //No usaremos un putExtra sino que un BUNDLE utilizando el metodo putIn
                Bundle bundle = new Bundle();
                bundle.putInt("num1",n1);
                bundle.putInt("num2",n2);
                bundle.putBoolean("operacion",false);


                //Es aquel que maneja los puntos de ida y de vuelta. Ademas el metodo que de navegacion se especifica a
                //que fragment irá y que informacion llevara con sigo en este caso el bundle
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nav_resultado, bundle);
            }
        });
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalculoViewModel.class);
        // TODO: Use the ViewModel
    }

}