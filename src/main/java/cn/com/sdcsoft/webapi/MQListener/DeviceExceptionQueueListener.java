package cn.com.sdcsoft.webapi.MQListener;

import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class DeviceExceptionQueueListener {

    private static final int Msg_Length = 4;
    private static final int Msg_PhoneNumber_Index = 0;
    private static final int Msg_DateTime_Index = 1;
    private static final int Msg_DeviceName_Index = 2;
    private static final int Msg_Exception_Index = 3;

    @Autowired
    LAN_API lan_api;

    @RabbitListener(
            queuesToDeclare = @Queue("DeviceExceptionQueue"),
            containerFactory = "baseRabbitListenerContainerFactory"
    )
    @RabbitHandler
    public void process(byte[] data) {
        String str = new String(data,Charset.forName("UTF-8"));
        //消息格式
        //["手机号码","时间","设备昵称","异常信息"]
        String[] msg = str.split("\\|");
        System.out.println(String.format("%d %s",msg.length,msg[0]));
        if (Msg_Length == msg.length) {
            lan_api.smsSendExpection(
                    "ZH",
                    msg);
        }
    }
}
