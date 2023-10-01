package com.rogerio.servicemain.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.rogerio.servicemain.entity.Message;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Component
@Log4j2
public class SocketIOServerService {

    @Autowired
    private SocketIOServer socketServer;

    @Autowired
    private ProducerService producerService;

    SocketIOServerService(SocketIOServer socketServer) throws URISyntaxException {
        this.socketServer=socketServer;
        this.socketServer.addConnectListener(onUserConnectWithSocket);
        this.socketServer.addDisconnectListener(onUserDisconnectWithSocket);

        /**
         * Here we create only one event listener
         * but we can create any number of listener
         * messageSendToUser is socket end point after socket connection user have to send message payload on messageSendToUser event
         */
        this.socketServer.addEventListener("messageSendToAllUsers", Message.class, onSendMessageToAll);

    }

    public ConnectListener onUserConnectWithSocket = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {
            log.info("Executando a operação no usuário conectado no controlador");
        }
    };

    public DisconnectListener onUserDisconnectWithSocket = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
            log.info("Executando a operação no usuário desconectado no controlador");
        }
    };


    public DataListener<Message> onSendMessageToAll = new DataListener<Message>() {
        @Override
        public void onData(SocketIOClient client, Message message, AckRequest acknowledge) throws Exception {

            /**
             * Sending message to target user
             * target user should subscribe the socket event with his/her name.
             * Send the same payload to user
             */
            message.setFrom(client.getSessionId().toString());
            message.setTimestamp(LocalDateTime.now().toString());
            log.info(String.format("Usuário %s enviando mensagem para todos os usuários.", message.getFrom()));
            socketServer.getBroadcastOperations().sendEvent("messageSendToAllUsers", message);
            producerService.sendMessage(message);
            log.info(String.format("Mensagem enviada %s ", message));

            /**
             * After sending message to target user we can send acknowledge to sender
             */
            acknowledge.sendAckData("Mensagem enviada com sucesso.");
        }
    };

    public void send(Message message) throws URISyntaxException {
        Socket client = IO.socket("http://localhost:8878");

        client.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @SneakyThrows
            @Override
            public void call(Object... objects) {
                // Envia a mensagem com o evento "sendMessageToServer"
                JSONObject obj = new JSONObject(message);
                client.emit("messageSendToAllUsers", obj);
                client.disconnect();
            }
        });
        client.connect();
    }
}
