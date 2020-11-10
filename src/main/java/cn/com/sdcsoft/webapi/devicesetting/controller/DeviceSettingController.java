package cn.com.sdcsoft.webapi.devicesetting.controller;

import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceMap;
import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceSetting;
import cn.com.sdcsoft.webapi.devicesetting.entity.ModbusSetting;
import cn.com.sdcsoft.webapi.devicesetting.service.*;
import cn.com.sdcsoft.webapi.devicesetting.utils.CRC16;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/devicesetting/setting")
public class DeviceSettingController {

    @Autowired
    DeviceSettingService deviceSettingService;
    @Autowired
    DeviceTypeService deviceTypeService;
    @Autowired
    DeviceFactoryService deviceFactoryService;
    @Autowired
    DeviceLineService deviceLineService;
    @Autowired
    DeviceAttrService deviceAttrService;
    @Autowired
    DeviceMapService deviceMapService;

    @PostMapping("/create")
    public Result create(DeviceSetting setting) {
        try {
            deviceSettingService.save(setting);
            return Result.getSuccessResult();
        } catch (Exception ex) {
            return Result.getFailResult(ex.getMessage());
        }
    }

    @RequestMapping("/list/type")
    public Result listType() {
        return Result.getSuccessResult(deviceTypeService.list());
    }

    @RequestMapping("/list/factory")
    public Result listFactory(String deviceType) {
        return Result.getSuccessResult(deviceFactoryService.list(deviceType));
    }

    @RequestMapping("/list/line")
    public Result listLine(String factory) {
        return Result.getSuccessResult(deviceLineService.list(factory));
    }

    @RequestMapping("/list/attr")
    public Result listAttr(String line) {
        return Result.getSuccessResult(deviceAttrService.list(line));
    }

    @RequestMapping("/get")
    public Result get(String deviceNo) {
        DeviceSetting setting = deviceSettingService.get(deviceNo);
        if (null == setting) {
            return Result.getFailResult("无配置！");
        }
        DeviceMap map = deviceMapService.get(setting.getDeviceMapId());
        if (null == map) {
            return Result.getFailResult("配置对应点位表无效！");
        }
        List<String> params = map.getParams();
        List<String> params2 = setting.getParams();
        if (params2.size() != params.size()) {
            return Result.getFailResult("设备配置与点位表参数数量不相同！");
        }

        List<String> otherCommands = map.getOtherCommands();
        String otherCommandString = String.join(";", otherCommands);
        for (int i = 0; i < params2.size(); i++) {
            otherCommandString = otherCommandString.replace(params.get(i), params2.get(i));
        }

        List<ModbusSetting> modbusSettingList = map.getModbusSettings();
        try {
            int stationNo = Integer.parseInt(setting.getStationNo());
            List<String> cmds = new ArrayList<>(20);
            for (int i = 0; i < modbusSettingList.size(); i++) {
                ModbusSetting ms = modbusSettingList.get(i);
                int action = Integer.parseInt(ms.getAction());
                int address = Integer.parseInt(ms.getStartAddress());
                int addr1 = (byte) (address & 0xff);
                int addr2 = (byte) ((address >> 8) & 0xff);
                int length = ms.getLength();
                int len1 = (byte) (length & 0xff);
                int len2 = (byte) ((length >> 8) & 0xff);
                int[] datas = {
                        (byte) stationNo,
                        (byte) action,
                        addr2,
                        addr1,
                        len2,
                        len1
                };
                int crc = CRC16.crc16(datas);
                //final String hex = Integer.toHexString(crc);
                String cmd = String.format("AT+LOCALCMD=%d,%02X%02X%04X%04X%04X#",
                        i,stationNo,action,address,length,crc);
                cmds.add(cmd);
            }
            String modbusCommandString = String.join(";",cmds);
            String settingString = String.format("%s;%s",modbusCommandString,otherCommandString);
            return Result.getSuccessResult("success.",settingString);
        } catch (Exception ex) {
            return Result.getFailResult(ex.getMessage());
        }
    }

}
