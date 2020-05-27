package com.example.academycode.menu_principal.foro;

import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.academycode.model.Mensaje;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

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

        foroGeneral.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(foroGeneral, "Conectado al chat!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);

        foroGeneral.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Mensaje jsonObject = new Mensaje("", "USUARIO", "EMAIL", text, "27/05/2020");

                foroGeneral.getAdapter().addItem(jsonObject);

                foroGeneral.getRecyclerView().getLayoutManager().scrollToPosition(foroGeneral.getMensajeList().size()-1);

               /* JSONObject jsonObject = new JSONObject();

                try{

                    jsonObject.put("message", text);
                    jsonObject.put("usuario", "USUARIO");
                    jsonObject.put("email", "EMAIL");
                    jsonObject.put("fecha", "26/05/2020");
                    jsonObject.put("byServer", true);

                    foroGeneral.adapter2.addItem(jsonObject);

                }catch (JSONException e) {
                    e.printStackTrace();
                }*/

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
}
