package com.ulp.clase15geolocalizacion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Matrix44;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.clase15geolocalizacion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
private MainActivityViewModel mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Instalamos la libreria necesaria en Module seting com.google.android.gms:play-services-location
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        solicitarPermisosLocalizacion();
        mv  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        mv.getMLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                double latitud = location.getLatitude();
                double longitud = location.getLongitude();
                binding.tvMostrarLoc.setText("latitud"+latitud+" long"+longitud);
            }
        });
        //mv.obtenerLocalizacion();
        //Actualizamos el metodo para que el dato de lectura se dinamico segun la poscion
//        mv.verCambios();
        //Actualizamos para usar herramienta local
        mv.leerCambiosHerramientaLocal();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Lo hacemos cuando la app se descarte
        mv.desactivarLecturas();
    }

    public void solicitarPermisosLocalizacion(){
        //Tras declarar los permisos en el manifest los solicitamos en la activity cuando inicia
        //la aplicacion
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ||(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
    }
}