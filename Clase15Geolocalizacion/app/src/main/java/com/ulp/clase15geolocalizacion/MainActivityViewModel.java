package com.ulp.clase15geolocalizacion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.TaskInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    //Usando libreria nativas
    private LocationManager manager;

            //CON LIBRERIAS EXTERNAS (GOOGLE)
    //Objeto que contiene de tipo de localizacion
    private FusedLocationProviderClient client;
    //Hacemos del callback un atributo para poder activar y desactivarlo
    private LocationCallback callback;
    private MutableLiveData<Location> mLocation = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        //El contexto es el de la activiti
        client = LocationServices.getFusedLocationProviderClient(application);

        manager = (LocationManager) application.getSystemService(application.LOCATION_SERVICE);
    }

    public LiveData<Location> getMLocation() {
        return mLocation;
    }

        //Este metodo get devuelve la tarea la cual promete devolver una location
public void obtenerLocalizacion(){
    if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
    }
    Task<Location> tareaLocalizacion = client.getLastLocation();

        //Necesita un EXecuter no un contexto
        tareaLocalizacion.addOnSuccessListener(getApplication().getMainExecutor(), new  OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    //Como es un listener y ademas la tarea es una promesa, es un metodo post
                    mLocation.postValue(location);
                }
            }
        });
    }
    public void verCambios() {
        //usamos client para registrar las lecturas
        //Debe ser del paquete gms no el de android ver importaciones en caso de error
        //CONFIGURACION
        LocationRequest locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000
        ).build();


        //Hay que sobre escribir estos dos metodos
        //LO QUE DEBE HACER CON LOS DATOS
         callback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null) {
                    //obtenemos la ultima localizacion
                    Location location = locationResult.getLastLocation();
                    //seteamos el mutable con los datos dinamicos
                    mLocation.postValue(location);
                    //Para ver los cambios aunque la app este cerrada
                    Toast.makeText(getApplication(), location.getLatitude()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };
        //usamos client. El segundo parametro es un pending intent que no usaremos en este caso
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        client.requestLocationUpdates(locationRequest, callback, null);

    }

    public void desactivarLecturas(){
        //Usamos el callback atributo
        client.removeLocationUpdates(callback);
    }


    public void leerCambiosHerramientaLocal(){
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        5000,

        5,

        new MiListenerPosition()

        );
    }


    //Creamos una clase interna para usarla en el metodo del manager
    private class MiListenerPosition implements android.location.LocationListener{


        @Override
        public void onLocationChanged(@NonNull Location location) {
            //Aca modificamos la localizacion
            mLocation.postValue(location);

        }
    }
}
