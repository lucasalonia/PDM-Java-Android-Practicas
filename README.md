# Prácticas de Programación en Dispositivos Móviles (Java)

Este repositorio centraliza las aplicaciones desarrolladas durante el cursado de la asignatura. Cada proyecto explora diferentes componentes del ecosistema Android, desde la gestión de interfaces hasta el uso de hardware específico como sensores y GPS.

## Ficha Técnica General

Todas las aplicaciones comparten los siguientes estándares técnicos:

| Parámetro | Valor |
| :--- | :--- |
| **IDE** | Android Studio Flamingo / Hedgehog (Panda 2 → actualización a 4) |
| **Lenguaje** | Java 11 (`VERSION_11`) |
| **SDK Mínimo** | API 36 |
| **SDK Objetivo** | API 36 |
| **Diseño de Interfaz** | XML con Material Design y ConstraintLayout |
| **Vinculación de Vistas** | View Binding (activado en `build.gradle.kts`) |
| **Arquitectura base** | MVVM (Model–View–ViewModel) con LiveData |

---

## Índice de Proyectos

| Carpeta | Temática / Conceptos Clave |
| :--- | :--- |
| [ClaseDos](#1-clasedos--primera-actividad-y-estructura-base) | Primera Activity, estructura base de un proyecto Android |
| [Clase4](#2-clase4--intents-navegación-entre-activities-y-acceso-al-registro-de-llamadas) | Intents explícitos, navegación entre Activities, ContentResolver, permisos en tiempo de ejecución |
| [Clase5BroadcastReceiver](#3-clase5broadcastreceiver--broadcastreceiver-y-eventos-del-sistema) | BroadcastReceiver, IntentFilter, ciclo de vida del receptor |
| [Clase6Servicios](#4-clase6servicios--services-y-tareas-en-segundo-plano) | Services, MediaPlayer, Thread, START_STICKY |
| [Clase7_Views](#5-clase7_views--view-binding-y-viewmodel-introducción) | View Binding, ViewModel, MutableLiveData, Observer |
| [Clase8PracticaVM](#6-clase8practicavm--viewmodel-con-modelo-de-datos-y-navegación-desde-el-vm) | ViewModel + Modelo (POJO), MutableLiveData de objeto, Intent desde ViewModel |
| [Clase8VMYDobleView](#7-clase8vmdoblecview--doble-actividad-con-viewmodels-independientes) | Doble Activity con ViewModel independiente por vista |
| [Clase9ListVieww](#8-clase9listvieww--listview-con-adapter-personalizado) | ListView, ArrayAdapter personalizado, Serializable, DetalleActivity |
| [Clase10RecycleView_CardView](#9-clase10recycleview_cardview--recyclerview-con-cardview) | RecyclerView, ViewHolder, CardView, GridLayoutManager, postValue |
| [Clase12MenuNavegable](#10-clase12menunavegable--navigation-component-drawerlayout-y-bottom-navigation) | Navigation Component, NavController, Fragments, DrawerLayout, Bundle entre Fragments |
| [Clase15Geolocalizacion](#11-clase15geolocalizacion--geolocalización-y-gps) | FusedLocationProviderClient, LocationManager, permisos de ubicación |
| [Clase16GoogleMaps](#12-clase16googlemaps--integración-de-google-maps) | Google Maps SDK, SupportMapFragment, marcadores, cámara |
| [Clase17Sensores](#13-clase17sensores--sensores-de-hardware) | SensorManager, Sensor, SensorEventListener, acelerómetro |
| [Clase18SharedPreference](#14-clase18sharedpreference--persistencia-con-sharedpreferences) | SharedPreferences, Editor, persistencia local sin base de datos |
| [ClaseBroadcastUSB / ClaseBroadCastUSB19](#15-clasebroadcastusb--clasebroadcastusb19--proyectos-sin-código) | Proyectos creados sin código fuente desarrollado |

---

## 1. `ClaseDos` — Primera Activity y Estructura Base

### Descripción

Proyecto inicial de la cursada. Sirve para familiarizarse con la estructura de un proyecto Android en Android Studio: la clase `MainActivity`, el método `onCreate`, el archivo de layout XML y la configuración de Gradle.

### Conceptos trabajados

- `AppCompatActivity` como clase base de toda pantalla Android.
- `setContentView(R.layout.activity_main)` vincula la clase Java con su layout XML.
- `EdgeToEdge.enable(this)` habilita que el contenido de la app se dibuje detrás de las barras del sistema (status bar y navigation bar), dando una apariencia de pantalla completa.
- `ViewCompat.setOnApplyWindowInsetsListener` aplica padding dinámico a la vista raíz para que el contenido no quede tapado por las barras del sistema.

### Layout XML

**`activity_main.xml`**

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:background="@color/background">

    <TextView
        android:id="@+id/textOne"
        android:layout_width="206dp"
        android:layout_height="90dp"
        android:text="@string/textOne"
        android:textColor="@color/blue"
        android:textSize="30sp" />

    <TextView
        android:id="@+id/textTwo"
        android:layout_width="163dp"
        android:layout_height="65dp"
        android:text="@string/textTwo"
        android:textColor="@color/red"
        android:textSize="20sp" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Los textos se definen en `res/values/strings.xml` y los colores en `res/values/colors.xml`, siguiendo la buena práctica de no hardcodear strings ni colores en el layout. `@color/background` define el fondo general de la pantalla.

---

## 2. `Clase4` — Intents, Navegación entre Activities y Acceso al Registro de Llamadas

### Descripción

Introduce la navegación entre pantallas mediante **Intents explícitos** y el acceso a datos del sistema (registro de llamadas) a través del `ContentResolver`. También se trabaja el pedido de permisos en tiempo de ejecución.

### Procedimiento paso a paso

#### A. Declarar vistas como atributos de la clase

```java
// Forma antigua de nombrar los elementos a través de atributos de la clase
private EditText dato;
private Button siguiente;
```

> Declarar las vistas como atributos permite que todos los métodos de la clase puedan acceder a ellas sin tener que buscarlas repetidamente con `findViewById`.

#### B. Vincular vistas en `onCreate`

```java
dato = findViewById(R.id.editTextDato);
siguiente = findViewById(R.id.btSiguiente);
```

> `findViewById` busca el componente en toda la jerarquía de vistas de la activity. El error sería usar el id de un componente que no está en la vista actual.

#### C. Escuchar eventos con clase anónima

```java
// Función propia de la clase Button que escucha eventos
// Hacemos una clase anónima de listener
siguiente.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // lógica del clic
    }
});
```

#### D. Crear y lanzar un Intent explícito

```java
// Creamos el Intent instanciando a Intent
Intent i = new Intent(MainActivity.this, SecondActivity.class);
// this se refiere al contexto actual. Dentro de una clase anónima, se usa MainActivity.this
// .class pasa al sistema un "plano" o la "identidad" de la clase que se quiere abrir
i.putExtra("dato", mensaje); // mapa clave-valor para enviar datos
startActivity(i);            // startActivity() vive en la clase Context
```

#### E. Recibir el Intent en la segunda Activity

```java
// Es la forma en la que SecondActivity recibe el "paquete" enviado desde MainActivity
Intent i = getIntent();
// Guardamos el dato usando la clave del mapa enviado por el intent
String datoExtraido = i.getStringExtra("dato");
mostraInfo.setText(datoExtraido);
```

#### F. Pedir permisos en tiempo de ejecución

```java
public void pedirPermiso() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && checkSelfPermission(Manifest.permission.READ_CALL_LOG)
               != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, 1000);
    }
}
```

> A partir de Android 6.0 (Marshmallow, API 23) los permisos peligrosos deben pedirse en tiempo de ejecución, además de declararlos en el `AndroidManifest.xml`.

#### G. Consultar el registro de llamadas con `ContentResolver`

```java
// Buscamos en la dirección pública de llamadas del móvil
Uri llamadas = Uri.parse("content://call_log/calls");
ContentResolver cr = getContentResolver();

// Hacemos consulta a la base de datos del dispositivo en SQLite → SELECT * FROM llamadas
Cursor cursor = cr.query(llamadas, null, null, null, null);
// Cursor devuelve los resultados de la query y hay que recorrerlos

if (cursor.getCount() > 0) {
    while (cursor.moveToNext()) {
        // Obtenemos el índice con el nombre de la columna
        int indiceNumero   = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int indiceDuracion = cursor.getColumnIndex(CallLog.Calls.DURATION);

        // Seteamos en variables los valores
        nro    = cursor.getString(indiceNumero);
        tiempo = cursor.getString(indiceDuracion);
        mensaje.append(nro + " " + tiempo + "\n");
    }
    mostraInfo.setText(mensaje.toString());
}
```

> `StringBuilder` se usa para concatenar cadenas de texto de manera eficiente, evitando crear múltiples objetos `String` inmutables en el heap.

### Layout XML

**`activity_main.xml`** — pantalla de entrada

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <EditText
        android:id="@+id/editTextDato"
        android:layout_width="match_parent"
        android:inputType="text"
        android:text="Nombre" />

    <Button
        android:id="@+id/btSiguiente"
        android:text="Siguiente"
        app:layout_constraintTop_toBottomOf="@+id/editTextDato" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> El `EditText` con `inputType="text"` activa el teclado alfanumérico. El `Button` se posiciona debajo del `EditText` usando `constraintTop_toBottomOf`.

**`activity_second.xml`** — pantalla de destino

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView
        android:id="@+id/textShow"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> El `TextView` ocupa toda la pantalla (`match_parent` en ancho y alto) para poder mostrar el historial de llamadas completo, que puede ser extenso.

---

## 3. `Clase5BroadcastReceiver` — BroadcastReceiver y Eventos del Sistema

### Descripción

Implementa un receptor de anuncios del sistema para detectar cambios en el estado del puerto USB (conectado / desconectado). Introduce el concepto de **registro dinámico** del receptor, ligado al ciclo de vida de la Activity.

### Procedimiento paso a paso

#### A. Crear la clase receptora

```java
// Creada a partir de New → Other → BroadcastReceiver
public class CambioUSB extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Usamos el método del intent que devuelve un booleano (formato clave-valor)
        boolean estado = intent.getBooleanExtra("connected", false);

        if (estado) {
            // Los parámetros son: contexto (que trae la información),
            // mensaje a mostrar y duración del toast
            Toast.makeText(context, "USB conectado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "USB desconectado", Toast.LENGTH_LONG).show();
        }
        // El intent ya viene armado; hay que saber qué información trae
        // dependiendo de a quién se está escuchando
    }
}
```

#### B. Declarar el receptor como atributo en la Activity

```java
// La clase que extiende de BroadcastReceiver la usaremos dentro del flujo de la Activity
// El objeto nunca se instancia antes de usarlo (fuera de métodos)
private CambioUSB cambioUSB;
```

#### C. Instanciar en `onCreate`

```java
// Lo creamos en onCreate porque lo vamos a usar siempre
// Pero aun así no se hace fuera de los métodos
cambioUSB = new CambioUSB();
```

#### D. Registrar y desregistrar según el ciclo de vida

```java
@Override
protected void onResume() {
    super.onResume();
    // Lo ponemos acá para que se reduzca el consumo y solo
    // escuche cuando el usuario esté interactuando con la app
    // Primer parámetro: el objeto BroadcastReceiver
    // Segundo parámetro: IntentFilter que le avisa al SO qué evento escuchar
    registerReceiver(cambioUSB, new IntentFilter("android.hardware.usb.action.USB_STATE"));
}

@Override
protected void onPause() {
    super.onPause();
    unregisterReceiver(cambioUSB); // Desregistramos para liberar recursos
}

@Override
protected void onStop() {
    super.onStop();
    unregisterReceiver(cambioUSB);
}
```

> El registro dinámico (en código) es la forma recomendada para eventos que solo importan cuando la app está en primer plano. El registro estático (en `AndroidManifest.xml`) es para eventos que deben recibirse aunque la app esté cerrada.

### Layout XML

**`activity_main.xml`** — no tiene UI funcional

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> La UI de esta app es intencionalmente mínima porque toda la lógica vive en el `BroadcastReceiver`. La información al usuario se comunica exclusivamente a través de `Toast.makeText()`, que no requiere ningún componente en el layout.

---

## 4. `Clase6Servicios` — Services y Tareas en Segundo Plano

### Descripción

Introduce el componente `Service` para ejecutar procesos en segundo plano. La aplicación reproduce música con `MediaPlayer` mientras el servicio está activo. Incluye un ejemplo comentado de cómo ejecutar tareas pesadas en un `Thread` paralelo.

### Procedimiento paso a paso

#### A. Crear el Service

```java
public class ServicioLibre extends Service {
    // Creamos variable atributo MediaPlayer
    // Las variables atributo no se instancian en el constructor sino en onCreate
    private MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();
        // Necesita el contexto y el recurso. Lo buscamos en el directorio res/raw
        mp = MediaPlayer.create(this, R.raw.roxette_1_1);
    }

    // Método sobreescrito que llevará los procesos del servicio.
    // Recibe un intent, un estado y un id.
    // Procede como una FUNCIÓN (devuelve entero), no como un PROCEDIMIENTO
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start(); // Los procesos suceden acá dentro

        // Si llega hasta el final tiene que devolver un entero que indique el
        // éxito de la función. Devuelve el entero guardado en una constante.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop(); // Cuando el servicio termina su tarea
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // No se implementa binding en este caso
    }
}
```

> `START_STICKY` le indica al sistema operativo que si el servicio es destruido por falta de memoria, debe recrearlo cuando haya recursos disponibles.

#### B. Iniciar el Service desde la Activity

```java
// El intent espera el contexto (this = MainActivity) y el servicio a comunicar.
// La clase lleva .class porque el constructor de Intent no necesita una instancia
// del servicio, sino una referencia a la definición de la clase:
//   ServicioLibre → el plano de construcción (la clase en sí)
//   ServicioLibre.class → objeto de tipo Class<ServicioLibre>, un "token" con los metadatos
private Intent intentQueComunicaElServicio;

@Override
protected void onCreate(Bundle savedInstanceState) {
    intentQueComunicaElServicio = new Intent(this, ServicioLibre.class);
}

@Override
protected void onResume() {
    super.onResume();
    startService(intentQueComunicaElServicio); // Una buena práctica sería iniciarlo acá
}
```

#### C. Declarar el Service en el AndroidManifest.xml

```xml
<service
    android:name=".ServicioLibre"
    android:enabled="true"
    android:exported="false" />
```

#### D. Ejemplo de tarea pesada con Thread (incluido como referencia en el código)

```java
// Para emular una tarea pesada (cómputo, búsqueda en API, lectura GPS)
// necesitamos generar un contexto de ejecución paralelo creando hilos.
// Creamos una clase anónima (POLIMORFISMO):
Thread trabajador = new Thread() {
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(10000); // Duerme 10 segundos
            } catch (InterruptedException e) { }
            // Se despierta y realiza esta acción
            Toast.makeText(ServicioLibre.this, "Pasó", Toast.LENGTH_SHORT).show();
        }
    }
};
trabajador.start();
```

> Nota: Un `Toast` no puede mostrarse desde un hilo secundario directamente. Para producción se debe usar `runOnUiThread` o `postValue` de LiveData.

### Layout XML

**`activity_main.xml`** — no tiene UI funcional

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Al igual que en `Clase5`, la UI está vacía porque el `Service` corre en segundo plano. No hay botones ni textos que interactúen con el usuario; el servicio se inicia automáticamente en `onResume()` y todo el proceso (reproducción de audio) ocurre sin intervención del usuario.

---

## 5. `Clase7_Views` — View Binding y ViewModel (Introducción)

### Descripción

Primera aproximación formal a la arquitectura **MVVM**. Introduce `View Binding` como reemplazo de `findViewById`, y `ViewModel` + `MutableLiveData` para separar la lógica de negocio de la interfaz.

### Procedimiento paso a paso

#### A. Activar View Binding en `build.gradle.kts`

```kotlin
android {
    buildFeatures {
        viewBinding = true
    }
}
```

> Una vez sincronizado, Android Studio genera automáticamente una clase de binding por cada layout XML. Para `activity_main.xml` genera `ActivityMainBinding`.

#### B. Declarar y usar el binding en la Activity

```java
// Si tenemos View Binding activado, buscamos la clase generada para la vista.
private ActivityMainBinding binding;

// Inicializamos la clase de la vista previo al setContentView
binding = ActivityMainBinding.inflate(getLayoutInflater());

// La variable binding representará la vista, por lo que hay que modificar setContentView
// Utilizamos la variable con la clase y buscamos la raíz
setContentView(binding.getRoot());
```

> Antes del View Binding se usaba `Button btContar = findViewById(R.id.contar)`. Con binding se accede como `binding.contar` directamente, con verificación de tipos en tiempo de compilación.

#### C. Instanciar el ViewModel

```java
// Creamos un atributo con la clase ViewModel que vamos a utilizar
private MainActivityViewModel mv;

// La instancia es así de compleja (luego en clases posteriores se simplifica)
mv = new ViewModelProvider(this).get(MainActivityViewModel.class);
```

#### D. Crear el ViewModel con MutableLiveData

```java
// Para que sea un ViewModel debe extender de AndroidViewModel
public class MainActivityViewModel extends AndroidViewModel {

    // La acción de incrementar el número del contador debería ir acá
    // y no directamente en la MainActivity
    private int c = 0;

    // Para que la vista se entere que el contador cambió de estado,
    // hay que guardarlo en un objeto especial (MutableLiveData).
    // La Activity observa este mutable y reacciona a cualquier cambio.
    private MutableLiveData<Integer> contadorMutable;

    // Supertipo de Mutable: el getter expone LiveData (solo lectura)
    public LiveData<Integer> getContadorMutable() {
        if (contadorMutable == null) {
            contadorMutable = new MutableLiveData<>();
            contadorMutable.setValue(c); // Para que el texto muestre el valor inicial
        }
        return contadorMutable;
    }

    // En este caso no debe devolver nada (procedimiento, no función)
    public void contar() {
        c++;
        contadorMutable.setValue(c);
    }
}
```

#### E. Observar el LiveData desde la Activity

```java
// new Observer crea un callback, un proceso en segundo plano que queda
// pendiente del mutable y reacciona a cualquier cambio
mv.getContadorMutable().observe(this, new Observer<Integer>() {
    @Override
    public void onChanged(Integer c) {
        // HAY QUE SÍ O SÍ TRANSFORMAR EL PARÁMETRO EN STRING
        binding.mostrar.setText(c + "");
    }
});
```

#### F. Delegar la acción al ViewModel desde el listener

```java
binding.contar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Utilizamos el método de contar provisto por el ViewModel
        mv.contar();
        // Ya no incrementamos c++ directamente aquí: esa es responsabilidad del ViewModel
    }
});
```

### Layout XML

**`activity_main.xml`** — la misma pantalla tiene dos funcionalidades: contador y traductor

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <!-- SECCIÓN CONTADOR -->
    <TextView android:id="@+id/textView2"
        android:text="Contador"
        android:textSize="30sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toTopOf="@+id/mostrar" />

    <TextView android:id="@+id/mostrar"
        android:textSize="24sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.257" />

    <Button android:id="@+id/contar"
        android:text="+"
        android:textSize="24sp"
        app:layout_constraintTop_toBottomOf="@+id/mostrar" />

    <!-- SECCIÓN TRADUCTOR -->
    <TextView android:id="@+id/textView3"
        android:text="Traductor"
        android:textSize="30sp"
        app:layout_constraintTop_toBottomOf="@+id/contar" />

    <EditText android:id="@+id/traducirTexto"
        android:layout_width="158dp"
        android:inputType="text"
        app:layout_constraintTop_toBottomOf="@+id/textView3" />

    <Button android:id="@+id/btTraducir"
        app:layout_constraintTop_toBottomOf="@+id/traducirTexto" />

    <TextView android:id="@+id/mostrarTraduccion"
        app:layout_constraintTop_toBottomOf="@+id/btTraducir" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Esta pantalla combina dos funcionalidades independientes en un mismo layout. Los ids `mostrar`, `contar`, `traducirTexto`, `btTraducir` y `mostrarTraduccion` son los que el View Binding genera como atributos de `ActivityMainBinding`, accesibles como `binding.mostrar`, `binding.contar`, etc.

---

## 6. `Clase8PracticaVM` — ViewModel con Modelo de Datos y Navegación desde el VM

### Descripción

Extiende el patrón MVVM introduciendo un **modelo POJO** (`Persona`) y un `MutableLiveData<Persona>` que encapsula un objeto completo en lugar de datos individuales. Demuestra cómo iniciar una Activity desde el ViewModel usando el contexto de la `Application`.

### Procedimiento paso a paso

#### A. Crear el modelo de datos (POJO)

```java
public class Persona {
    private String nombre;
    private String apellido;
    private String email;

    // Constructor con todos los campos
    public Persona(String nombre, String email, String apellido) { ... }

    // Getters y setters
    public String getNombre() { return nombre; }
    // ...
}
```

#### B. MutableLiveData de un objeto completo vs. múltiples LiveData

```java
// Versión inicial (comentada en el código): un MutableLiveData por campo
private MutableLiveData<String> nombre;
private MutableLiveData<String> apellido;
private MutableLiveData<String> email;

// Versión final: un solo MutableLiveData que encapsula el objeto completo
// "Vamos a hacerlo con un objeto — esto se hizo en la clase 9"
private MutableLiveData<Persona> personaMutable;

public LiveData<Persona> getPersonaMutable() {
    if (personaMutable == null) {
        personaMutable = new MutableLiveData<>();
    }
    return personaMutable;
}
```

#### C. Lanzar un Intent desde el ViewModel

```java
public void buscarAlumno(String nombreABuscar) {
    for (String a : listaAlumnos) {
        if (a.equalsIgnoreCase(nombreABuscar)) {
            // 1. Crea el Intent explícito con el contexto de la Application y la clase destino
            Intent i = new Intent(getApplication(), SecondActivity.class);

            // 2. Agrega el flag obligatorio para iniciar una Activity desde un
            // contexto de Application (crea una nueva tarea en el back stack de Android)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // 3. Pasa datos extras (clave, valor) a la siguiente pantalla
            i.putExtra("nombre", nombreABuscar);

            // 4. Lanza la ejecución del Intent a través del sistema operativo
            getApplication().startActivity(i);
            return;
        }
    }
    alumnoMutable.setValue("No encontrado");
}
```

> **¿Por qué `FLAG_ACTIVITY_NEW_TASK`?** El Intent se lanza desde el ViewModel, que no es una Activity. Android requiere esta bandera cuando se inicia una Activity desde un contexto que no es una Activity (como `Application`).

#### D. Observar el LiveData de objeto en la segunda Activity

```java
// Recibimos el objeto completo de Persona y acá seleccionamos los que usamos
vm.getPersonaMutable().observe(this, new Observer<Persona>() {
    @Override
    public void onChanged(Persona p) {
        b.tvEmail.setText(p.getEmail());
        b.tvApellido.setText(p.getApellido());
        b.tvNombre.setText(p.getNombre());
    }
});

vm.obtenerNombre(getIntent().getStringExtra("nombre"));
```

### Layout XML

**`activity_main.xml`** — busca un alumno por nombre

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <EditText android:id="@+id/etAlumno"
        android:layout_width="241dp"
        android:inputType="text"
        android:text="Name"
        app:layout_constraintTop_toTopOf="parent" />

    <Button android:id="@+id/btBuscar"
        android:text="Button"
        app:layout_constraintTop_toBottomOf="@+id/etAlumno" />

    <TextView android:id="@+id/tvAlumnoEncontrado"
        android:text="TextView"
        app:layout_constraintTop_toBottomOf="@+id/btBuscar" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

**`activity_second.xml`** — muestra los datos del alumno encontrado

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView android:id="@+id/tvNombre"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView android:id="@+id/tvApellido"
        app:layout_constraintTop_toBottomOf="@+id/tvNombre" />

    <TextView android:id="@+id/tvEmail"
        app:layout_constraintTop_toBottomOf="@+id/tvApellido" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Los tres `TextView` de `activity_second.xml` no tienen texto inicial (`android:text` ausente), a diferencia de `Clase8VMYDobleView` donde aparecen con `"TextView"` como placeholder. Todos se llenan en tiempo de ejecución a través del Observer del ViewModel.

---

## 7. `Clase8VMYDobleView` — Doble Activity con ViewModels Independientes

### Descripción

Refuerza el patrón de dos Activities con un ViewModel independiente para cada una. Muestra el enfoque con **3 `MutableLiveData<String>` separadas** (nombre, apellido, email) en la segunda Activity, en contraste con el enfoque de objeto único del proyecto anterior.

### Conceptos clave

- `ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(...)` como forma directa de instanciar ViewModels que extienden `AndroidViewModel`.
- Cada pantalla tiene su propio ViewModel con sus propios `MutableLiveData`.
- La navegación sigue el mismo patrón: Intent con `FLAG_ACTIVITY_NEW_TASK` lanzado desde el ViewModel de la primera Activity.
- La segunda Activity recibe el nombre por `getIntent().getStringExtra("nombre")` y lo pasa a su ViewModel.

### Comparación con `Clase8PracticaVM`

| Aspecto | `Clase8VMYDobleView` | `Clase8PracticaVM` |
| :--- | :--- | :--- |
| LiveData en SecondActivity | 3 separados (`nombre`, `apellido`, `email`) | 1 de tipo `Persona` |
| Observadores en la Activity | 3 observers distintos | 1 observer que desempaqueta el objeto |
| Mantenimiento | Más código, más verboso | Más limpio y escalable |

### Layout XML

**`activity_main.xml`** — primera pantalla: busca por nombre

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <EditText android:id="@+id/etNombre"
        android:layout_width="248dp"
        android:hint="Ingrese nombre"
        android:inputType="text"
        app:layout_constraintTop_toTopOf="parent" />

    <Button android:id="@+id/bBuscar"
        android:text="Buscar"
        app:layout_constraintTop_toBottomOf="@+id/etNombre" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> El atributo `android:hint` muestra texto de ayuda en gris cuando el campo está vacío, sin que sea el valor real. A diferencia de `Clase8PracticaVM`, acá se usa `hint` en lugar de `android:text` — mejor práctica para campos de entrada.

**`activity_segunda.xml`** — segunda pantalla: muestra datos

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView android:id="@+id/tvNombre"
        android:layout_width="277dp"
        android:layout_height="65dp"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView android:id="@+id/tvApellido"
        android:layout_width="277dp"
        android:layout_height="65dp"
        app:layout_constraintTop_toBottomOf="@+id/tvNombre" />

    <TextView android:id="@+id/tvEmail"
        android:layout_width="277dp"
        android:layout_height="65dp"
        app:layout_constraintTop_toBottomOf="@+id/tvApellido" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Los `TextView` tienen dimensiones fijas (`277dp × 65dp`) para dar espacio visual a cada campo. Todos sin texto inicial, se llenan desde el Observer del ViewModel.

---

## 8. `Clase9ListVieww` — ListView con Adapter Personalizado

### Descripción

Implementa una lista de personas con fotos usando `ListView` y un `ArrayAdapter` personalizado. Introduce el patrón de **ítem clickeable** que navega a una pantalla de detalle, pasando el objeto completo mediante `Serializable`.

### Procedimiento paso a paso

#### A. El modelo debe implementar `Serializable`

```java
// Primero creamos la clase Persona
// Android indexa la imagen a través de un id (int)
public class Persona implements Serializable {
    private String apellido;
    private String nombre;
    private String correo;
    private int foto; // id del recurso drawable
    // ...
}
```

> `Serializable` es la interfaz de Java que permite que un objeto se convierta en una secuencia de bytes para ser transmitido por un `Intent`. Es menos eficiente que `Parcelable` (la versión Android), pero más simple de implementar.

#### B. Crear el ViewModel con la lista de datos

```java
// MutableLiveData inicializado directamente para cargar datos en el constructor
private MutableLiveData<List<Persona>> personasMutable = new MutableLiveData<>();

public MainActivityViewModel(@NonNull Application application) {
    super(application);
    List<Persona> personas = new ArrayList<>();
    personas.add(new Persona("Carlo", "Carlo", "c@efe.com", R.drawable.persona1));
    // ...
    personasMutable.setValue(personas);
}
```

#### C. Crear el Adapter personalizado

```java
// extends ArrayAdapter<Persona> — adaptador de la librería para conectar datos con la vista
public class PersonaAdapter extends ArrayAdapter<Persona> {
    private List<Persona> personas;  // conoce cuáles son las personas
    private Context context;          // el contexto de la app
    private LayoutInflater li;        // para inflar cada persona en el layout item
    private int itemMostrar;          // id del layout del ítem

    // Constructor con contexto, id del recurso (layout item),
    // lista de elementos y LayoutInflater agregado manualmente
    public PersonaAdapter(@NonNull Context context, int resource,
                          @NonNull List<Persona> objects, LayoutInflater li) {
        super(context, resource, objects);
        this.personas   = objects;
        this.context    = context;
        this.li         = li;
        this.itemMostrar = resource;
    }
```

#### D. Implementar `getView` — el corazón del Adapter

```java
// Método de ArrayAdapter que accede a cada ítem de la lista y configura cada persona.
// Retorna una vista (View) que es cada ítem incrustado en el ListView.
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View item = convertView;
    if (item == null) {
        // Parent es el contenedor padre (el ListView).
        // false para que la lista gestione sus propios elementos.
        item = li.inflate(itemMostrar, parent, false);
    }
    // Recuperamos los componentes del ítem a través de su id
    ImageView foto    = item.findViewById(R.id.foto);
    TextView apellido = item.findViewById(R.id.apellido);
    TextView nombre   = item.findViewById(R.id.nombre);
    TextView correo   = item.findViewById(R.id.correo);

    // Obtenemos la persona en la posición actual
    Persona p = personas.get(position);
    foto.setImageResource(p.getFoto());
    apellido.setText(p.getApellido());
    nombre.setText(p.getNombre());
    correo.setText(p.getCorreo());

    // ítem es un VIEW, por lo que se puede usar setOnClickListener
    item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // ADAPTER NO FUNCIONA COMO CONTEXTO, usamos el contexto del constructor
            Intent i = new Intent(context, DetalleActivity.class);
            i.putExtra("persona", p); // pasamos el objeto Serializable

            // Añadimos bandera para indicarle al JVM que el inicio del intent
            // no es desde una Activity
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    });
    return item;
}

// Método para saber la cantidad de personas a recorrer
@Override
public int getCount() {
    return personas.size();
}
```

#### E. Configurar el ListView en la Activity

```java
mv.getPersonaMutable().observe(this, new Observer<List<Persona>>() {
    @Override
    public void onChanged(List<Persona> personas) {
        // Configuramos el Adapter con: contexto, id del layout del ítem,
        // la lista de personas y el LayoutInflater
        PersonaAdapter pa = new PersonaAdapter(
            MainActivity.this, R.layout.item, personas, getLayoutInflater()
        );
        // setAdapter asigna el adaptador al ListView
        binding.listado.setAdapter(pa);
    }
});
```

#### F. Recuperar el objeto en `DetalleActivityViewModel`

```java
public void recuperarPersona(Intent intent) {
    // getSerializableExtra con la nueva API que requiere la clase como segundo parámetro
    Persona p = intent.getSerializableExtra("persona", Persona.class);
    if (p != null) {
        if ((p.getApellido() != null && !p.getApellido().isEmpty()) && (p.getCorreo() != null)) {
            personaRecuperadaMutable.setValue(p);
        }
    }
}
```

### Layout XML

**`activity_main.xml`** — contiene solo el `ListView`

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <ListView
        android:id="@+id/listado"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="1dp" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> El `ListView` con `match_parent` en ancho y alto ocupa toda la pantalla. El `margin` de 1dp evita que el primer y último ítem queden pegados al borde.

**`item.xml`** — plantilla de cada fila de la lista

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView android:id="@+id/foto"
        android:layout_width="300px"
        android:layout_height="300px"
        android:layout_marginTop="56dp"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView android:id="@+id/apellido"
        android:layout_width="match_parent"
        app:layout_constraintTop_toBottomOf="@+id/foto" />

    <TextView android:id="@+id/nombre"
        android:layout_width="match_parent"
        app:layout_constraintTop_toBottomOf="@+id/apellido" />

    <TextView android:id="@+id/correo"
        android:layout_width="match_parent"
        app:layout_constraintTop_toBottomOf="@+id/nombre" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> La imagen usa `px` (píxeles físicos) en lugar de `dp`. Esto hace que el tamaño varíe según la densidad de pantalla del dispositivo. La práctica recomendada es usar `dp` para que el diseño sea consistente en todos los dispositivos.

**`activity_detalle.xml`** — pantalla de detalle al tocar un ítem

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <ImageView android:id="@+id/fotoDetalle"
        android:layout_width="406dp"
        android:layout_height="197dp"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView android:id="@+id/apellidoDetalle"
        android:layout_width="match_parent"
        app:layout_constraintTop_toBottomOf="@+id/fotoDetalle" />

    <TextView android:id="@+id/nombreDetalle"
        android:layout_width="match_parent"
        android:layout_marginTop="76dp"
        app:layout_constraintTop_toBottomOf="@+id/fotoDetalle" />

    <TextView android:id="@+id/correoDetalle"
        android:layout_width="match_parent"
        android:layout_marginTop="108dp"
        app:layout_constraintTop_toBottomOf="@+id/fotoDetalle" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Los tres `TextView` de detalle (apellido, nombre, correo) están todos anclados al `fotoDetalle` con distintos `marginTop`, lo que los apila visualmente debajo de la imagen. La imagen tiene un ancho de `406dp` (ancho casi completo en un teléfono estándar).

---

## 9. `Clase10RecycleView_CardView` — RecyclerView con CardView

### Descripción

Migra de `ListView` a `RecyclerView`, el componente moderno y optimizado para listas. Introduce el patrón **ViewHolder** obligatorio, el `CardView` para diseño visual de cada ítem, y el `GridLayoutManager` para controlar la disposición. También demuestra cómo actualizar datos desde un hilo secundario con `postValue`.

### Diferencias clave entre ListView y RecyclerView

| Aspecto | `ListView` | `RecyclerView` |
| :--- | :--- | :--- |
| Adapter base | `ArrayAdapter` | `RecyclerView.Adapter` |
| ViewHolder | Opcional (patrón manual) | **Obligatorio** (clase interna) |
| Layout manager | Integrado, solo vertical | Configurable (Grid, Linear, Staggered) |
| Animaciones | Sin soporte nativo | Soporte nativo con `ItemAnimator` |
| Diseño del ítem | Layout XML simple | `CardView` para diseño con elevación/sombra |

### Procedimiento paso a paso

#### A. Crear el modelo de datos

```java
public class Inmueble {
    private String direccion;
    private double precio;
    private double superficie;
    // Es de tipo entero porque rastrea la imagen por el id dentro del proyecto.
    // Cuando las imágenes no estén en el proyecto, se buscará mediante una URI.
    private int foto;
    // ...
}
```

#### B. Agregar dependencias en `build.gradle.kts`

```kotlin
dependencies {
    implementation("androidx.recyclerview:recyclerview:1.3.x")
    implementation("androidx.cardview:cardview:1.0.0")
}
```

#### C. Crear el layout del ítem con `CardView`

```xml
<!-- item.xml -->
<androidx.cardview.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardElevation="4dp"
    app:cardCornerRadius="8dp">

    <!-- ImageView y TextViews del inmueble -->
</androidx.cardview.widget.CardView>
```

#### D. Crear el Adapter con la clase interna ViewHolder

```java
// Diferencias con ListView: se usa la clase Holder para tener la representación
// del modelo y conectar los datos con la vista que debe representarlos.
// Una vez con la clase interna creada, extendemos RecyclerView.Adapter
public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {

    // Este método devuelve un objeto ViewHolderInmueble,
    // que es la representación del XML de cada ítem
    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item, parent, false);
        return new ViewHolderInmueble(itemView);
    }

    // Este método se encarga del elemento de la lista según su posición
    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        // Utilizamos la representación de la vista y le asignamos
        // los valores del inmueble en el arreglo
        Inmueble inmuebleActual = inmuebles.get(position);
        holder.direccion.setText("Direccion " + inmuebleActual.getDireccion());
        holder.precio.setText("Precio " + inmuebleActual.getPrecio());
        holder.superficie.setText("Superficie " + inmuebleActual.getSuperficie());
        holder.foto.setImageResource(inmuebleActual.getFoto());
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    // Método para actualizar la lista después de cargar nuevos datos
    public void actualizarLista(List<Inmueble> nuevaLista) {
        inmuebles.clear();
        inmuebles.addAll(nuevaLista);
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos cambiaron
    }

    // Clase interna que extiende RecyclerView.ViewHolder.
    // Hace el match entre Java y los elementos XML.
    // ¡REPRESENTA A ITEM.XML!
    public class ViewHolderInmueble extends RecyclerView.ViewHolder {
        TextView direccion, superficie, precio;
        ImageView foto;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion  = itemView.findViewById(R.id.tvDireccion);
            superficie = itemView.findViewById(R.id.tvSuperficie);
            precio     = itemView.findViewById(R.id.tvPrecio);
            foto       = itemView.findViewById(R.id.ivFoto);
        }
    }
}
```

#### E. Configurar el RecyclerView con LayoutManager en la Activity

```java
vm.getInmueblesMutable().observe(this, new Observer<List<Inmueble>>() {
    @Override
    public void onChanged(List<Inmueble> inmuebles) {
        ia = new InmuebleAdapter(inmuebles, MainActivity.this, getLayoutInflater());

        // Al usar CardView necesitamos un LayoutManager.
        // GridLayoutManager: configura el orden de las tarjetas.
        // Parámetros: contexto, número de columnas, orientación, reverso
        GridLayoutManager glm = new GridLayoutManager(
            MainActivity.this, 1, GridLayoutManager.VERTICAL, false
        );

        binding.lista.setLayoutManager(glm);
        binding.lista.setAdapter(ia);
    }
});
vm.cargarInmuebles();
```

#### F. Actualizar la lista desde un hilo secundario con `postValue`

```java
// Primera forma de actualizar contenido desde un hilo paralelo
public void dormir() {
    List<Inmueble> l = new ArrayList<>();
    Thread t = new Thread() {
        public void run() {
            try {
                Thread.sleep(5000);
                l.add(new Inmueble("casaNueva", 4848, 84.88, R.drawable.casa3));
                // Método para actualizar un LiveData fuera del hilo principal.
                // setValue() solo funciona en el hilo principal.
                // postValue() encola el cambio para que se aplique en el hilo principal.
                inmuebleMutable2.postValue(l);
            } catch (Exception e) {
                Log.d("TAG", e + "");
            }
        }
    };
    t.start();
}
```

### Layout XML

**`activity_main.xml`** — contiene solo el `RecyclerView`

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/lista"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="1dp" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Igual que en `Clase9`, el componente de lista ocupa toda la pantalla. La diferencia es que aquí el componente es `RecyclerView` en lugar de `ListView`. El `RecyclerView` no sabe cómo mostrar los ítems por sí solo: necesita que la Activity le asigne tanto un `Adapter` como un `LayoutManager`.

**`item.xml`** — plantilla de cada tarjeta (`CardView` como raíz)

```xml
<androidx.cardview.widget.CardView
    android:id="@+id/cvEjemplo"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardBackgroundColor="#02032A"
    app:cardCornerRadius="30dp"
    app:cardElevation="20dp"
    app:contentPadding="30dp">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:background="@drawable/fondo">

        <TextView android:id="@+id/tvDireccion"
            android:layout_width="match_parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView android:id="@+id/tvPrecio"
            android:layout_width="match_parent"
            app:layout_constraintTop_toBottomOf="@+id/tvDireccion" />

        <TextView android:id="@+id/tvSuperficie"
            android:layout_width="match_parent"
            app:layout_constraintTop_toTopOf="@+id/tvPrecio" />

        <ImageView android:id="@+id/ivFoto"
            android:layout_width="200px"
            app:layout_constraintTop_toBottomOf="@+id/tvSuperficie" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</androidx.cardview.widget.CardView>
```

> El `CardView` es el **nodo raíz** del ítem. Sus atributos clave:
> - `cardBackgroundColor="#02032A"` — fondo casi negro para la tarjeta
> - `cardCornerRadius="30dp"` — esquinas muy redondeadas
> - `cardElevation="20dp"` — sombra pronunciada para efecto de profundidad
> - `contentPadding="30dp"` — espacio interno entre el borde de la tarjeta y su contenido
>
> El `ConstraintLayout` interno tiene `android:background="@drawable/fondo"`, que aplica un drawable personalizado como fondo visual dentro de la tarjeta. La imagen del inmueble usa `200px` de ancho (en píxeles físicos), lo que puede verse diferente en distintas densidades de pantalla.

---

## 10. `Clase12MenuNavegable` — Navigation Component, DrawerLayout y Bottom Navigation

### Descripción

Implementa navegación entre múltiples pantallas (Fragments) usando el **Navigation Component** de Android Jetpack. Combina un **Navigation Drawer** (menú hamburguesa lateral) con una **Bottom Navigation Bar** operando sobre el mismo `NavController`. Incluye una funcionalidad de cálculo matemático que demuestra cómo pasar datos entre Fragments con `Bundle`.

### Procedimiento paso a paso

#### A. Estructura del proyecto

```
MainActivity
├── DrawerLayout (menú lateral)
│   └── NavigationView (ítems del drawer)
├── AppBarLayout + Toolbar
│   └── FAB
└── NavHostFragment (contenedor de fragments)
    ├── TransformFragment
    ├── ReflowFragment
    ├── SlideshowFragment
    ├── SalirFragment
    ├── CalculoFragment     ← personalizado
    └── ResultadoFragment   ← destino de navegación (no en menú)
```

#### B. Registrar destinos en `AppBarConfiguration`

```java
// Todos los fragmentos del Navigation Drawer deben registrarse aquí
// para que el ícono hamburguesa aparezca correctamente
mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_transform,
        R.id.nav_reflow,
        R.id.nav_slideshow,
        R.id.nav_salir,
        R.id.nav_calculo    // Nuevo elemento navegable
).setOpenableLayout(binding.drawerLayout).build();

// Conectar el NavController con la AppBar y con el Drawer
NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
NavigationUI.setupWithNavController(binding.navView, navController);

// Bottom Navigation usa el mismo NavController
NavigationUI.setupWithNavController(binding.appBarMain.contentMain.bottomNavView, navController);
```

#### C. Inflar la vista en un Fragment con View Binding

```java
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
    // El inflater viene como parámetro y se indica dónde se va a inflar
    // false = el fragment no se adjunta automáticamente al contenedor padre
    binding = FragmentCalculoBinding.inflate(inflater, container, false);
    return binding.getRoot();
}
```

#### D. Pasar datos entre Fragments con `Bundle`

```java
// No se usa putExtra como con Activities sino Bundle con putInt/putBoolean
Bundle bundle = new Bundle();
bundle.putInt("num1", n1);
bundle.putInt("num2", n2);
bundle.putBoolean("operacion", true); // true = suma, false = resta

// NavController maneja los puntos de ida y vuelta.
// El método navigate especifica el fragment destino y el bundle con los datos.
Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
        .navigate(R.id.action_nav_calculo_to_resultadoFragment, bundle);
```

#### E. Recuperar el Bundle en el Fragment destino

```java
@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = new ViewModelProvider(this).get(ResultadoViewModel.class);

    // El Observer en un Fragment usa getViewLifecycleOwner() (no this)
    mViewModel.getResultado().observe(getViewLifecycleOwner(), new Observer<Integer>() {
        @Override
        public void onChanged(Integer resultado) {
            binding.tvResultado.setText(resultado + "");
        }
    });

    // getArguments() obtiene el Bundle recibido desde el fragment origen
    mViewModel.recuperarDatos(getArguments());
}
```

#### F. Procesar el Bundle en el ViewModel

```java
public void recuperarDatos(Bundle bundle) {
    int n1 = bundle.getInt("num1");
    int n2 = bundle.getInt("num2");
    boolean tipoOperacion = bundle.getBoolean("operacion");

    if (tipoOperacion) {
        mostrarTexto.setValue("La operación fue una suma");
        resultado.setValue(n1 + n2);
    } else {
        mostrarTexto.setValue("La operación fue una resta");
        resultado.setValue(n1 - n2);
    }
}
```

### Layout XML

La app tiene una estructura de layouts anidados en varios archivos:

**`activity_main.xml`** — contenedor raíz con `DrawerLayout`

```xml
<FrameLayout android:id="@+id/activity_container">

    <androidx.drawerlayout.widget.DrawerLayout
        android:id="@+id/drawer_layout"
        android:fitsSystemWindows="true">

        <!-- Contenido principal: toolbar + fragments -->
        <include android:id="@+id/app_bar_main" layout="@layout/app_bar_main" />

        <!-- Menú lateral deslizable (hamburguesa) -->
        <com.google.android.material.navigation.NavigationView
            android:id="@+id/nav_view"
            android:layout_gravity="start"
            android:background="#85B7E8"
            app:headerLayout="@layout/nav_header_main"
            app:menu="@menu/navigation_drawer" />

    </androidx.drawerlayout.widget.DrawerLayout>
</FrameLayout>
```

> El `DrawerLayout` es el componente que hace que el menú lateral se "deslice" desde el borde izquierdo. `android:layout_gravity="start"` posiciona el menú a la izquierda. `app:headerLayout` agrega una cabecera visual en el drawer y `app:menu` define los ítems del menú navegable desde un archivo XML en `res/menu/`.

**`app_bar_main.xml`** — barra superior y contenedor de fragments

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout>

    <com.google.android.material.appbar.AppBarLayout>
        <androidx.appcompat.widget.Toolbar android:id="@+id/toolbar" />
    </com.google.android.material.appbar.AppBarLayout>

    <include android:id="@+id/content_main" layout="@layout/content_main" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

> `CoordinatorLayout` es necesario para que la `Toolbar` y el contenido se coordinen visualmente (por ejemplo, que el contenido no quede tapado). El `include` de `content_main` mantiene los archivos modulares y más fáciles de mantener.

**`content_main.xml`** — área de fragments y barra inferior

```xml
<LinearLayout android:orientation="vertical"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <!-- Contenedor donde el NavController intercambia los fragments -->
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment_content_main"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_weight="1"
        app:defaultNavHost="true"
        app:navGraph="@navigation/mobile_navigation" />

    <!-- Barra de navegación inferior -->
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_nav_view"
        android:layout_height="56dp"
        app:menu="@menu/bottom_navigation" />

</LinearLayout>
```

> `FragmentContainerView` con `android:layout_weight="1"` toma todo el espacio disponible excepto los 56dp que ocupa el `BottomNavigationView`. `app:navGraph="@navigation/mobile_navigation"` referencia el grafo de navegación que define todos los fragments y sus acciones. `app:defaultNavHost="true"` hace que este contenedor intercepte el botón Atrás del sistema.

**`fragment_calculo.xml`** — pantalla de ingreso de números

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <EditText android:id="@+id/etNumero1"
        android:layout_width="215dp"
        android:inputType="text"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText android:id="@+id/etNumero2"
        android:layout_width="216dp"
        android:inputType="text"
        app:layout_constraintTop_toBottomOf="@+id/etNumero1" />

    <!-- Botón de suma, alineado a la izquierda -->
    <Button android:id="@+id/btCalcularSuma"
        app:layout_constraintHorizontal_bias="0.1"
        app:layout_constraintTop_toBottomOf="@+id/etNumero2" />

    <!-- Botón de resta, alineado a la derecha con marginEnd -->
    <Button android:id="@+id/btResta"
        android:layout_marginEnd="68dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/etNumero2" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Los dos botones están en la misma fila pero posicionados en extremos opuestos: `btCalcularSuma` usa `horizontal_bias="0.1"` (cerca del borde izquierdo) y `btResta` usa `constraintEnd_toEndOf` con `marginEnd` (hacia el borde derecho).

**`fragment_resultado.xml`** — pantalla de resultado

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView android:id="@+id/tvTipo"
        android:textSize="34sp"
        android:layout_marginTop="116dp"
        app:layout_constraintTop_toTopOf="@+id/tvResultado" />

    <TextView android:id="@+id/tvResultado"
        android:textSize="34sp"
        android:layout_marginTop="240dp"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> `tvTipo` muestra el tipo de operación ("La operación fue una suma") y `tvResultado` muestra el número. Ambos tienen `textSize="34sp"` para ser fácilmente legibles. `tvTipo` está anclado a `tvResultado` con un margen negativo (`116dp` sobre los `240dp` de `tvResultado`), lo que lo posiciona visualmente encima del resultado.

---

## 11. `Clase15Geolocalizacion` — Geolocalización y GPS

### Descripción

Trabaja la obtención de coordenadas GPS usando dos enfoques: la librería de Google Play Services (`FusedLocationProviderClient`) y la API nativa de Android (`LocationManager`). Demuestra cómo gestionar permisos de ubicación en tiempo de ejecución y cómo desactivar las lecturas cuando la app se cierra.

### Procedimiento paso a paso

#### A. Declarar permisos en `AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>
```

#### B. Agregar la dependencia en `build.gradle.kts`

```kotlin
// Instalamos la librería necesaria en Module settings
implementation("com.google.android.gms:play-services-location:21.x.x")
```

#### C. Solicitar permisos en tiempo de ejecución

```java
// Tras declarar los permisos en el manifest, los solicitamos en la Activity al inicio
public void solicitarPermisosLocalizacion() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
            || (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)) {
        requestPermissions(new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        }, 1000);
    }
}
```

#### D. Configurar el cliente de localización en el ViewModel

```java
// CON LIBRERÍAS EXTERNAS (GOOGLE)
// Objeto que contiene el tipo de localización
private FusedLocationProviderClient client;
// Hacemos del callback un atributo para poder activarlo y desactivarlo
private LocationCallback callback;
private MutableLiveData<Location> mLocation = new MutableLiveData<>();

public MainActivityViewModel(@NonNull Application application) {
    super(application);
    // El contexto es el de la Application
    client = LocationServices.getFusedLocationProviderClient(application);
    // Librería nativa de Android (alternativa)
    manager = (LocationManager) application.getSystemService(application.LOCATION_SERVICE);
}
```

#### E. Escuchar cambios de ubicación de forma continua (con Google Play Services)

```java
public void verCambios() {
    // Configuración: precisión alta, actualización cada 5 segundos
    LocationRequest locationRequest = new LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000
    ).build();

    // LO QUE DEBE HACER CON LOS DATOS
    callback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if (locationResult != null) {
                Location location = locationResult.getLastLocation();
                // postValue porque este callback no ocurre en el hilo principal
                mLocation.postValue(location);
            }
        }

        @Override
        public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    };
    // Registrar el callback en el cliente
    client.requestLocationUpdates(locationRequest, callback, null);
}
```

#### F. Alternativa nativa con `LocationManager`

```java
// Herramienta local (sin Google Play Services)
public void leerCambiosHerramientaLocal() {
    manager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER, // proveedor GPS
        5000,                          // intervalo mínimo en ms
        5,                             // distancia mínima en metros
        new MiListenerPosition()       // clase interna con el listener
    );
}

// Clase interna para manejar los eventos de posición
private class MiListenerPosition implements android.location.LocationListener {
    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLocation.postValue(location); // Actualizamos el mutable
    }
}
```

#### G. Desactivar lecturas en `onDestroy`

```java
// En la Activity, desactivamos cuando la app se descarta
@Override
protected void onDestroy() {
    super.onDestroy();
    mv.desactivarLecturas();
}

// En el ViewModel
public void desactivarLecturas() {
    client.removeLocationUpdates(callback); // Usamos el callback atributo
}
```

### Layout XML

**`activity_main.xml`** — un solo `TextView` centrado

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView
        android:id="@+id/tvMostrarLoc"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> El layout es mínimo: un único `TextView` centrado en pantalla que muestra las coordenadas GPS en formato `"latitudX.X longX.X"`. No hay botones porque la lectura comienza automáticamente al iniciar la Activity. Toda la interacción con el hardware ocurre en el ViewModel.

---

## 12. `Clase16GoogleMaps` — Integración de Google Maps

### Descripción

Integra el SDK de Google Maps para mostrar un mapa con marcadores y cámara animada. La Activity extiende de `FragmentActivity` (en lugar de `AppCompatActivity`) para soportar el `SupportMapFragment`.

### Procedimiento paso a paso

#### A. Registrar la API Key en `AndroidManifest.xml`

```xml
<meta-data
    android:name="com.google.android.gms.version"
    android:value="@integer/google_play_services_version" />
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="TU_API_KEY_AQUI" />
```

#### B. Cambiar la base de la Activity

```java
// Hay que hacer que extienda de FragmentActivity para usar el mapa
// (no AppCompatActivity como en el resto de proyectos)
public class MainActivity extends FragmentActivity {
```

#### C. Crear la clase interna que implementa `OnMapReadyCallback`

```java
// Creamos la clase interna que implementa el método de la librería
public class MapaActual implements OnMapReadyCallback {

    // Usamos el objeto LatLng para crear las coordenadas (latitud, longitud)
    LatLng SanLuis = new LatLng(-33.280576, -66.332482);
    LatLng ULP     = new LatLng(-33.150720, -66.306864);

    // Sobreescribimos el método onMapReady de la interfaz OnMapReadyCallback
    // para crear los marcadores en el mapa
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); // Tipo satelite

        // Creamos los marcadores con addMarker
        googleMap.addMarker(new MarkerOptions().position(SanLuis).title("San Luis"));
        googleMap.addMarker(new MarkerOptions().position(ULP).title("ULP"));

        // CameraPosition le da movimiento/animación al mapa
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(SanLuis) // Centro del mapa
                .zoom(15)        // Nivel de zoom
                .bearing(0)      // Orientación de la cámara (norte)
                .tilt(30)        // Inclinación en grados
                .build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate); // Animación fluida
    }
}
```

#### D. Conectar el mapa con el ViewModel y el Fragment

```java
// En el ViewModel: publicamos la instancia de MapaActual como LiveData
public void cargarMapa() {
    MapaActual nuevoMapaActual = new MapaActual();
    mapaActualMutable.setValue(nuevoMapaActual);
}

// En la Activity: observamos el LiveData y conectamos con el SupportMapFragment
viewModel.getMapaActual().observe(this, new Observer<MainActivityViewModel.MapaActual>() {
    @Override
    public void onChanged(MainActivityViewModel.MapaActual mapaActual) {
        ((SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map))
            .getMapAsync(mapaActual); // getMapAsync recibe un OnMapReadyCallback
    }
});
```

### Layout XML

**`activity_main.xml`** — el mapa ocupa toda la pantalla

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <fragment
        android:id="@+id/map"
        android:name="com.google.android.gms.maps.SupportMapFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Se usa la etiqueta `<fragment>` (no `<FragmentContainerView>`) con `android:name` apuntando directamente a `SupportMapFragment`. Esto es el modo de integración estático del mapa: el fragment se crea al inflar el layout. El `id="@+id/map"` es el que usa `getSupportFragmentManager().findFragmentById(R.id.map)` en la Activity para obtener la referencia al mapa.

---

## 13. `Clase17Sensores` — Sensores de Hardware

### Descripción

Accede a los sensores de hardware del dispositivo mediante `SensorManager`. Implementa dos funcionalidades: listar todos los sensores disponibles y leer datos en tiempo real del acelerómetro (ejes X, Y, Z).

### Procedimiento paso a paso

#### A. Listar todos los sensores disponibles

```java
public void leerSensor() {
    // Accedemos a SensorManager para obtener la lista de sensores
    SensorManager sm = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);

    List<Sensor> sensores = sm.getSensorList(Sensor.TYPE_ALL);

    // Para evitar sobrecargar la RAM con pull de strings, utilizamos StringBuilder.
    // Es más eficiente que concatenar String directamente.
    StringBuilder sb = new StringBuilder();

    // Recorremos la lista de sensores para saber cuál es el que necesitamos
    for (Sensor sensor : sensores) {
        sb.append(sensor.getName());
        sb.append("\n");
    }
    // Notificamos a la vista que los datos cambiaron y parseamos el builder a String
    mutableLectura.setValue(sb.toString());
}
```

#### B. Leer un sensor específico (acelerómetro)

```java
public void leerUnSensor() {
    SensorManager sm = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);

    // Accedemos al acelerómetro específicamente
    List<Sensor> sensores = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);

    // El dispositivo puede NO tener el sensor, por lo que preguntamos
    if (sensores.size() != 0) {
        Sensor acelerometro = sensores.get(0); // Solo hay uno en la lista

        // El SensorManager registra el Listener del sensor.
        // Parámetros: clase que maneja eventos, sensor a escuchar,
        // frecuencia de lectura (NORMAL, GAME, UI, FASTEST)
        sm.registerListener(new ManejaEventos(), acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
```

#### C. Crear la clase interna que maneja los eventos del sensor

```java
// Clase interna que maneja eventos e implementa SensorEventListener
private class ManejaEventos implements SensorEventListener {

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Se dispara cuando cambia la precisión del sensor
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Este método nos interesa
        // Guardamos los valores de lectura del sensor en variables
        // 0 = eje X, 1 = eje Y, 2 = eje Z
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        String dato = "X: " + x + " Y: " + y + " Z: " + z;

        // Seteamos el valor al mutable para que lo muestre la vista
        mutableLectura.setValue(dato);
    }
}
```

> **Nota importante:** `SensorManager.registerListener()` debe emparejarse con `SensorManager.unregisterListener()` en `onPause()` para evitar consumo innecesario de batería cuando la app no está en primer plano.

### Layout XML

**`activity_main.xml`** — un único `TextView` centrado

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView
        android:id="@+id/mostrar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> El layout es deliberadamente simple: un `TextView` centrado que recibe el texto desde el Observer del ViewModel. Puede mostrar tanto la lista completa de nombres de sensores (separados por `\n`) como los valores en tiempo real del acelerómetro (`"X: 0.1 Y: 9.8 Z: 0.0"`), dependiendo de qué método se llame en el ViewModel.

---

## 14. `Clase18SharedPreference` — Persistencia con SharedPreferences

### Descripción

Introduce `SharedPreferences` como mecanismo de persistencia liviano para guardar pares clave-valor (datos primitivos) en un archivo XML privado de la aplicación. El proyecto tiene dos Activities: una para guardar datos y otra para mostrarlos, sin pasar información por Intent.

### Procedimiento paso a paso

#### A. Guardar datos con `SharedPreferences.Editor`

```java
public void guardarDatos(String nombre, String edad) {
    // Creamos un objeto SharedPreferences.
    // Parámetro 1: nombre del archivo XML donde se guardarán los datos.
    //   Si no está creado se crea en este momento; si ya existe, lo busca.
    // Parámetro 2: MODE_PRIVATE → solo puede ser accedido por esta app.
    SharedPreferences sp = getApplication().getSharedPreferences("datos.xml", MODE_PRIVATE);

    // Creamos un editor para editar el archivo XML usando el método edit()
    SharedPreferences.Editor editor = sp.edit();

    // Validamos que los campos no estén vacíos
    if (nombre.isEmpty() || edad.isEmpty()) {
        Toast.makeText(getApplication(), "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
        return;
    }

    int iEdad = Integer.parseInt(edad);

    // Con el objeto editor guardamos los datos (clave, valor)
    editor.putInt("edad", iEdad);
    editor.putString("nombre", nombre);

    // Los datos NO se guardan todavía. Hay que aplicar commit().
    // commit() devuelve un boolean que indica el éxito de la operación.
    boolean guardado = editor.commit();

    if (guardado) {
        Toast.makeText(getApplication(), "Datos guardados", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(getApplication(), "Datos no guardados", Toast.LENGTH_SHORT).show();
    }
}
```

#### B. Leer datos con `SharedPreferences`

```java
public void mostrarDatos() {
    SharedPreferences sp = context.getSharedPreferences("datos.xml", MODE_PRIVATE);

    // En este caso no necesitamos el editor para leer
    // Hay que asignar valores por defecto en caso de no recuperar nada
    int    edad   = sp.getInt("edad", -1);       // -1 si no existe
    String nombre = sp.getString("nombre", null); // null si no existe

    if (edad != -1 && nombre != null) {
        this.nombre.setValue(nombre);
        this.edad.setValue(edad + "");
    } else {
        Toast.makeText(context, "No hay datos guardados", Toast.LENGTH_SHORT).show();
    }
}
```

#### C. Navegar a MostrarActivity sin pasar datos por Intent

```java
// En MainActivity: no pasamos nada por el intent ya que la información
// a mostrar la encontrará en datos.xml directamente
binding.btMostrar.setOnClickListener(v -> {
    Intent intent = new Intent(MainActivity.this, MostrarActivity.class);
    startActivity(intent);
});
```

> **`commit()` vs `apply()`:** `commit()` escribe de forma síncrona y devuelve un `boolean`. `apply()` escribe de forma asíncrona y no devuelve resultado. Para confirmar el éxito de la escritura se recomienda `commit()`.

### Layout XML

**`activity_main.xml`** — pantalla para guardar datos

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <EditText android:id="@+id/etNombre"
        android:layout_width="213dp"
        android:hint="Nombre"
        android:inputType="text"
        app:layout_constraintBottom_toTopOf="@+id/etEdad" />

    <EditText android:id="@+id/etEdad"
        android:layout_width="215dp"
        android:hint="Edad"
        android:inputType="number"
        android:layout_marginTop="196dp"
        app:layout_constraintTop_toTopOf="parent" />

    <Button android:id="@+id/btGuardar"
        android:text="Guardar"
        app:layout_constraintTop_toBottomOf="@+id/etEdad" />

    <Button android:id="@+id/btMostrar"
        android:text="Mostrar"
        app:layout_constraintTop_toBottomOf="@+id/btGuardar" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> `etEdad` usa `inputType="number"` para que el teclado muestre solo números. `etNombre` usa `inputType="text"`. Ambos usan `android:hint` en lugar de `android:text` para mostrar texto de ayuda sin ser el valor real del campo. Los dos botones están apilados verticalmente.

**`activity_mostrar.xml`** — pantalla para mostrar los datos guardados

```xml
<androidx.constraintlayout.widget.ConstraintLayout>

    <TextView android:id="@+id/tvNombre"
        android:textSize="34sp"
        android:layout_marginTop="68dp"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView android:id="@+id/tvEdad"
        android:textSize="34sp"
        android:layout_marginTop="48dp"
        app:layout_constraintTop_toBottomOf="@+id/tvNombre" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Dos `TextView` con `textSize="34sp"` para mostrar los datos recuperados de `SharedPreferences`. No hay `EditText` ni botones porque esta pantalla es solo de lectura. Los datos no vienen por `Intent` sino que el ViewModel los lee directamente de `datos.xml`.

---

## 15. `ClaseBroadcastUSB` / `ClaseBroadCastUSB19` — Proyectos sin Código

Estas carpetas contienen únicamente la configuración del IDE (directorio `.idea`) y no tienen código fuente desarrollado. Posiblemente son variantes del proyecto `Clase5BroadcastReceiver` creadas para explorar el manejo de USB en versiones específicas de Android (API 19+) pero que no llegaron a completarse.

---

## Progresión de Aprendizaje

```
ClaseDos          → Estructura base
Clase4            → Intents + Permisos + ContentResolver
Clase5            → BroadcastReceiver (sistema de eventos)
Clase6            → Services (tareas en segundo plano)
Clase7_Views      → View Binding + ViewModel + LiveData (MVVM básico)
Clase8PracticaVM  → ViewModel con modelo POJO + navegación desde VM
Clase8VMYDoble    → Refuerzo doble Activity con ViewModels independientes
Clase9ListView    → ListView + ArrayAdapter + Serializable
Clase10RecyclerView → RecyclerView + ViewHolder + CardView + postValue
Clase12Menu       → Navigation Component + Fragments + Bundle
Clase15Geo        → Hardware GPS + permisos + LiveData desde callbacks
Clase16Maps       → Google Maps SDK + marcadores + cámara
Clase17Sensores   → SensorManager + acelerómetro + eventos de hardware
Clase18SharedPref → Persistencia local con SharedPreferences
```
