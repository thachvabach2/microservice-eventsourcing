package vn.bachdao.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {

    @RetryableTopic(
            attempts = "4", // 3 topics retry + 1 topic DLQ
            backoff = @Backoff(delay = 1000, multiplier = 2), // 1st delay: 1s, 2nd delay: 2s, 3rd delay: 4s
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR, // ở dlq ko retry (ok nhat)
            include = {
                    ReflectiveOperationException.class,
                    RuntimeException.class
            } // declare exceptionx can retry
    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        log.info("Received message: {}", message);
        // processing message

    }

    @DltHandler //giám sát error message
    // hàm này chị khi message được đưa vào dlq
    // có thể gửi mail cho dev để biết message nào bị đưa vào dlq để fix
    void processDltMessage(@Payload String message) {
        log.info("DLT receive message: {}", message);
    }
}
