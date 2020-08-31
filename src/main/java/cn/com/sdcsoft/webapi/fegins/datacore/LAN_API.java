package cn.com.sdcsoft.webapi.fegins.datacore;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Device;
import cn.com.sdcsoft.webapi.entity.datacenter.DeviceDataMap;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "${cloud.lan-api}")
public interface LAN_API {

    @GetMapping("/datacore/agent/list")
    String agentList();

    @GetMapping(value = "/datacore/agent/find")
    String agentFindById(@RequestParam("id") int id);

    @PostMapping("/datacore/agent/create")
    String agentCreate(@RequestParam("agentName") String agentName, @RequestParam("status") int status);

    @PostMapping("/datacore/agent/modify")
    String agentModify(@RequestParam("id") int id, @RequestParam("agentName") String agentName, @RequestParam("status") int status);

    @GetMapping("/datacore/company/list")
    String companyList();

    @PostMapping("/datacore/company/create")
    String companyCreate(@RequestParam("companyName") String companyName, @RequestParam("status") int status);

    @PostMapping("/datacore/company/modify")
    String companyModify(@RequestParam("id") int id, @RequestParam("companyName") String companyName, @RequestParam("status") int status);

    @GetMapping("/datacore/customer/list")
    String customerList();

    @GetMapping(value = "/datacore/customer/find")
    String customerFindById(@RequestParam("id") int id);

    @GetMapping(value = "/datacore/customer/prefix")
    Result customerPrefix(@RequestParam("id") Integer id);

    @PostMapping("/datacore/customer/init")
    String customerInit(@RequestParam("customerName") String customerName, @RequestParam("codePrefix") String codePrefix);

    @PostMapping("/datacore/customer/modify")
    String customerModify(@RequestParam("id") int id, @RequestParam("status") int status);

    @PostMapping(value = "/datacore/device/modify/type")
    String deviceModifyType(@RequestParam("suffix") String suffix, @RequestParam("deviceType") String deviceType, @RequestParam("subType") String subType);

    @GetMapping("/datacore/device/list")
    String deviceList();

    @GetMapping(value = "/datacore/device/list/enterprise")
    String deviceFindByEnterprise(@RequestParam("enterpriseId") int enterpriseId);

    @GetMapping(value = "/datacore/device/list/customer")
    String deviceFindByCustomer(@RequestParam("customerId") int customerId);

    @GetMapping(value = "/datacore/device/list/suffix5")
    Result deviceFindBySuffix5(@RequestParam("suffix5") String suffix5);

    @GetMapping(value = "/datacore/device/get/deviceno")
    String deviceFindByDeviceNo(@RequestParam("deviceNo") String deviceNo);

    @GetMapping(value = "/datacore/device/get/suffix")
    String deviceFindSuffix(@RequestParam("id") String suffix);

    @GetMapping(value = "/datacore/device/fix/suffix")
    String deviceFindBySuffixForEnterpriseUser(@RequestParam("suffix") String suffix);

    @PostMapping(value = "/datacore/device/fix/modify")
    String deviceModifyForEnterpriseUser(@RequestParam("suffix") String suffix, @RequestParam("prefix") int prefix, @RequestParam("deviceType") String deviceType, @RequestParam("saleStatus") int saleStatus, @RequestParam("power") int power, @RequestParam("media") int media, @RequestParam("iMEI") String iMEI,@RequestParam("isCanCtl") Integer isCanCtl,@RequestParam("cnId") Integer cnId,@RequestParam("enId") Integer enId);

    @PostMapping(value = "/datacore/device/create")
    String deviceCreate(@RequestBody List<Device> deviceList);

    @GetMapping("/datacore/device/type/list")
    String deviceTypeList();

    @PostMapping("/datacore/device/type/create")
    String deviceTypeCreate(@RequestParam("typeName") String typeName);

    @PostMapping("/datacore/device/type/modify")
    String deviceTypeModify(@RequestParam("id") int id, @RequestParam("typeName") String typeName);

    @PostMapping(value = "/datacore/device/check/enterprise")
    Result deviceCheckEnterprise(@RequestParam("suffix") String suffix, @RequestParam("enterpriseId") Integer enterpriseId);


    @PostMapping(value = "/datacore/device/modify/customerid")
    String deviceModifyCustomerId(@RequestParam("suffix") String suffix, @RequestParam("customerId") Integer customerId);

    @PostMapping(value = "/datacore/device/modify/agentid")
    String deviceModifyAgentId(@RequestParam("suffix") String suffix, @RequestParam("agentId") Integer agentId);

    @PostMapping(value = "/datacore/device/modify/enduserid")
    String deviceModifyEndUserId(@RequestParam("suffix") String suffix, @RequestParam("endUserId") Integer endUserId);

