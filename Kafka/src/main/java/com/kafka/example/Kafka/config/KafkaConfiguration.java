package com.kafka.example.Kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

//import com.kafka.example.Kafka.model.ConsumerUser;
import com.kafka.example.Kafka.model.User;

@EnableKafka //this is useful only to the consumer part, so kafka will know it has to scan the code for listeners (consumers)
@Configuration
public class KafkaConfiguration {
	
	@Bean
	public ProducerFactory<String, User> producerFactory(){
		/*
		 * in order to publish more complex messages than Strings to kafka (like objects), 
		 * we need to tell kafka who is the producer and how can we serialize the message
		 */
		Map<String, Object> config = new HashMap<>();
		//producer's server name
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		//key serialization
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		//value serialization
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<String, User>(config);
	}
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		/*
		 * in order to consume messages from kafka, 
		 * we need to tell kafka who is the consumer and how can we deserialize the message
		 * here, we deserialize the message as String
		 */
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, String>(config);
	}
	
	@Bean
	public ConsumerFactory<String, User> consumerUserFactory(){
		/*
		 * in order to consume messages from kafka, 
		 * we need to tell kafka who is the consumer and how can we deserialize the message
		 * here, we deserialize the message as json
		 */
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, 
				User>(config, new StringDeserializer(), new JsonDeserializer<>(User.class));
	}
	
	//this bean will produce messages of group "group_id" and will serialize them as json
	@Bean
	public KafkaTemplate<String, User> kafkaTemplate(){
		return new KafkaTemplate<String, User>(producerFactory());
	}
	
	//this bean will listen to messages of group "group_id" and will deserialize them as String
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	//this bean will listen to messages of group "group_json" and will deserialize them as json
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<String, User>();
		factory.setConsumerFactory(consumerUserFactory());
		return factory;
	}
}
