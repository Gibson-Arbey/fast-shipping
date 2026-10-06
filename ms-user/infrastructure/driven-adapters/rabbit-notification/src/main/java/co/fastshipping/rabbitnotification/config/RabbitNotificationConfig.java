package co.fastshipping.rabbitnotification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitNotificationConfig {

    @Bean
    public TopicExchange notificationEventsExchange() {
        return new TopicExchange("notification-events", true, false);
    }

    @Bean
    public Queue emailNotificationQueue() {
        return new Queue("email-notification-queue", true);
    }

    @Bean
    public Binding emailNotificationBinding(Queue emailNotificationQueue, TopicExchange notificationEventsExchange) {
        return BindingBuilder.bind(emailNotificationQueue)
                .to(notificationEventsExchange)
                .with("notification.email");
    }

    @Bean
    public MessageConverter notificationMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
