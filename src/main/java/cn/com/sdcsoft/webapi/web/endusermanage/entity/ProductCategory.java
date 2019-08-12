package cn.com.sdcsoft.webapi.web.endusermanage.entity;

import java.io.Serializable;

/**
 * 锅炉型号
 * @date 2018-08-17
 * @author doudou
 */
public class ProductCategory implements Serializable {

    private Integer id;         //id主键
    private String name;       //字典名称
    private Integer orgId;       //组织Id
    private Integer sort;           //字典排序

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
