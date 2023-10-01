package com.rogerio.servicemain.controller;

import com.rogerio.servicemain.entity.Message;
import com.rogerio.servicemain.service.SocketIOServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/main/messages")
public class MessageController {

    @Autowired
    private SocketIOServerService socketIOServerService;

    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    @PostMapping
    public void sendMessage(@RequestBody Message message) throws URISyntaxException {
        socketIOServerService.send(message);
        log.info("message-controller - [flow: send-message]");
    }
}
