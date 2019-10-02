package it.laterale.cloud.config;

import it.laterale.cloud.consumers.BallDataConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * The type Rabbit mq config.
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    private Environment env;

    /**
     * Ball queue queue.
     *
     * @return the queue
     */
    @Bean
    public Queue ballQueue() {
        return createQueue(this.env.getProperty("spring.rabbitmq.queue.ball.name"));
    }

    /**
     * Ball message adapter message listener adapter.
     *
     * @param consumer the consumer
     * @return the message listener adapter
     */
    @Bean
    public MessageListenerAdapter ballMessageAdapter(BallDataConsumer consumer) {
        return new MessageListenerAdapter(consumer, "receiveMessage");
    }

    /**
     * Ball message listener container simple message listener container.
     *
     * @param connectionFactory the connection factory
     * @param consumer          the consumer
     * @return the simple message listener container
     */
    @Bean
    public SimpleMessageListenerContainer ballMessageListenerContainer(ConnectionFactory connectionFactory, BallDataConsumer consumer) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ballQueue().getName());
        container.setMessageListener(ballMessageAdapter(consumer));
        return container;
    }

    private Queue createQueue(String name) {
        log.debug("Creating queue {}", name);
        return new Queue(name);
    }

}