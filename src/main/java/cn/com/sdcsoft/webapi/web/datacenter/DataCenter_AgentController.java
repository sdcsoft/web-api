package cn.com.sdcsoft.webapi.web.datacenter;

import cn.com.sdcsoft.webapi.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/webapi/datacenter/agent", produces = "application/json;charset=utf-8")
@Auth
public class DataCenter_AgentController extends BaseController{
    /**
     * 获取代理商列表
     * @return
     */
    @GetMapping(value = "/list")
    public String getAll() {
        return lan_api.agentList();
    }

    @PostMapping(value = "/create")
    public String create(String agentName, int status) {
        return lan_api.agentCreate(agentName,status);
    }

    /**
     * 修改代理商信息
     * @param id
     * @param agentName
     * @param status
     * @return
     */
    @PostMapping(value = "/modify")
    public String modify(int id,String agentName,int status) {
        return lan_api.agentModify(id,agentName,status);
    }
}
