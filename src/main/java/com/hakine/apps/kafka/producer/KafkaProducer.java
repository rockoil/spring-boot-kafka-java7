package com.hakine.apps.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String payload) {
        logger.info("send payload='{}'", payload);
        kafkaTemplate.send("test-topic", payload);
    }

    public void sendMessage(final String message) {

        // ListenableFuture는 spring 4.0 에서 추가된 인터페이스이다. 4.0 이전에서는 사용할 수 없으니 참고하면 되겠다.
        // 실제로 AsyncRestTemplate의 리턴차입은 ListenableFuture로 정의 되어 있다. 비동기 적으로 특정한 API를 호출할때
        // 유용한 AsyncRestTemplate은 리턴타입 그대로 spring mvc에게 넘겨줘도 된다.
        // API 통신후에 적절히 사용한다면 매우 유용한 인터페이스이다.
        // http://wonwoo.ml/index.php/post/1912
        // 카프카는 매우 빠르게 메세지를 주고 받는다. 메세지 성공 여부를 확인 및 처리를 위해서
        // Thread를 block 해서 결과를 처리한다.
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test-topic", "Hi!!!");

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            // 실패 했을 경우
            @Override
            public void onFailure(Throwable throwable) {
                logger.info("Unable to send message=['{}'] due to : '{}'", message, throwable.getMessage());
            }

            // 성공했을 경우
            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                logger.info("Sent message = ['{}'] with offset = ['{}']", message, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }
}
