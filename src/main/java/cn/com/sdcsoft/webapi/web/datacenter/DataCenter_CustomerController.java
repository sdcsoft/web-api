package cn.com.sdcsoft.webapi.web.datacenter;

import cn.com.sdcsoft.webapi.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 锅炉厂管理接口
 * 锅炉厂与企业解绑 二者业务无必然联系
 * 核心数据库中仅存储锅炉厂基本信息与用户映射关系
 * 锅炉厂产品、员工、客户均由锅炉厂数据库存储
 */

@RestController
@RequestMapping(value = "/webapi/datacenter/customer", produces = "application/json;charset=utf-8")
@Auth
public class DataCenter_CustomerController extends BaseController {

    /**
     * 获取锅炉厂列表
     * @return
     */
    @GetMapping(value = "/list")
    public String getAll() {
        return lan_api.customerList();
    }

    /**
     * 创建锅炉厂
     * @param customerName
     * @param status
     * @return
     */
    @PostMapping(value = "/create")
    public String create(String customerName, int status) {
        return lan_api.customerCreate(customerName,status);
    }

    /**
     * 修改锅炉厂信息
     * @param id
     * @param customerName
     * @param status
     * @return
     */
    @PostMapping(value = "/modify")
    public String modify(int id,String customerName,int status) {
        return lan_api.customerModify(id,customerName,status);
    }
}
