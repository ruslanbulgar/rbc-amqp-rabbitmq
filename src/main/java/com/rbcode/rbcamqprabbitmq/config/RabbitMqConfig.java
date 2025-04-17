package com.rbcode.rbcamqprabbitmq.config;

import com.rbcode.rbcamqprabbitmq.listener.AlertBatchMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rbc.queue.name}")
    private String queueName;

    @Value("${rbc.exchange.name}")
    private String topicExchangeName;

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
                                             AlertBatchMessageListener batchMessageListener) {

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
        container.setBatchSize(10);
        // this is the size of messages read from rabbit, guess is better to be the same as batch
        container.setPrefetchCount(20);
        // this is the interval on which the batch is proccessed
        container.setReceiveTimeout(500);

        return container;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
