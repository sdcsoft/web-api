package cn.com.sdcsoft.webapi.web.command.entity;

import java.util.ArrayList;
import java.util.List;

public class DeviceCommandsItem {
    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    private String deviceNo;
    private List<String> commands;
    public DeviceCommandsItem(){
        commands = new ArrayList<>(10);
    }

    public void addCommand(String command){
        commands.add(command);
    }

    public String getCommands(){
        StringBuilder sb = new StringBuilder();
        for (String str : commands)
        {
            sb.append(str);
        }
        return sb.toString();// StringUtils.join(commands,0);
    }
}
