package vn.bachdao.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import vn.bachdao.commonservice.services.EmailService;

@Component
@Slf4j
public class EventConsumer {

    @Autowired
    private EmailService emailService;

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

    @KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
    public void testEmail(String message) {
        log.info("Received message: {}", message);
        String template = "<div>\n" +
                "    <h1>Welcome, %s!</h1>\n" +
                "    <p>Thank you for joining us. We're excited to have you on board.</p>\n" +
                "    <p>Your username is: <strong>%s</strong></p>\n" +
                "</div>";
        String filledTemplate = String.format(template,"Brother",message);

        emailService.sendEmail(message,"Thanks for buy my course",filledTemplate,true,null);
    }

}
