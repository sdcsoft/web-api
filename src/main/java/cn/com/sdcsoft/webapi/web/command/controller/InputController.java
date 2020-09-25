package cn.com.sdcsoft.webapi.web.command.controller;

import cn.com.sdcsoft.webapi.rabbitMQ.sender.CmdMsgSender;
import cn.com.sdcsoft.webapi.utils.CommandCacheUtil;
import cn.com.sdcsoft.webapi.web.command.entity.DeviceCommandsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/webapi/commands")
public class InputController {
    @Autowired
    CmdMsgSender cmdMsgSender;
    @Autowired
    private CommandCacheUtil commandCacheUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private void errorLog(String msg){
        //logger.error(String.format("%s\r\n",msg));
    }

    @PostMapping(value = "/send2")
    public void addCommand(@RequestHeader(name = "DeviceSuffix") String key,
                           @RequestParam String command){
        addCommand2("noName",key,command);
    }

    @PostMapping(value = "/send")
    public void addCommand2(@RequestHeader(name = "UserId") String userId,
                           @RequestHeader(name = "DeviceSuffix") String key,
                           @RequestParam String command){
        errorLog(String.format("%s send %s->%s",userId,key,command));

        Object item = commandCacheUtil.getCacheItem(key);

        if(null == item){
            DeviceCommandsItem deviceCommandsItem = new DeviceCommandsItem();
            deviceCommandsItem.setDeviceNo(key);
            deviceCommandsItem.addCommand(command);
            commandCacheUtil.putCacheItem(key,deviceCommandsItem);
        }
        else{
            DeviceCommandsItem deviceCommandsItem = (DeviceCommandsItem)item;
            deviceCommandsItem.addCommand(command);
        }
        Object[] msg = new Object[]{
                key,
                toBytes(command)
        };
        cmdMsgSender.send(msg);
    }



    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }
}