    @GetMapping(value = "/datacore/device/snapshots")
    Result deviceSnapshots();
    @GetMapping(value = "/datacore/device/count/system/devices/amount")
    Result countSystemDevicesAmount();
    @GetMapping(value = "/datacore/device/count/enterprise/devices/amount")
    Result countEnterpriseDevicesAmount(@RequestParam("enterpriseId") Integer enterpriseId);
    @GetMapping(value = "/datacore/device/count/customer/devices/amount")
    Result countCustomerDevicesAmount(@RequestParam("enterpriseId") Integer enterpriseId,@RequestParam("customerId") Integer customerId);
    @GetMapping(value = "/datacore/device/count/system/devices/mediapowergroups")
    Result countSystemDevicesInfo();
    @GetMapping(value = "/count/enterprise/devices/mediapowergroups")
    Result countEnterpriseDevicesInfo(@RequestParam("enterpriseId") Integer enterpriseId);
    @GetMapping(value = "/datacore/device/count/customer/devices/mediapowergroups")
    Result countCustomerDevicesInfo(@RequestParam("enterpriseId") Integer enterpriseId,@RequestParam("customerId") Integer customerId);

    @GetMapping(value = "/datacore/employee/list")
    String employeeList();

    @PostMapping(value = "/datacore/employee/create")
    String employeeCreate(@RequestBody Employee employee);

    @GetMapping(value = "/datacore/employee/find")
    String employeeFind(@RequestParam("loginId") String loginId);

    @PostMapping(value = "/datacore/employee/wechat")
    Result employeeFindWechat(@RequestParam("openId") String openId);

    @PostMapping(value = "/datacore/employee/find/openId")
    Result findWeChatEmployee(@RequestParam("openId") String openId);

    @PostMapping(value = "/datacore/employee/wechat2")
    Result employeeFindWechat2(@RequestParam("unionId") String unionId);

    @PostMapping(value = "/datacore/employee/wechat/bind")
    Result employeeBindWechat(@RequestParam("loginId") String loginId, @RequestParam("openId") String openId, @RequestParam("unionId") String unionId);

    @GetMapping(value = "/datacore/employee/find/company")
    String employeeFindCompanyUser(@RequestParam("loginId") String loginId);

    @GetMapping(value = "/datacore/employee/find/enterprise")
    String employeeFindEnterprise(@RequestParam("loginId") String loginId);

    @GetMapping(value = "/datacore/employee/find/customer")
    String employeeFindCustomerUser(@RequestParam("loginId") String loginId);

    @GetMapping(value = "/datacore/employee/find/agent")
    String employeeFindAgentUser(@RequestParam("loginId") String loginId);

    @GetMapping(value = "/datacore/employee/find/enduser")
    String employeeFindEndUser(@RequestParam("loginId") String loginId);

    @PostMapping(value = "/datacore/employee/modify")
    String employeeModify(@RequestParam("employee") Employee employee);

    @PostMapping(value = "/datacore/employee/modify/org")
    Result employeeModifyOrg(@RequestParam("employeeId") Integer employeeId, @RequestParam("orgType") int orgType, @RequestParam("orgId") int orgId);

    @PostMapping(value = "/datacore/employee/change/status")
    String employeeChangeStatus(@RequestParam("loginId") String loginId, @RequestParam("status") int status);

    @PostMapping(value = "/datacore/employee/change/infos")
    String employeeChangeInfos(@RequestParam("loginId") String loginId, @RequestParam("mobile") String mobile, @RequestParam("email") String email, @RequestParam("qq") String qq, @RequestParam("realName") String realName);

    @PostMapping(value = "/datacore/employee/change/user/password")
    String employeeChangeUserPassword(@RequestParam("loginId") String loginId, @RequestParam("password") String password);

    @PostMapping(value = "/datacore/employee/change/password")
    String employeeChangePassword(@RequestParam("id") String id, @RequestParam("password") String password);

    @GetMapping(value = "/datacore/employee/wechat/users/amount")
    Result countWechatUsersAmount();
    @GetMapping(value = "/datacore/employee/wechat/users")
    Result countWechatUsers();


    @GetMapping("/datacore/enduser/list")
    String enduserList();

    @GetMapping(value = "/datacore/enduser/find")
    String endUserFindById(@RequestParam("id") int id);

    @PostMapping("/datacore/enduser/create")
    String enduserCreate(@RequestParam("endUserName") String endUserName, @RequestParam("status") int status);

    @PostMapping("/datacore/enduser/modify")
    String enduserModify(@RequestParam("id") int id, @RequestParam("endUserName") String endUserName, @RequestParam("status") int status);

    @GetMapping(value = "/datacore/enterprise/list")
    String enterpriseList();

    @GetMapping(value = "/datacore/enterprise/find")
    String enterpriseFindById(@RequestParam("id") int id);

