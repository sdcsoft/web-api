package cn.com.sdcsoft.webapi.web.datacenter.controller.boiler;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartCategory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/part")
public class Boiler_Part {

    /**
     * 获取辅机大类列表
     */
    @GetMapping(value = "/list")
    public void List(){

    }

    /**
     * 创建一个辅机大类
     * @param partCategory
     */
    @PostMapping(value = "/create")
    public void create(@RequestBody PartCategory partCategory){

    }

    /**
     * 修改一个辅机大类
     * @param partCategory
     */
    @PostMapping(value = "/modify")
    public void modify(@RequestBody PartCategory partCategory){

    }

    /**
     * 删除一个辅机大类
     * @param partId
     */
    @PostMapping(value = "/remove")
    public void remove(@RequestBody Integer partId){

    }
}
