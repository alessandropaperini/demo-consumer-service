package it.laterale.cloud.config;

import it.laterale.cloud.consumers.BallDataConsumer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue ballQueue() {
        return new Queue("ball-queue");
    }

    @Bean
    public MessageListenerAdapter ballMessageAdapter(BallDataConsumer consumer) {
        return new MessageListenerAdapter(consumer, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer ballMessageListenerContainer(ConnectionFactory connectionFactory, BallDataConsumer consumer) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ballQueue().getName());
        container.setMessageListener(ballMessageAdapter(consumer));
        return container;
    }


}