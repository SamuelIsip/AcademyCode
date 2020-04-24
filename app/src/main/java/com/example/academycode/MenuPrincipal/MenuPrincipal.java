package com.example.academycode.MenuPrincipal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.academycode.BaseDeDatos.SQLiteBaseDeDatos;
import com.example.academycode.Ejercicios.EjerciciosPDF;
import com.example.academycode.Login.IniciarSesion;
import com.example.academycode.R;
import com.example.academycode.Teoria.TeoriaPDF;
import com.example.academycode.Tutoriales.TutorialesVideo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.io.File;


public class MenuPrincipal extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    TextView nomb,email;
    CircularImageView fotoPerfilUser;

    SQLiteBaseDeDatos db;

    ImageButton btnAbrirMenu;

    Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db =  new SQLiteBaseDeDatos(this);

        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.menu_desplegable);
        navigationView.setNavigationItemSelectedListener(this); //Para click en items del menu desplegable

        //Acceder a los elementos de la cabecera del menu desplegable
        nomb = navigationView.getHeaderView(0).findViewById(R.id.nombre_user_menu);
        email = navigationView.getHeaderView(0).findViewById(R.id.email_user_menu);
        fotoPerfilUser = navigationView.getHeaderView(0).findViewById(R.id.imagen_usuario_menu);

        //Para que aparezca el icono de menu en el toolbar
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/
        btnAbrirMenu = findViewById(R.id.btnAbrirMenu);
        btnAbrirMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        //Solicitamos cuenta con email
        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        fotoPerfilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpciones();
            }
        });


    }

    //****************************************
    //Controlar la foto de perfil del usuario

    //MOSTRAR VENTANA CON OPCIONES
    private void mostrarOpciones() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    tomarFoto();
                }else if(option[which] == "Elegir de galeria"){
                    seleccionarImagen();
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private Uri photoURI;
    String rutaFoto = "";

    private String CARPETA_RAIZ = "AcademyCodePhotos";
    private String CARPETAS_IMAGENES = "photos";
    private String RUTA_IMAGEN = CARPETA_RAIZ + CARPETAS_IMAGENES;
    private static String path;
    private String nombreImagen = "";

    //HACER FOTO
    public void tomarFoto() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(isCreada == false) {
            isCreada = fileImagen.mkdirs();
        }

        if(isCreada == true) {
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = this.getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }

        startActivityForResult(intent, 100);
    }

    //SELECCIONAR LA IMÁGEN DE LA GALERÍA
    public void seleccionarImagen() {
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 200) {//Para seleccionar foto
            photoURI = data.getData();
            fotoPerfilUser.setImageURI(photoURI);
            //Guardar uri en BD
            try {
                rutaFoto = getRealPathFromURI(photoURI);
                db.insertarFotoUser(rutaFoto, email.getText().toString(),nomb.getText().toString());
            } catch (Exception e) {
                System.out.println("ERROR AL GUARDAR FOTO SELECCIONADA EN BD");
                e.printStackTrace();
            }
        } else if(resultCode == RESULT_OK && requestCode == 100) {//Para hacer foto
            try{
                MediaScannerConnection.scanFile(MenuPrincipal.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                    }
                });

                Glide.with(this)
                        .load(path)
                        .into(fotoPerfilUser);
                //Guardar path en BD
                db.insertarFotoUser(path, email.getText().toString(), nomb.getText().toString());
            } catch (Exception e) {
                System.out.println("ERROR EN CARGAR LA FOTO");
                e.printStackTrace();
            }
            /*Bitmap bitmap = BitmapFactory.decodeFile(path);
            img.setImageBitmap(bitmap);*/
        }
    }

    //Conseguir el path de la imagen pasándole la URI
    private String getRealPathFromURI(Uri contentURI) throws Exception{
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    //**************************************

    //Método seleccion del item del menu desplegable
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.cerrar_sesion:
                cerrarSesionGoogle();
                break;
            case R.id.ajustes:
                //accederAjustes();
                break;
            case R.id.teoria:
                abrirTeoria(null);
                break;
            case R.id.tutovid:
                abrirVideoTuto(null);
                break;
            case R.id.ejercicios:
                abrirEjercicios(null);
                break;
            case R.id.foro:

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true; //fue seleccionado
    }


    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            salirAplicacion();
        }
    }

    private void salirAplicacion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir");
        builder.setMessage("¿Seguro que quieres salir de la aplicación?");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MenuPrincipal.this, IniciarSesion.class));
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();

    }

    //************************************
    //Métodos para gestionar cuenta Google
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    GoogleSignInAccount account;
    //Recoger datos de la cuenta google
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            account=result.getSignInAccount();
            nomb.setText(account.getDisplayName());
            email.setText(account.getEmail());
            //Foto de perfil
            //Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(fotoPerfilUser);
            String fotoUser = db.recuperarFotoUser(account.getDisplayName());
            mostrarImagen_guardada_o_no(fotoUser);
        }

    }

    public void cerrarSesionGoogle(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()){ //Si se cierra sesion
                        startActivity(new Intent(MenuPrincipal.this, IniciarSesion.class));
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
    //************************************

    @Override
    protected void onStart() {
        super.onStart();

        //Mantener Cuenta de google conectada
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }

        Bundle datos = this.getIntent().getExtras();
        if (datos != null) {
            nomb.setText(datos.getString("nombreUsuario"));
            email.setText(datos.getString("emailUsuario"));

            String fotoPerfil = db.recuperarFotoUser(nomb.getText().toString());
            mostrarImagen_guardada_o_no(fotoPerfil);

        }
    }


    @Override protected void onStop() {
        Auth.GoogleSignInApi.signOut(googleApiClient);
        super.onStop();
    }

    //******************************************************
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nomb", nomb.getText().toString());
        outState.putString("email" , email.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        nomb.setText(savedInstanceState.getString("nomb"));
        email.setText(savedInstanceState.getString("email"));

        String fotoUser = db.recuperarFotoUser(savedInstanceState.getString("nomb"));
        mostrarImagen_guardada_o_no(fotoUser);

    }
    //******************************************************

    public void mostrarImagen_guardada_o_no(String fotoUser){
        if (fotoUser!=null){
            //Mostrar foto perfil accedido
            Glide.with(this)
                    .load(fotoUser) //conseguir fotoUsuario de BD
                    .into(fotoPerfilUser);
        }else{
            Glide.with(this)
                    .load(R.mipmap.ic_launcher_round)
                    .into(fotoPerfilUser);
        }
    }

    //******************************************************
    //Abrir activityes de los cardview
    public void abrirTeoria(View view) {
        startActivity(new Intent(this, TeoriaPDF.class));
    }

    public void abrirEjercicios(View view) {
        startActivity(new Intent(this, EjerciciosPDF.class));
    }

    public void abrirVideoTuto(View view) {
        startActivity(new Intent(this, TutorialesVideo.class));
    }

    //******************************************************
}
