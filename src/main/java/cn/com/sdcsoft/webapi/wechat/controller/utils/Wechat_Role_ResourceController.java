package cn.com.sdcsoft.webapi.wechat.controller.utils;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_Role_ResourceMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_StoreMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import cn.com.sdcsoft.webapi.wechat.entity.Store;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/webapi/wechat/RoleResource")
public class Wechat_Role_ResourceController {

    @Autowired
    private Wechat_DB_Role_ResourceMapper wechat_db_role_resourceMapper;


    @GetMapping(value = "/list")
    public Result Role_ResourcelistbyemployeeMobile(String openId) {
        return Result.getSuccessResult(wechat_db_role_resourceMapper.getRole_ResourceListByopenId(openId));
    }


    @PostMapping("/create")
    public Result editRole_Resource(@RequestBody Role_Resource role_resource) {
        wechat_db_role_resourceMapper.insertRole_Resource(role_resource);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result modifyRole_Resource(@RequestBody Role_Resource role_resource) {
        wechat_db_role_resourceMapper.updateRole_Resource(role_resource);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/remove")
    public Result deleteRole_Resource(String openId, String deviceNo) {
        wechat_db_role_resourceMapper.deleteWxDevice(deviceNo, openId);
        return Result.getSuccessResult();
    }

    @PostMapping("/create/many")
    public Result insertManyRole_Resource(String role_ResourceList) {
        List<Role_Resource> list = JSON.parseArray(role_ResourceList, Role_Resource.class);
        for (int i = 0; i < list.size(); i++) {
//           if(wechat_db_role_resourceMapper.getWxDeviceByopenIdAndDeviceNo(list.get(i).getOpenId(),list.get(i).getDeviceNo())!=null){
//               list.remove(i);
//           }
        }
        if (list.size() > 0) {
            wechat_db_role_resourceMapper.insertManyRole_Resource(list);
            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }
}

