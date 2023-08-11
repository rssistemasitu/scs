package com.rogerio.servicemain.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.rogerio.servicemain.utils.Constants;
import com.rogerio.servicemain.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    private static Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private SocketIOServer socketServer;

    @KafkaListener(
            topics = Constants.KAFKA_TOPIC,
            groupId = Constants.GROUP_ID
    )
    public void listen(Message message) {
        socketServer.getBroadcastOperations().sendEvent("messageSendToAllUsers", message);
        log.info("consumer-service - [flow: listen-messages-to-topic]", message);
    }
}
