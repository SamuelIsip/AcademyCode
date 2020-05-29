package com.example.academycode.menu_principal.foro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.academycode.R;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.login.RegistrarUsuario;
import com.example.academycode.menu_principal.MenuPrincipal;

import com.example.academycode.model.Mensaje;
import com.example.academycode.model.Usuario;
import com.example.academycode.model.adapters.MessagesAdapter;
import com.example.academycode.model.response.DefaultResponse;

import com.example.academycode.api.RetrofitClient;
import com.example.academycode.model.response.MessagesResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForoGeneral extends AppCompatActivity {

    private EditText mensajeDelUsuario;

    private static List<Mensaje> mensajeList;

    private  static RecyclerView recyclerView;
    private static MessagesAdapter adapter;

    private WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general);

        //Permitir acceder por http
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        recyclerView = findViewById(R.id.recyclerViewForo);

        LinearLayoutManager lnr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lnr);

        mensajeDelUsuario = findViewById(R.id.edTxtMensajeForo);

        if (!comprobarInternet()){
            Toast.makeText(ForoGeneral.this, "Debe conectarse a Internet", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MenuPrincipal.class));
            finish();
        }else{

            cargarMensjaes(this);

            initiateWebSocket();

            adapter = new MessagesAdapter(getApplicationContext());
            recyclerView.setAdapter(adapter);

            try {
                MyTask.getInstance(this).execute();
            }catch (RuntimeException e){
                e.printStackTrace();
            }

        }


    }


    public void enviarMensaje(View view) {

        closeTecladoMovil();

        String mensaje = mensajeDelUsuario.getText().toString().trim();

        Usuario user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        String nombre_usuario = user.getNombre_usuario();
        String email = user.getEmail();

        if (!mensaje.trim().equals("")){

            JSONObject jsonObject = new JSONObject();

            try{
            jsonObject.put("nombre_usuario", nombre_usuario);
            jsonObject.put("email", email);
            jsonObject.put("mensaje", mensaje);

            }catch (JSONException e) {
                e.printStackTrace();
            }

            webSocket.send(jsonObject.toString());
            mensajeDelUsuario.setText("");

            Mensaje mensajeObject = new Mensaje(ipPublica(), nombre_usuario, email, mensaje, fecha_horaActual());

            adapter.addItem(mensajeObject);

            recyclerView.getLayoutManager().scrollToPosition(mensajeList.size()-1);

            Call<DefaultResponse> call3 = RetrofitClient.getInstance()
                    .getApi().saveMessage(ipPublica(), nombre_usuario, email, mensaje);

            call3.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    if(response.code() == 422){
                        Toast.makeText(getApplicationContext(), "No se ha enviado el mensaje", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });

        }

    }

    public String fecha_horaActual(){
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
       return hourdateFormat.format(date);

    }


    private boolean comprobarInternet(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }

        return false;
    }


    private void initiateWebSocket(){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("ws://academycode.ddns.net:8080").build();

        SocketListener socketListener = new SocketListener(this);

        webSocket = client.newWebSocket(request, socketListener);

    }


    public static void cargarMensjaes(Context context){

        Call<MessagesResponse> call2 = RetrofitClient.getInstance()
                .getApi().getAllMessages();

        call2.enqueue(new Callback<MessagesResponse>() {
            @Override
            public void onResponse(Call<MessagesResponse> call, Response<MessagesResponse> response) {

                int tamanioMensajes = 0;

                if (mensajeList!=null) {
                    tamanioMensajes = mensajeList.size();
                }
                mensajeList = response.body().getMessages();

                if (tamanioMensajes!=0 && tamanioMensajes<mensajeList.size()){
                    mostrarNotificacion(context);
                }

                adapter = new MessagesAdapter(context,mensajeList);

                recyclerView.setAdapter(adapter);
                recyclerView.getLayoutManager().scrollToPosition(mensajeList.size()-1);
            }

            @Override
            public void onFailure(Call<MessagesResponse> call, Throwable t) {

            }
        });
    }

    static Notification.Builder notificacion;
    private static final int idUnica = 51623;

    public static void mostrarNotificacion(Context context){

        notificacion = new Notification.Builder(context);
        notificacion.setAutoCancel(true);

        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setTicker("Nueva notificacion");
        notificacion.setPriority(Notification.PRIORITY_HIGH);
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setContentTitle("Atención");
        notificacion.setContentText("¡Hay un nuevo mensaje en el foro!");

        Intent intent = new Intent(context,ForoGeneral.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(idUnica,notificacion.build());

    }


    private void closeTecladoMovil(){

        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, RegistrarUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    private String ipPublica(){

        String publica = "";

        try{

            URL mip = new URL("http://checkip.amazonaws.com");

            BufferedReader in = new BufferedReader(new InputStreamReader(mip.openStream()));

            publica = in.readLine();

        }catch(MalformedURLException e) {
            System.out.println(e.getMessage());
        }catch( IOException e2){
            System.out.println(e2.getMessage());
        }

        return publica;
    }


    public List<Mensaje> getMensajeList() {
        return mensajeList;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public MessagesAdapter getAdapter() {
        return adapter;
    }

    public void btnAtras(View view) {
        startActivity(new Intent(this, MenuPrincipal.class));
    }

}


class MyTask extends AsyncTask<String, String, String> {

    private boolean aux;

    private static final String MYTASK_NAME = "my_task";
    private static MyTask mInstance;
    private Context mCtx;

    private MyTask(Context mCtx){
        this.mCtx = mCtx;
    }

    public static synchronized  MyTask getInstance(Context mCtx){
        if (mInstance == null){
            mInstance = new MyTask(mCtx);
        }

        return mInstance;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        aux = true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(mCtx, "CARGADO", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        aux = false;
    }

    @Override
    protected String doInBackground(String... strings) {

        while (aux) {
            try {
                ForoGeneral.cargarMensjaes(mCtx);
                publishProgress();
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
