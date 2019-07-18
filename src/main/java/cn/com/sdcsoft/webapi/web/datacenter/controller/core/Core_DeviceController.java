package cn.com.sdcsoft.webapi.web.datacenter.controller.core;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.datacenter.Device;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备管理接口
 */
@RestController
@RequestMapping(value = "/webapi/datacenter/core/device", produces = "application/json;charset=utf-8")
@Auth
public class Core_DeviceController extends BaseController{

    /**
     * 获取控制器列表
     * @return
     */
    @GetMapping(value = "/list")
    public String getAll() {
        return lan_api.deviceList();
    }

    /**
     * 查找企业的类型列表
     * @param enterpriseId
     * @return
     */
    @GetMapping(value = "/list/enterprise")
    public String getEnterprise(int enterpriseId) {
        return lan_api.deviceFindByEnterprise(enterpriseId);
    }

    /**
     * 查找锅炉厂的设备列表
     * @param customerId
     * @return
     */
    @GetMapping(value = "/list/customer")
    public String getCustomer(int customerId) {
        return lan_api.deviceFindByCustomer(customerId);
    }

    /**
     * 根据deviceNo获取可用设备，设备状态非可用时，deviceNo有效也无法拿到数据
     * 专为手机APP/微信小程序提供的添加设备用的查询接口
     * @param deviceNo 加密或非加密的设备编号
     * @return
     */
    @GetMapping(value = "/get/deviceno")
    public String findByNo(@RequestParam("deviceNo") String deviceNo) {
        return lan_api.deviceFindByDeviceNo(deviceNo);
    }

    /**
     * 根据suffix获取可用设备，设备状态非可用时，suffix有效也无法拿到数据
     * 专为手机APP/微信小程序提供的添加设备用的查询接口
     * @param suffix 未加密的设备编号
     * @return
     */
    @GetMapping(value = "/get/suffix")
    public String findBySuffix(@RequestParam("id") String suffix) {
        return lan_api.deviceFindSuffix(suffix);
    }


    /**
     * 根据suffix获取设备信息，设备状态非可用也可获取到数据
     * 专为微信小程序提供的企业内部员工查询设备的接口
     * @param suffix
     * @return
     */
    @GetMapping(value = "/fix/suffix")
    public String findBySuffixForEuser(String suffix) {
        return lan_api.deviceFindBySuffixForEnterpriseUser(suffix);
    }

    /**
     * 根据suffix修改设备信息
     * 专为微信小程序提供的企业内部员工修改设备的接口
     * @param suffix
     * @param prefix 设备类型 1为控制器 2为PLC
     * @param deviceType 设备类型信息
     * @param saleStatus 销售状态 0 未销售 1 已销售
     * @return
     */
    @PostMapping(value = "/fix/modify")
    public String modifyDevice(String suffix, int prefix, String deviceType, int saleStatus) {
        return lan_api.deviceModifyForEnterpriseUser(suffix,prefix,deviceType,saleStatus);
    }

    /**
     * 批量创建设备
     * @param deviceList  要创建的设备列表
     * @return
     */
    @PostMapping("/create")
    public String insertManyDevice(@RequestBody List<Device> deviceList) {
        return lan_api.deviceCreate(deviceList);
    }

    /**
     * 根据suffix修改设备类型信息,专为web端、微信端核对设备类型及修改掉用
     * @param deviceType 设备类型信息
     * @param subType 具体类型信息
     * @return
     */
    @PostMapping(value = "/modify/type")
    public String modifyDeviceType(String suffix, String deviceType, String subType) {
        return lan_api.deviceModifyType(suffix,deviceType,subType);
    }


    /**
     * 获取设备类型列表
     * @return
     */
    @GetMapping(value = "/type/list")
    public String getDeviceTypeList() {
        return lan_api.deviceTypeList();
    }

    /**
     * 创建设备类型
     * @param typeName
     * @return
     */
    @PostMapping(value = "/type/create")
    public String createDeviceType(String typeName) {
        return lan_api.deviceTypeCreate(typeName);
    }

    /**
     * 修改设备类型
     * @param id
     * @param typeName
     * @return
     */
    @PostMapping(value = "/type/modify")
    public String modifyDeviceType(int id, String typeName) {
        return lan_api.deviceTypeModify(id,typeName);
    }

}
