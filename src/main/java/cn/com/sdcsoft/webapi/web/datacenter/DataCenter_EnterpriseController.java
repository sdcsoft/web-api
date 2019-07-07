package cn.com.sdcsoft.webapi.web.datacenter;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 企业管理接口
 * 企业的客户、产品、员工 应该单独进行数据库存储，现临时放在核心库中
 * 需要将来分离的数据表有：
 * EnterpriseCustomer
 * EnterpriseCustomerCode
 * EnterpriseProduct
 * 现在EnterpriseProduct暂时没有使用，企业的产品临时由Device充当
 * 将来需要由Device数据构建出企业的产品信息并存入企业产品库
 */

@RestController
@RequestMapping(value = "/webapi/datacenter/enterprise", produces = "application/json;charset=utf-8")
//@Auth
public class DataCenter_EnterpriseController extends BaseController{

    /**
     * 获取企业列表
     * @return
     */
    @GetMapping(value = "/list")
    public String getAll() {
        return lan_api.enterpriseList();
    }


    /**
     * 创建企业
     * @param enterpriseName
     * @param status
     * @return
     */
    @PostMapping(value = "/create")
    public String create(String enterpriseName,int status) {
        return lan_api.enterpriseCreate(enterpriseName,status);
    }

    /**
     * 修改企业信息
     * @param id
     * @param enterpriseName
     * @param status
     * @return
     */
    @PostMapping(value = "/modify")
    public String modifyEnterprise(int id,String enterpriseName,int status) {
        return lan_api.enterpriseModify(id,enterpriseName,status);
    }


    /**
     * 获取企业编号号段列表
     * 说明：该功能属于企业管理系统，临时放在核心接口中。在企业管理系统完成后会迁出
     * @return
     */
    @GetMapping(value = "/prefix/list")
    public String getEnterprisePrefix() {
        return lan_api.enterprisePrefixList();
    }

    /**
     * 修改企业编号号段信息，只能改：状态是否可用
     * 说明：该功能属于企业管理系统，临时放在核心接口中。在企业管理系统完成后会迁出
     * @param enterpriseId
     * @param status
     * @return
     */
    @PostMapping(value = "/prefix/modify")
    public String changePrefixStatus(int enterpriseId, int status) {
        return lan_api.enterprisePrefixModify(enterpriseId,status);
    }

    /**
     * 创建企业设备编号号段
     * 说明：该功能属于企业管理系统，临时放在核心接口中。在企业管理系统完成后会迁出
     * @param enterpriseId
     * @param prefix
     * @return
     */
    @PostMapping(value = "/prefix/create")
    public String createPrefix(int enterpriseId, String prefix) {
        return lan_api.enterprisePrefixCreate(enterpriseId,prefix);
    }

    /**
     * 获取企业的客户列表
     * 说明：该功能属于企业管理系统，临时放在核心接口中。在企业管理系统完成后会迁出
     *       企业的客户是由企业自行维护的数据，不属于核心库
     * @param enterpriseId
     * @return
     */
    @GetMapping(value = "/customer/list")
    public String getCustomer(int enterpriseId) {
        return lan_api.enterpriseCustomerList(enterpriseId);
    }

    /**
     * 创建企业客户
     * 说明：该功能属于企业管理系统，临时放在核心接口中。在企业管理系统完成后会迁出
     * @param enterpriseId
     * @param customerName
     * @param status
     * @return
     */
    @PostMapping(value = "/customer/create")
    public String create(int enterpriseId, String customerName, int status) {
        return lan_api.enterpriseCustomerCreate(enterpriseId,customerName,status);
    }

    /**
     * 修改企业客户信息
     * 说明：该功能属于企业管理系统，临时放在核心接口中。在企业管理系统完成后会迁出
     * @param id
     * @param customerName
     * @return
     */
    @PostMapping(value = "/customer/modify")
    public String modifyCustomer(int id, String customerName) {
        return lan_api.enterpriseCustomerModify(id,customerName);
    }


    /**
     * 获取企业客户号段列表
     * @param enterpriseCustomerId
     * @return
     */
    @GetMapping(value = "/customer/prefix/list")
    public String getEnterpriseCustomerPrefixByEnterpriseId(int enterpriseCustomerId) {
        return lan_api.enterpriseCustomerPrefixList(enterpriseCustomerId);
    }

    /**
     * 创建企业客户号段
     * 说明：该功能属于企业管理系统，临时放在核心接口中。在企业管理系统完成后会迁出
     * @param enterpriseCustomerId
     * @param code
     * @return
     */
    @PostMapping(value = "/customer/prefix/create")
    public String createEnterpriseCustomerPrefix(int enterpriseCustomerId, String code, HttpServletResponse response) {
        return lan_api.enterpriseCustomerPrefixCreate(enterpriseCustomerId,code);
    }



}
