package cn.com.sdcsoft.webapi.rabbitMQ.sender;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CmdMsgSender {

    @Resource(name = "baseRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void send(Object msg) {
        try {
            rabbitTemplate.convertAndSend("NjzjCmdMsgQueue",msg);
        } catch (AmqpException e) {
            System.out.println(e);
        }
    }
}
