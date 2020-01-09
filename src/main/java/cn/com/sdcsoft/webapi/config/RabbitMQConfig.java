package cn.com.sdcsoft.webapi.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {

    private ConnectionFactory getFactory(String host, int port, String virtual_host, String username, String password) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtual_host);
        return connectionFactory;
    }

    @Bean(name = "baseConnectionFactory")
    @Primary
    public ConnectionFactory baseConnectionFactory(
            @Value("${spring.rabbitmq.base.host}") String host,
            @Value("${spring.rabbitmq.base.port}") int port,
            @Value("${spring.rabbitmq.base.virtual-host}") String virtual_host,
            @Value("${spring.rabbitmq.base.username}") String username,
            @Value("${spring.rabbitmq.base.password}") String password
    ) {
        return getFactory(host, port, virtual_host, username, password);
    }


    @Bean(name = "baseRabbitTemplate")
    @Primary
    public RabbitTemplate baseRabbitTemplate(@Qualifier("baseConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

    @Bean(name="baseRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("baseConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
