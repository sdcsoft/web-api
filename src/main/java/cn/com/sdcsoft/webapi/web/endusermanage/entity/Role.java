package cn.com.sdcsoft.webapi.web.endusermanage.entity;

import java.util.List;

public class Role {
    public static final int SYSTEM_ADMIN_ROLE_ID = 1;
    private Integer id;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    private Integer orgId;
    private String roleName, roleDesc;
    private List<RoleResource> roleResourceList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public List<RoleResource> getRoleResourceList() {
        return roleResourceList;
    }

    public void setRoleResourceList(List<RoleResource> roleResourceList) {
        this.roleResourceList = roleResourceList;
    }
}
