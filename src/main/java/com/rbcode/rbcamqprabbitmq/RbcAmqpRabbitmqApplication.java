package com.rbcode.rbcamqprabbitmq;

import com.rbcode.rbcamqprabbitmq.messagingrabbitmq.StringBatchMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RbcAmqpRabbitmqApplication {

    public static final String topicExchangeName = "rbc-message-exchange";

    public static final String queueName = "rbc-queue";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("rbc.key.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             StringBatchMessageListener batchMessageListener) {

        ((CachingConnectionFactory) connectionFactory).setUsername("user");
        ((CachingConnectionFactory) connectionFactory).setPassword("pass");
        ConditionalRejectingErrorHandler errorHandler = new ConditionalRejectingErrorHandler();
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        // if we set consumers greatter then one, then we will read messages concurrently
        // and we need a higher rate of insertion of messages in queue
        container.setConcurrentConsumers(1);
        container.setErrorHandler(errorHandler);
        container.setMessageListener(batchMessageListener);
        container.setConsumerBatchEnabled(true);
        // this is the maximum size of the batch
        container.setBatchSize(5);
        // this is the size of messages read from rabbit, guess is better to be the same as batch
        container.setPrefetchCount(10);
        // this is the interval on which the batch is proccessed
        container.setReceiveTimeout(500);

        return container;
    }

    public static void main(String[] args) {
        SpringApplication.run(RbcAmqpRabbitmqApplication.class, args);
    }
}
