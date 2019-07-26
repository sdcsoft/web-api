package cn.com.sdcsoft.webapi.web.datacenter.controller.boiler;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartCategory;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.PartCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/part")
@Auth
public class Boiler_Part {

    @Autowired
    PartCategoryMapper partCategoryMapper;

    /**
     * 获取辅机大类列表
     */
    @GetMapping(value = "/list")
    public Result List(){
        return Result.getSuccessResult(partCategoryMapper.findAll());
    }

    /**
     * 创建一个辅机大类
     * @param partCategory
     */
    @PostMapping(value = "/create")
    public Result create(@RequestBody PartCategory partCategory){
        partCategoryMapper.create(partCategory);
        return Result.getSuccessResult();
    }

    /**
     * 修改一个辅机大类
     * @param partCategory
     */
    @PostMapping(value = "/modify")
    public Result<Object> modify(@RequestBody PartCategory partCategory){
        partCategoryMapper.modify(partCategory);
        return Result.getSuccessResult();
    }

    /**
     * 删除一个辅机大类
     * @param partId
     */
    @PostMapping(value = "/remove")
    public Result remove(@RequestParam Integer partId){
        partCategoryMapper.remove(partId);
        return Result.getSuccessResult();
    }
}
