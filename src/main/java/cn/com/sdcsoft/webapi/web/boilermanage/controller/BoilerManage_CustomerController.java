package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.CustomerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 客户管理
 */
@RestController
@RequestMapping(value = "/webapi/boilermanage/customer", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public Result list(int pageNum, int pageSize, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        List<Customer> list = customerMapper.find(orgId);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(list);
        return Result.getSuccessResult(pageInfo);

    }

    /**
     * 搜索客户
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/search")
    public Result search(String name, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        return Result.getSuccessResult(customerMapper.search(orgId, name));
    }

    /**
     * 创建客户
     *
     * @param customer
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody Customer customer, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        customer.setOrgId(orgId);
        if (0 < customerMapper.checkExist(customer)) {
            return Result.getFailResult("已存在该名称的客户数据！");
        }
        customerMapper.createCustomer(customer);
        return Result.getSuccessResult();
    }

    /**
     * 编辑客户
     *
     * @param customer
     * @return
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody Customer customer,HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        customer.setOrgId(orgId);
        if(0 < customerMapper.checkExist(customer)){
            customerMapper.modifyCustomerExtendsInfo(customer);
            return Result.getSuccessResult();
        }
        customerMapper.modifyCustomer(customer);
        return Result.getSuccessResult();
    }

    /**
     * 删除客户
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/remove")
    public Result remove(@RequestParam int id, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        customerMapper.removeCustomer(orgId, id);
        return Result.getSuccessResult();
    }
}
