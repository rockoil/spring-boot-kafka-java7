package com.hakine.apps.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 카프카 생산자 설정
 */
@Configuration
public class KafkaProducerConfig {

    // 카프카 서버 주소
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * 생산자 기본 설정
     * @return
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        // list of host: port pairs used for establishing the initial connection to the kafka cluster
        // 카프카 메세지는 Key-value 형태의 메타데이터를 포함하고 있다.
        // 그말은 kafka는 메세지를 byte array 형태로 전송 및 저장 하기 때문에
        // Key와 value 값을 특정 포맷으로 Serializer 해주어야 한다.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
