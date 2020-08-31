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
@RequestMapping(value = "/commands")
public class OutputController {

    @Autowired
    private CommandCacheUtil commandCacheUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private void errorLog(String msg){
        //logger.error(String.format("%s\r\n",msg));
    }

    @GetMapping(value = "/get")
    public void getCommand(@RequestHeader(name = "DeviceSuffix") String key, HttpServletResponse response) throws IOException {
        System.out.println("request...."+key);
        Object obj = commandCacheUtil.getCacheItem(key);
        if(null != obj) {
            commandCacheUtil.removeCacheItem(key);
            DeviceCommandsItem item = (DeviceCommandsItem) obj;
            String cmd = item.getCommands();
            if(null == cmd || 0 == cmd.length())
                return;
            errorLog(String.format("%s->%s",key,cmd));
            response.getOutputStream().write(InputController.toBytes(cmd));
        }
    }
}