    @PostMapping(value = "/datacore/enterprise/create")
    String enterpriseCreate(@RequestParam("enterpriseName") String enterpriseName, @RequestParam("status") int status);

    @PostMapping(value = "/datacore/enterprise/modify")
    String enterpriseModify(@RequestParam("id") int id, @RequestParam("enterpriseName") String enterpriseName, @RequestParam("status") int status);

    @GetMapping(value = "/datacore/enterprise/prefix/list")
    String enterprisePrefixList();

    @RequestMapping(value = "/datacore/enterprise/prefix")
    Result enterprisePrefix(@RequestParam("enterpriseId") Integer enterpriseId);

    @PostMapping(value = "/datacore/enterprise/prefix/modify")
    String enterprisePrefixModify(@RequestParam("enterpriseId") int enterpriseId, @RequestParam("status") int status);

    @PostMapping(value = "/datacore/enterprise/prefix/create")
    String enterprisePrefixCreate(@RequestParam("enterpriseId") int enterpriseId, @RequestParam("prefix") String prefix);

    @GetMapping(value = "/datacore/enterprise/customer/list")
    String enterpriseCustomerList(@RequestParam("enterpriseId") int enterpriseId);

    @PostMapping(value = "/datacore/enterprise/customer/create")
    String enterpriseCustomerCreate(@RequestParam("enterpriseId") int enterpriseId, @RequestParam("customerName") String customerName, @RequestParam("status") int status);

    @PostMapping(value = "/datacore/enterprise/customer/modify")
    String enterpriseCustomerModify(@RequestParam("id") int id, @RequestParam("customerName") String customerName);

    @GetMapping(value = "/datacore/enterprise/customer/prefix/list")
    String enterpriseCustomerPrefixList(@RequestParam("enterpriseCustomerId") int enterpriseCustomerId);

    @GetMapping(value = "/datacore/enterprise/modify/lastdeviceno")
    String modifyLastDeviceNo(@RequestParam("lastDeviceNo") String lastDeviceNo,@RequestParam("code") String code);

    @PostMapping(value = "/datacore/enterprise/customer/prefix/create")
    String enterpriseCustomerPrefixCreate(@RequestParam("enterpriseCustomerId") int enterpriseCustomerId, @RequestParam("code") String code);

    @GetMapping("/datacore/org/list")
    String orgList();

    @PostMapping("/datacore/org/create")
    String orgCreate(@RequestParam("orgType") int orgType, @RequestParam("orgTypeName") String orgTypeName);

    @PostMapping("/datacore/org/modify")
    String orgModify(@RequestParam("orgType") int orgType, @RequestParam("orgTypeName") String orgTypeName);

    @GetMapping(value = "/datacore/sms/send/vcode")
    Result smsSendVcode(@RequestParam("lang") String lang, @RequestParam("msg") String[] msg);

    @GetMapping(value = "/datacore/sms/send/exception")
    Result smsSendExpection(@RequestParam("lang") String lang, @RequestParam("msg") String[] msg);

    //新点位协议相关接口
    @GetMapping("/datacore/device/modify/datalength")
    Result deviceModifyDataLength(@RequestParam("deviceSuffix") String deviceSuffix,@RequestParam("dataLength") Integer dataLength);

    @GetMapping("/datacore/device/modify/datamap")
    Result deviceModifyDataLength(@RequestParam("deviceSuffix") String deviceSuffix,@RequestParam("mapCn") Integer mapCn,@RequestParam("mapEN") Integer mapEN);

    @PostMapping("/datacore/datamap/create")
    Result dataMapCreate(@RequestBody DeviceDataMap dataMap);

    @PostMapping("/datacore/datamap/search")
    Result dataMapSearch(@RequestParam("title") String title,@RequestParam("author") String author);

    @PostMapping("/datacore/datamap/search/author")
    Result dataMapSearchByAuthor(@RequestParam("author") String author);

    @GetMapping("/datacore/datamap/get")
    Result dataMapGet(@RequestParam("id") Integer id);

    @PostMapping("/datacore/datamap/modify/map/{id}")
    Result dataMapModifyMap(@PathVariable("id") Integer id, @RequestBody String dataMap);

    @PostMapping("/datacore/datamap/modify/share")
    Result dataMapModifyShare(@RequestParam("id") Integer id,@RequestParam("share") Integer share);

    @PostMapping("/datacore/datamap/modify/share/title")
    Result modifyShareAndTitle(@RequestParam("id") Integer id,@RequestParam("share") Integer share,@RequestParam("title") String title);

    @PostMapping("/datacore/datamap/modify/other")
    Result dataMapModifyOther(@RequestParam("id") Integer id,@RequestParam("pointIndexMap") String pointIndexMap,@RequestParam("dataLength") Integer dataLength);

    @GetMapping("/datacore/device/ids")
    Result deviceIdList();
}
