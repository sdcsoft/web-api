package cn.com.sdcsoft.webapi.web.endusermanage.service;

import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_ProductMapper;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_ProductPartInfoMapper;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_ProductUserMapper;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_RoleMapper;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)
public class EndUserManage_ProductService {

    @Autowired
    EndUser_DB_ProductMapper productMapper;

    @Autowired
    EndUser_DB_ProductUserMapper productUserMapper;

    @Autowired
    EndUser_DB_ProductPartInfoMapper productPartInfoMapper;

    @Autowired
    EndUser_DB_RoleMapper roleMapper;


    /**
     * 管理员创建产品
     * @param product
     * @return
     */
    public boolean createProduct(Product product){
        productMapper.createProduct(product);
        return true;
    }

    /**
     * 普通员工创建产品
     * @param product
     * @param userId
     * @return
     */
    public boolean createProduct(Product product,Integer userId){
        productMapper.createProduct(product);
        productUserMapper.createProductUser(userId,product.getId());
        return true;
    }

    public int deleteProduct(int id,String controllerNo){
        productUserMapper.clearProductMap(id);
        productPartInfoMapper.clearProductPartInfos(id);
        productMapper.removerProduct(id);
        return 0;
    }
}
