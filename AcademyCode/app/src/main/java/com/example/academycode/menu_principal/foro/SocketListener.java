package com.example.academycode.menu_principal.foro;

import android.widget.Toast;

import com.example.academycode.model.Mensaje;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SocketListener extends WebSocketListener {

    private ForoGeneral foroGeneral;

    public SocketListener(ForoGeneral foroGeneral) {
        this.foroGeneral = foroGeneral;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);

        foroGeneral.runOnUiThread(() -> Toast.makeText(foroGeneral, "Conectado al chat!", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);

        foroGeneral.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                JSONObject json = null;

                try {
                    json = new JSONObject(text);
                    Mensaje mensajeObject = new Mensaje(SocketListener.this.ipPublica(), json.getString("nombre_usuario"), json.getString("email"), json.getString("mensaje"), foroGeneral.fecha_horaActual());
                    foroGeneral.getAdapter().addItem(mensajeObject);
                    ForoGeneral.mostrarNotificacion(foroGeneral.getApplicationContext());
                    foroGeneral.getRecyclerView().getLayoutManager().scrollToPosition(foroGeneral.getMensajeList().size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }


    private String ipPublica() {

        String publica = "";

        try {

            URL mip = new URL("http://checkip.amazonaws.com");

            BufferedReader in = new BufferedReader(new InputStreamReader(mip.openStream()));

            publica = in.readLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return publica;
    }

}
