package com.kafka.example.Kafka.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//import com.kafka.example.Kafka.model.ConsumerUser;
import com.kafka.example.Kafka.model.User;

@Service
public class KafkaConsumer {

	@KafkaListener(topics="KafkaExample", groupId="group_id")
	public void consume(String message){
		System.out.println("Consumed message: " + message);
	}
	
	@KafkaListener(topics="KafkaExample", groupId="group_json", containerFactory="userKafkaListenerContainerFactory")
	public void consumeJson(User user){
		System.out.println("Consumed JSON message: " + user);
	}
}
