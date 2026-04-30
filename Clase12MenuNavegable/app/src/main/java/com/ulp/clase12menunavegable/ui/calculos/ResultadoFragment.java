package com.ulp.clase12menunavegable.ui.calculos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.clase12menunavegable.R;
import com.ulp.clase12menunavegable.databinding.FragmentCalculoBinding;
import com.ulp.clase12menunavegable.databinding.FragmentResultadoBinding;


//Este fragment esta dentro del paquete de calculo porque estan relacionados.
//A este fragment lo queremos navegar pero no queremos que se vea
public class ResultadoFragment extends Fragment {

    private ResultadoViewModel mViewModel;
    private FragmentResultadoBinding binding;

    public static ResultadoFragment newInstance() {
        return new ResultadoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentResultadoBinding.inflate(inflater, container, false);

        
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ResultadoViewModel.class);
        //El observer va aca. El contexto es getViewLifecycleOwner
        mViewModel.getResultado().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer resultado) {
                binding.tvResultado.setText(resultado+"");
            }
        });
        mViewModel.getMostrarTexto().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    binding.tvTipo.setText(s);
            }
        });

        //Utilizamos ese metodo para obtener el bundle que recibe del otro fragment

        mViewModel.recuperarDatos(getArguments());
        // TODO: Use the ViewModel
    }

}