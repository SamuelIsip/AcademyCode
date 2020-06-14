package com.example.academycode.menu_principal;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.academycode.R;
import com.example.academycode.almacenamiento.SQLiteBaseDeDatos;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.fragments.SettingsFragment;
import com.example.academycode.login.IniciarSesion;
import com.example.academycode.login.RegistrarUsuario;
import com.example.academycode.menu_principal.amigos.AmigosUsers;
import com.example.academycode.menu_principal.ejercicios.EjerciciosPDF;
import com.example.academycode.menu_principal.foro.ForoGeneral;
import com.example.academycode.menu_principal.teoria.TeoriaPDF;
import com.example.academycode.menu_principal.tutoriales.TutorialesVideo;
import com.example.academycode.model.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;


public class MenuPrincipal extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView nomb, email;
    private CircularImageView fotoPerfilUser;

    private SQLiteBaseDeDatos db;
    private ImageButton btnAbrirMenu;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SQLiteBaseDeDatos(this);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.menu_desplegable);
        navigationView.setNavigationItemSelectedListener(this);

        nomb = navigationView.getHeaderView(0).findViewById(R.id.nombre_user_menu);
        email = navigationView.getHeaderView(0).findViewById(R.id.email_user_menu);
        fotoPerfilUser = navigationView.getHeaderView(0).findViewById(R.id.imagen_usuario_menu);

        btnAbrirMenu = findViewById(R.id.btnAbrirMenu);
        btnAbrirMenu.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        //Solicitamos cuenta con email
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        fotoPerfilUser.setOnClickListener(v -> mostrarOpciones());


    }

    private void mostrarOpciones() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, (dialog, which) -> {
            if (option[which] == "Tomar foto") {
                tomarFoto();
            } else if (option[which] == "Elegir de galeria") {
                seleccionarImagen();
            } else {
                dialog.dismiss();
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

    public void tomarFoto() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if (!isCreada) {
            isCreada = fileImagen.mkdirs();
        }

        if (isCreada) {
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory() + File.separator + RUTA_IMAGEN + File.separator + nombreImagen;
        File imagen = new File(path);

        Intent intent;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = this.getPackageName() + ".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }

        startActivityForResult(intent, 100);
    }

    public void seleccionarImagen() {
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 200) {//Para seleccionar foto
            if (data != null) {
                photoURI = data.getData();
            }
            fotoPerfilUser.setImageURI(photoURI);
            //Guardar uri en BD
            try {
                rutaFoto = getRealPathFromURI(photoURI);
                db.insertarFotoUser(rutaFoto, nomb.getText().toString());
            } catch (Exception e) {
                System.out.println("ERROR AL GUARDAR FOTO SELECCIONADA EN BD");
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == 100) {//Para hacer foto
            try {
                MediaScannerConnection.scanFile(MenuPrincipal.this, new String[]{path}, null, (s, uri) -> {
                });

                Glide.with(this)
                        .load(path)
                        .into(fotoPerfilUser);
                //Guardar path en BD
                db.insertarFotoUser(path, nomb.getText().toString());
            } catch (Exception e) {
                System.out.println("ERROR EN CARGAR LA FOTO");
                e.printStackTrace();
            }

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.cerrar_sesion:
                cerrarSesionGoogle();
                break;
            case R.id.ajustes:
                Fragment fragment = new SettingsFragment();
                if (fragment != null) {
                    displayFragment(fragment);
                }
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
                abrirForo(null);
                break;
            case R.id.amigo:
                abrirAmigos(null);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer_layout, fragment)
                .commit();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            cerrarSesionGoogle();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    GoogleSignInAccount account;

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            account = result.getSignInAccount();
            Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(fotoPerfilUser);
        }

    }

    private void cerrarSesionGoogle() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir");
        builder.setMessage("¿Seguro que quieres salir de la aplicación?");

        builder.setPositiveButton("SI", (dialog, which) -> {
            SharedPrefManager.getInstance(getApplicationContext()).clear();

            Intent intent = new Intent(getApplicationContext(), IniciarSesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    status -> {
                        if (status.isSuccess()) { //Si se cierra sesion
                            Intent intent1 = new Intent(getApplicationContext(), IniciarSesion.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(getApplicationContext(), "Session not close", Toast.LENGTH_LONG).show();
                        }
                    });
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();


    }


    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(googleSignInResult -> handleSignInResult(googleSignInResult));
        }

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, RegistrarUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        Usuario user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        nomb.setText(user.getNombre_usuario());
        email.setText(user.getEmail());

        String fotoUser = db.recuperarFotoUser(user.getNombre_usuario());
        mostrarImagen_guardada_o_no(fotoUser);
        db.close();

    }

    private void mostrarImagen_guardada_o_no(String fotoUser) {
        File archivo = null;
        try {
            archivo = new File(fotoUser);
        }catch(Exception e){
            e.printStackTrace();
        }

        if (fotoUser != null && archivo.exists()) {
            //Mostrar foto perfil accedido
            Glide.with(this)
                    .load(fotoUser) //conseguir fotoUsuario de BD
                    .into(fotoPerfilUser);
        } else {
            /*Glide.with(this)
                    .load(R.mipmap.ic_launcher_round)
                    .into(fotoPerfilUser);*/
        }
    }

    public void abrirTeoria(View view) {
        startActivity(new Intent(this, TeoriaPDF.class));
    }

    public void abrirEjercicios(View view) {
        startActivity(new Intent(this, EjerciciosPDF.class));
    }

    public void abrirVideoTuto(View view) {
        startActivity(new Intent(this, TutorialesVideo.class));
    }

    public void abrirForo(View view) {
        startActivity(new Intent(this, ForoGeneral.class));
    }

    public void abrirAmigos(View view) {
        startActivity(new Intent(this, AmigosUsers.class));
    }

}
