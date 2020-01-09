package cn.com.sdcsoft.webapi.web.datacenter.boiler.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_ResourceMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Resource;
import cn.com.sdcsoft.webapi.web.entity.OrgResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/resource")
@Auth
public class Boiler_ResourceController {

    @Autowired
    Customer_DB_ResourceMapper resourceMapper;

    /**
     * 获取锅炉厂所有功能项
     */
    @GetMapping(value = "/list")
    public Result list() {
        return Result.getSuccessResult(resourceMapper.list());
    }

    /**
     * 创建一项锅炉厂功能
     *
     * @param resource
     */
    @PostMapping(value = "/create")
    public Result create(@RequestBody Resource resource) {
        resourceMapper.createResource(resource);
        return Result.getSuccessResult();
    }

    /**
     * 修改一项锅炉厂功能
     *
     * @param resource
     */
    @PostMapping(value = "/modify")
    public Result modify(@RequestBody Resource resource) {
        resourceMapper.modifyResource(resource);
        return Result.getSuccessResult();
    }

    /**
     * 获取某个锅炉厂的功能列表
     *
     * @param orgId
     */
    @GetMapping(value = "/org")
    public Result org(@RequestParam Integer orgId) {
        return Result.getSuccessResult(resourceMapper.getOrgResources(orgId));
    }

    /**
     * 映射某个锅炉厂与功能列表
     *
     * @param orgId
     * @param orgResources
     */
    @PostMapping(value = "/map")
    public Result map(@RequestParam Integer orgId, @RequestBody List<OrgResource> orgResources) {
        resourceMapper.clearOrgResources(orgId);
        resourceMapper.createOrgResourceMap(orgResources);
        return Result.getSuccessResult();
    }
}
