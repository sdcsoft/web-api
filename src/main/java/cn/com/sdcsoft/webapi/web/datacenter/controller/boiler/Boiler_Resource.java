package cn.com.sdcsoft.webapi.web.datacenter.controller.boiler;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Resource;
import cn.com.sdcsoft.webapi.web.datacenter.entity.OrgResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/resource")
public class Boiler_Resource {

    /**
     * 获取锅炉厂所有功能项
     */
    @GetMapping(value = "/list")
    public void list(){

    }

    /**
     * 创建一项锅炉厂功能
     * @param resource
     */
    @PostMapping(value = "/create")
    public void create(@RequestBody Resource resource){

    }

    /**
     * 修改一项锅炉厂功能
     * @param resource
     */
    @PostMapping(value = "/modify")
    public void modify(@RequestBody Resource resource){

    }

    /**
     * 获取某个锅炉厂的功能列表
     * @param orgId
     */
    @GetMapping(value = "/org")
    public void org(@RequestParam Integer orgId){

    }

    /**
     * 映射某个锅炉厂与功能列表
     * @param orgId
     * @param orgResources
     */
    @PostMapping(value = "/map")
    public void map(@RequestParam Integer orgId, @RequestBody List<OrgResource> orgResources){

    }
}
