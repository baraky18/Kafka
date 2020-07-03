package com.kafka.example.Kafka.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.example.Kafka.model.User;

@RestController
@RequestMapping("kafka")
public class KafkaPublisher {
	
	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;
	
	private final String STRING_TOPIC = "KafkaExample";
	
	@PostMapping("/publishstring/{name}")
	public String postString(@PathVariable("name") final String name){
		kafkaTemplate.send(STRING_TOPIC, new User(name, "Technology", "120000"));
		return "Published successfully";
	}
	
	@PostMapping("/publishjson/{name}")
	public String postJson(@PathVariable("name") final String name){
		kafkaTemplate.send(STRING_TOPIC, new User(name, "Operations", "130000"));
		return "Published successfully";
	}
}
