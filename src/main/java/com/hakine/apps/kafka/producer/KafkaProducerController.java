package com.hakine.apps.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KafkaProducerController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping("/kafka")
    @ResponseBody
    public String request(@RequestParam(value="msg", required=true, defaultValue="")String message) {

        kafkaTemplate.send("test-topic", message);

        return "kafkaTemplate.send : >> " + message;
    }
}
