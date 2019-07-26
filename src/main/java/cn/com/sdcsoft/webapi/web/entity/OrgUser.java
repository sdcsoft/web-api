package cn.com.sdcsoft.webapi.web.entity;

import java.io.Serializable;

public class OrgUser implements Serializable {
    private Integer id;

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    private Integer orgType;
    private Integer orgId;
    private Integer roleId;

    private String realName;


    public Integer getId() {
        return id;
    }

    /**
     * 设置注册用户Id（非登录账号）
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    /**
     * 设置锅炉厂Id
     * @param orgId
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }


    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置职务Id
     * @param roleId 0或null为管理员
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
