package cn.com.sdcsoft.webapi.web.command.controller;

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
public class CommandsController {
    @Autowired
    private CommandCacheUtil commandCacheUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private void errorLog(String msg){
        //logger.error(String.format("%s\r\n",msg));
    }

    @PostMapping(value = "/send")
    public void addCommand(@RequestHeader(name = "DeviceSuffix") String key,
                           @RequestParam String command){
        addCommand2("noName",key,command);
    }


    @PostMapping(value = "/send2")
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
    }

    @GetMapping(value = "/get")
    public void getCommand(@RequestHeader(name = "DeviceSuffix") String key, HttpServletResponse response) throws IOException {
        Object obj = commandCacheUtil.getCacheItem(key);
        if(null != obj) {
            commandCacheUtil.removeCacheItem(key);
            DeviceCommandsItem item = (DeviceCommandsItem) obj;
            String cmd = item.getCommands();
            if(null == cmd || 0 == cmd.length())
                return;
            errorLog(String.format("%s->%s",key,cmd));
            response.getOutputStream().write(toBytes(cmd));
        }
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