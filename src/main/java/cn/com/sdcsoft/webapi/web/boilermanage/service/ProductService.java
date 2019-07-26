package cn.com.sdcsoft.webapi.web.boilermanage.service;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Product;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.Boiler_ProductMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.Boiler_ProductPartInfoMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.Boiler_ProductUserMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.Boiler_RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)
public class ProductService {

    @Autowired
    Boiler_ProductMapper productMapper;

    @Autowired
    Boiler_ProductUserMapper productUserMapper;

    @Autowired
    Boiler_ProductPartInfoMapper productPartInfoMapper;

    @Autowired
    Boiler_RoleMapper roleMapper;


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
