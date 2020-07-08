package cn.com.sdcsoft.webapi.commcontroller.webhook;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.Setting;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hook/point")
public class PointHookController {

    @Autowired
    LAN_API lan_api;

    @PostMapping("/callback")
    public void settingCallBack(@RequestBody Setting setting){
        try {
            JSONObject result = JSONObject.parseObject(lan_api.deviceFindByDeviceNo(setting.getNo()));
            if (Result.RESULT_CODE_SUCCESS == result.getIntValue("code")) {
                JSONObject device = result.getJSONObject("data");
                Integer mapCnId = device.getInteger("deviceDataMapCn");
                Integer mapEnId = device.getInteger("deviceDataMapEn");
                if (null != mapCnId && mapCnId > 0) {
                    lan_api.dataMapModifyOther(mapCnId, setting.getMap(), setting.getLength());
                }
                if (null != mapEnId && mapEnId > 0) {
                    lan_api.dataMapModifyOther(mapEnId, setting.getMap(), setting.getLength());
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

