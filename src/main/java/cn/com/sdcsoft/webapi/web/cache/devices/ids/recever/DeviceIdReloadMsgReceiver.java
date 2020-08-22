package cn.com.sdcsoft.webapi.web.cache.devices.ids.recever;

import cn.com.sdcsoft.webapi.web.cache.devices.ids.service.DeviceIdService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 消息格式：设备编号-操作
 * 操作：add新增 del删除
 */
@Component
public class DeviceIdReloadMsgReceiver {
    private static final String ADD = "ADD";
    private static final String DEL = "DEL";

    @Autowired
    DeviceIdService service;

    @RabbitListener(queues = "${reload.queue}")
    @RabbitHandler
    public void process(String msg) {
        try {
            if (!StringUtils.isEmpty(msg)) {
                //System.out.println(msg);
                String[] strings = msg.split("-");
                if (strings.length == 2) {
                    if (strings[1].equals(DEL) || strings[1].equals(ADD)) {
                        service.load();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
