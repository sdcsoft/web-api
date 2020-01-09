package cn.com.sdcsoft.webapi.web.endusermanage.entity;


import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Integer id;
    private Integer orgId;
    private Integer employeeId;
    private String userName;
    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    private String mark;

    private List<Resource> listResource;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }


    public List<Resource> getListResource() {
        return listResource;
    }

    public void setListResource(List<Resource> listResource) {
        this.listResource = listResource;
    }

}
