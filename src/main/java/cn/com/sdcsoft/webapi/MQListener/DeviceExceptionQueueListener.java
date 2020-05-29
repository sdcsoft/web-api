package cn.com.sdcsoft.webapi.MQListener;

import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.utils.DeviceSmsCacheUtil;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;

@Component
public class DeviceExceptionQueueListener {

    private static final int Msg_Length = 5;
    private static final int Msg_PhoneNumber_Index = 0;
    private static final int Msg_DateTime_Index = 1;
    private static final int Msg_DeviceName_Index = 2;
    private static final int Msg_Exception_Index = 3;

    @Value("${sms.interval}")
    int smsInterval;
    @Autowired
    DeviceSmsCacheUtil deviceSmsCacheUtil;

    @Autowired
    LAN_API lan_api;

    @RabbitListener(
            queuesToDeclare = @Queue("DeviceExceptionQueue"),
            containerFactory = "baseRabbitListenerContainerFactory"
    )
    @RabbitHandler
    public void process(byte[] data) {
        Long now = new Date().getTime();
        String str = new String(data, Charset.forName("UTF-8"));
        //消息格式
        //["手机号码","时间","设备号","异常信息","异常key"]
        String[] msg = str.split("\\|");
        String key = String.format("%s-%s", msg[2], msg[4]);
        Object item = deviceSmsCacheUtil.getCacheItem(key);
        if (null != item) {
            Long pass = (Long) item;
            if ((now - pass) / 1000 / 60 < smsInterval) {
                //如果短信发送时间间隔小于smsInterval，则无需再次发送短信
                return;
            }
        }
        //更新缓存信息
        deviceSmsCacheUtil.putCacheItem(key, now);
        System.out.println(String.format("%s %s", msg[2], msg[0]));
        if (Msg_Length == msg.length) {
            lan_api.smsSendExpection(
                    "ZH",
                    msg);
        }
    }
}
