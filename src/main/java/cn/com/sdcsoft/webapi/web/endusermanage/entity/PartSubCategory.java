package cn.com.sdcsoft.webapi.web.endusermanage.entity;

import java.io.Serializable;

/**
 * 辅机大类
 *
 * @author doudou
 * @date 2018-07-10
 */
public class PartSubCategory implements Serializable {

    private Integer id;             //id主键
    private Integer categoryId;   //大类Id
    private String name;           //小类名称
    private int sort;               //排序

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
