package cn.com.sdcsoft.webapi.web.datacenter.controller.boiler;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartSubCategory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/subpart")
public class Boiler_SubPart {

    /**
     * 获取辅机小类列表
     */
    @GetMapping(value = "/list")
    public void List(@RequestParam Integer partId){

    }

    /**
     * 创建一个辅机大类
     * @param partSubCategory
     */
    @PostMapping(value = "/create")
    public void create(@RequestBody PartSubCategory partSubCategory){

    }

    /**
     * 修改一个辅机小类
     * @param partSubCategory
     */
    @PostMapping(value = "/modify")
    public void modify(@RequestBody PartSubCategory partSubCategory){

    }


    /**
     * 删除一个辅机小类
     * @param subPartId
     */
    @PostMapping(value = "/remove")
    public void remove(@RequestBody Integer subPartId){

    }
}
