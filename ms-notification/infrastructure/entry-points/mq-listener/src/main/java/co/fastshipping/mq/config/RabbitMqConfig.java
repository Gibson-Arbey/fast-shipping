package co.fastshipping.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public TopicExchange notificationEventsExchange() {
        return new TopicExchange("notification-events");
    }

    @Bean
    public Queue emailNotificationQueue() {
        return new Queue("email-notification-queue", true);
    }

    @Bean
    public Queue smsNotificationQueue() {
        return new Queue("sms-notification-queue", true);
    }

    @Bean
    public Binding emailNotificationBinding(Queue emailNotificationQueue, TopicExchange notificationEventsExchange) {
        return BindingBuilder
                .bind(emailNotificationQueue)
                .to(notificationEventsExchange)
                .with("notification.email");
    }

    @Bean
    public Binding smsNotificationBinding(Queue smsNotificationQueue, TopicExchange notificationEventsExchange) {
        return BindingBuilder
                .bind(smsNotificationQueue)
                .to(notificationEventsExchange)
                .with("notification.sms");
    }
}
