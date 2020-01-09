package cn.com.sdcsoft.webapi.web.datacenter.boiler.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_PartSubCategoryMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartSubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/subpart")
@Auth
public class Boiler_SubPartController {

    @Autowired
    Customer_DB_PartSubCategoryMapper subCategoryMapper;

    /**
     * 获取辅机小类列表
     */
    @GetMapping(value = "/list")
    public Result List(@RequestParam Integer partId) {
        return Result.getSuccessResult(subCategoryMapper.findAllPartCategoryId(partId));
    }

    /**
     * 创建一个辅机大类
     *
     * @param partSubCategory
     */
    @PostMapping(value = "/create")
    public Result create(@RequestBody PartSubCategory partSubCategory) {
        subCategoryMapper.create(partSubCategory);
        return Result.getSuccessResult();
    }

    /**
     * 修改一个辅机小类
     *
     * @param partSubCategory
     */
    @PostMapping(value = "/modify")
    public Result modify(@RequestBody PartSubCategory partSubCategory) {
        subCategoryMapper.modify(partSubCategory);
        return Result.getSuccessResult();
    }


    /**
     * 删除一个辅机小类
     *
     * @param subPartId
     */
    @PostMapping(value = "/remove")
    public Result remove(@RequestParam Integer subPartId) {
        subCategoryMapper.remove(subPartId);
        return Result.getSuccessResult();
    }
}
