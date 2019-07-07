package cn.com.sdcsoft.webapi.web.datacenter;

import cn.com.sdcsoft.webapi.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 公司管理接口
 */
@RestController
@RequestMapping(value = "/webapi/datacenter/company", produces = "application/json;charset=utf-8")
@Auth
public class DataCenter_CompanyController extends BaseController {


    /**
     * 获取公司列表
     * @return
     */
    @GetMapping(value = "/list")
    public String getAllCompany() {
        return lan_api.companyList();
    }

    /**
     * 创建公司
     * @param companyName
     * @param status
     * @return
     */
    @PostMapping(value = "/create")
    public String create(String companyName,int status) {
        return lan_api.companyCreate(companyName,status);
    }

    /**
     * 修改公司信息
     * @param id
     * @param companyName
     * @param status
     * @return
     */
    @PostMapping(value = "/modify")
    public String modifyCompany(int id,String companyName,int status) {
        return lan_api.agentModify(id,companyName,status);
    }
}
