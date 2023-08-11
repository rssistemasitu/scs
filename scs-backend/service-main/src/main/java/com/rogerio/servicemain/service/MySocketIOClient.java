package com.rogerio.servicemain.service;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class MySocketIOClient {
    private Socket socket;

    public MySocketIOClient() {
        try {
            // Conectar ao servidor Socket.IO
            socket = IO.socket("http://localhost:8878");

            socket.connect();
            System.out.println("Conectado ao servidor Socket.IO! Socket ID: " + socket.id());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        MySocketIOClient client = new MySocketIOClient();
        if (client.socket.connected()) {
            // Envia a mensagem com o evento "sendMessageToServer"
            client.socket.emit("messageSendToAllUsers", message);
        } else {
            System.out.println("Não foi possível enviar a mensagem. Socket não conectado.");
        }
    }

    public void disconnect() {
        if (socket != null) {
            socket.disconnect();
        }
    }
}
